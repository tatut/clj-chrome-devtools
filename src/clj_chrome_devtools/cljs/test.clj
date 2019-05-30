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
            [clojure.test]
            [clojure.java.shell :as sh])
  (:import (java.io File)))


(defn- find-defproject [file]
  (with-open [in (java.io.PushbackReader. (io/reader file))]
    (loop [form (read in false ::eof)]
      (cond
        (= ::eof form)
        (throw (ex-info "Can't find defproject form" {:file file}))


        (and (coll? form) (= (first form) 'defproject))
        form

        :else
        (recur (read in))))))

(defn- load-project-clj
  "Load project.clj file and turn it into a map."
  []
  (->> "project.clj" find-defproject
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
  (-> "clj_chrome_devtools_runner.tpl"
      io/resource slurp
      (str/replace "__REQUIRE_NAMESPACES__"
                   (str/join "\n        " namespaces))
      (str/replace "__TEST_NAMESPACES__"
                   (str/join "\n         "
                             (map #(str "'" %) namespaces)))))

(defn- with-test-runner-source [namespaces source-path fun]
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
      {:status 200
       :headers {"Content-Type" (cond
                                  (str/ends-with? uri ".html")
                                  "text/html"

                                  (str/ends-with? uri ".js")
                                  "application/javascript"

                                  :else
                                  "application/octet-stream")}
       :body (slurp file)}

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

(defn- poll-test-execution []
  (loop [screenshots []]
    (if-let [screenshot-file-name (automation/evaluate "window.CLJ_SCREENSHOT_NAME || null")]
      (do
        ;; Take screenshot if requested
        (println "Taking screenshot to:" screenshot-file-name)
        (automation/screenshot @automation/current-automation screenshot-file-name)
        (automation/evaluate "window.CLJ_SCREENSHOT_NAME = null")
        (automation/evaluate "window.CLJ_SCREENSHOT_RESOLVE(true)")
        (recur (conj screenshots screenshot-file-name)))
      (let [msgs (automation/evaluate "clj_chrome_devtools_runner.get_printed()")]
        (doseq [m (mapcat #(str/split % #"\n") msgs)]
          (println "[CLJS]" m))

        (if-not (some assert-test-result msgs)
          (do
            (Thread/sleep 100)
            (recur screenshots))
          screenshots)))))

(defn output-screenshot-videos [screenshots framerate]
  (let [video-names (into #{}
                          (keep #(second (re-matches #"^([^\d]+)-(\d+)\.png$" %)))
                          screenshots)]
    (loop [[video-name & video-names] video-names]
      (when video-name
        (let [input (str video-name "-%d.png")
              output (str video-name ".gif")
              cmd ["ffmpeg" "-framerate" "2" "-y" "-i" input output]]
          (println "Generate video " output)
          (let [{exit :exit err :err} (apply sh/sh cmd)]
            (if (zero? exit)
              (recur video-names)
              (println "Failed to generate video with command: \n  > "
                       (str/join " " cmd)
                       "\n Check ffmpeg installation, subprocess err: \n"
                       err))))))))

(defn run-tests
  ([build-output]
   (run-tests build-output nil))
  ([{:keys [js js-directory]} {:keys [no-sandbox? screenshot-video? framerate]}]
   (let [chrome-fixture (create-chrome-fixture {:headless? true :no-sandbox? no-sandbox?})]
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

             (let [screenshots (poll-test-execution)]
               (when screenshot-video?
                 (output-screenshot-videos screenshots (or framerate 2))))

             (server)
             (finally
               (io/delete-file f)))))))))

(defn build-and-test
  ([build-id namespaces]
   (build-and-test build-id namespaces nil))
  ([build-id namespaces options]
   (let [project-clj (load-project-clj)
         build-output (build project-clj build-id namespaces)]
     (run-tests build-output options))))
