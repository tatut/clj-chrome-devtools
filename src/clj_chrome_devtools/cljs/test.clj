(ns clj-chrome-devtools.cljs.test
  "Build a ClojureScript build and run its tests as a clojure
  test. This can hook your cljs app tests into the normal
  clojure testing run."
  (:require [cljs.build.api :as cljs-build]
            [clojure.java.io :as io]
            [clj-chrome-devtools.automation.fixture :refer [create-chrome-fixture]]
            [clj-chrome-devtools.automation :as automation]
            [clj-chrome-devtools.impl.util :refer [random-free-port]]
            [org.httpkit.server :as http-server]
            [clojure.string :as str]
            [clojure.test])
  (:import (java.io File)))


(defn- load-project-clj
  "Load project.clj file and turn it into a map."
  []
  (->> "project.clj" slurp read-string
       (drop 3) ;; remove defproject, name and version
       (partition 2) ;; take top level :key val pairs
       (map vec)
       (into {})))

(defn- build-by-id [project-clj build-id]
  (->> project-clj :cljsbuild :builds
       (some #(when (= build-id (:id %))
                %))))

(defn- test-runner-forms
  "ClojureScript forms for test runner"
  [namespaces]
  (str
   "(ns clj-chrome-devtools-runner
      (:require [cljs.test :refer [run-tests]]
                " (str/join "\n" namespaces) "))\n"
   "(def PRINTED (atom []))\n"
   "(defn get-printed [] "
   "  (let [v @PRINTED] "
   "    (reset! PRINTED []) "
   "    (clj->js v)))\n"
   "(defn run-chrome-tests []"
   " (set! *print-fn* (fn [& msg] (swap! PRINTED conj (apply str msg))))\n"
   " (run-tests " (str/join "\n"
                            (map #(str "'" %) namespaces)) "))"))

(defn with-test-runner-source [namespaces source-path fun]
  ;; Create a test runner source file in the given source path
  ;; We have to put this in an existing source path as
  ;; we can't add a new source path dynamically (files therein
  ;; won't be found with io/resource). It is simpler to add
  ;; it to an existing source path and remove afterwards.
  (let [runner (io/file source-path
                        "clj_chrome_devtools_runner.cljs")]
    (spit runner (test-runner-forms namespaces))
    (try
      (fun)
      (finally
        (io/delete-file runner)))))

(defn build [project-clj build-id test-runner-namespaces]
  (let [{:keys [source-paths compiler] :as build}
        (build-by-id project-clj build-id)]

    (with-test-runner-source test-runner-namespaces (last source-paths)
      #(cljs-build/build
        (cljs-build/inputs source-paths)
        (assoc compiler
               :main "clj-chrome-devtools-runner"
               :warnings {:single-segment-namespace false})))
    (assert (.exists (io/file (:output-to compiler)))
            "build output file exists")
    {:js (:output-to compiler)
     :js-directory (:output-dir compiler)}))

(defn- test-page [js]
  (str "<html>"
       "  <head>"
       "  </head>"
       "  <body onload=\"clj_chrome_devtools_runner.run_chrome_tests();\">"
       "    <script type=\"text/javascript\" src=\"" js "\">"
       "    </script>"
       "  </body>"
       "</html>"))

(defn- file-handler [{:keys [uri request-method] :as req}]
  (let [file (io/file "." (subs uri 1))]
    (if (and (= request-method :get) (.canRead file))
      (do
        #_(println "GET " (.getName file))
        {:status 200
         :headers {"Content-Type" (cond
                                    (str/ends-with? uri ".html")
                                    "text/html"

                                    (str/ends-with? uri ".js")
                                    "application/javascript"

                                    :default
                                    "application/octet-stream")}
         :body (slurp file)})

      {:status 404})))

(def ^{:doc "cljs.test failure/error report regex"
       :private true}
  final-test-report-pattern #"(\d+) failures, (\d+) errors.")

(defn- assert-test-result [msg]
  (let [[match errors failures] (re-matches final-test-report-pattern msg)]
    (when match
      (assert (= "0" errors failures)
              "ClojureScript tests had failures or errors, see previous output for details.")
      true)))

(defn- read-console-log-messages []
  (loop []
    (let [msgs (automation/evaluate "clj_chrome_devtools_runner.get_printed()")]
      (doseq [m (mapcat #(str/split % #"\n") msgs)]
        (println "[CLJS]" m))

      (when-not (some assert-test-result msgs)
        (Thread/sleep 100)
        (recur)))))

(defn run-tests [{:keys [js js-directory]}]
  (let [chrome-fixture (create-chrome-fixture {:headless? true})]
    (chrome-fixture
     (fn []
       (let [con (.-connection @automation/current-automation)
             port (random-free-port)
             server (http-server/run-server file-handler {:port port})
             dir (io/file ".")
             f (File/createTempFile "test" ".html"
                                    (io/file "."))]
         (try
           (spit f (test-page js))
           (automation/to (str "http://localhost:" port "/" (.getName f)))

           (read-console-log-messages)

           (server)
           (finally
             (io/delete-file f))))))))

(defn build-and-test [build-id namespaces]
  (let [project-clj (load-project-clj)
        build-output (build project-clj build-id namespaces)]
    (run-tests build-output)))
