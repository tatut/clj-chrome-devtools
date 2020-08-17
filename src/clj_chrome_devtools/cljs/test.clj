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
            [clojure.test :refer [is]]
            [clojure.java.shell :as sh])
  (:import (java.io File)))



(defn no-op-log [& _args]
  nil)

(defn println-log [& args]
  (println (str/join " " args)))

(def ^:dynamic log no-op-log)

(defn- find-defproject [file]
  (log "Loading leiningen project from: " file)
  (with-open [in (java.io.PushbackReader. (io/reader file))]
    (loop [form (read in false ::eof)]
      (cond
        (= ::eof form)
        (throw (ex-info "Can't find defproject form" {:file file}))


        (and (coll? form) (= (first form) 'defproject))
        (do
          (log "Found defproject: " (nth form 1) (nth form 2))
          form)

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
  (log "Generating test runner forms for namespaces: " namespaces)
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
  (let [{:keys [source-paths compiler] :as _build}
        (build-by-id project-clj build-id)]
    (log "Building ClojureScript, source paths: " source-paths)
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

(defn- test-page [{:keys [js runner]
                   :or {runner "clj_chrome_devtools_runner.run_chrome_tests();"}}]
  (-> "test-page.tpl" io/resource slurp
      (str/replace "__RUNNER__" runner)
      (str/replace "__JS__" (slurp js))))

(defn- file-handler [{:keys [uri request-method] :as _req}]
  (log "REQUEST: " uri)
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

(defn- test-result [msg]
  (let [[match errors failures] (re-matches final-test-report-pattern msg)]
    (when match
      (if (= "0" errors failures)
        :ok
        :fail))))

(defn- poll-test-execution []
  (loop [started? false
         screenshots []]
    (if (not started?)
      ;; Tests have not started yet, check for fatal error or start flag
      (if-let [fatal-error (automation/evaluate "window['CLJ_FATAL_ERROR'] || null")]
        (throw (ex-info "Test page had error before tests were started." {:error fatal-error}))

        (if (automation/evaluate "window['CLJ_TESTS_STARTED'] || false")
          (recur true screenshots)
          (do
            (Thread/sleep 100)
            (recur false screenshots))))

      ;; Tests have started, poll for screenshots and printed output
      (if-let [screenshot-file-name (automation/evaluate "window['CLJ_SCREENSHOT_NAME'] || null")]
        (do
          ;; Take screenshot if requested
          (log "Taking screenshot to:" screenshot-file-name)
          (automation/screenshot @automation/current-automation screenshot-file-name)
          (automation/evaluate "window.CLJ_SCREENSHOT_NAME = null")
          (automation/evaluate "window.CLJ_SCREENSHOT_RESOLVE(true)")
          (recur started? (conj screenshots screenshot-file-name)))
        (let [msgs (automation/evaluate "clj_chrome_devtools_runner.get_printed()")]
          (doseq [m (mapcat #(str/split % #"\n") msgs)]
            (println "[CLJS]" m))

          (if-let [result (some test-result msgs)]
            {:result result
             :screenshots screenshots}
            (do
              (Thread/sleep 100)
              (recur started? screenshots))))))))

(defn output-screenshot-videos [screenshots framerate loop-video?]
  (let [video-names (into #{}
                          (keep #(second (re-matches #"^([^\d]+)-(\d+)\.png$" %)))
                          screenshots)]
    (loop [[video-name & video-names] video-names]
      (when video-name
        (let [input (str video-name "-%d.png")
              output (str video-name ".png")
              cmd ["ffmpeg"
                   "-framerate" (str framerate)
                   "-y" "-i" input "-f" "apng"
                   "-plays" (if loop-video? "0" "1")
                   output]]
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
  ([{:keys [js runner]} {:keys [headless? no-sandbox?
                                screenshot-video? framerate loop-video?
                                ring-handler on-test-result]}]
   (log "Run compiled js test file:" js)
   (let [chrome-fixture (create-chrome-fixture {:headless? (if (some? headless?)
                                                             headless?
                                                             true)
                                                :no-sandbox? no-sandbox?})]
     (chrome-fixture
      (fn []
        (log "Chrome launched")
        (let [port (random-free-port)
              server (http-server/run-server (fn [req]
                                               (or (and ring-handler (ring-handler req))
                                                   (file-handler req)))
                                             {:port port})
              f (File/createTempFile "test" ".html" (io/file "."))
              url (str "http://localhost:" port "/" (.getName f))]
          (try
            (spit f (test-page (merge
                                {:js js}
                                (when runner
                                  {:runner runner}))))

            (log "Navigate to:" url)
            (automation/to url)

            (log "Wait for test output")
            (let [{:keys [result screenshots] :as test-result} (poll-test-execution)]
              (when screenshot-video?
                (output-screenshot-videos screenshots (or framerate 2) loop-video?))
              (if on-test-result
                (on-test-result test-result)
                (is (= result :ok)
                    "ClojureScript tests had failures or errors, see previous output for details ")))

            (log "Tests done, cleanup")
            (server)
            (finally
              (io/delete-file f)))))))))

(defn build-and-test
  ([build-id namespaces]
   (build-and-test build-id namespaces nil))
  ([build-id namespaces options]
   (binding [log (if (:verbose? options)
                   println-log
                   no-op-log)]
     (let [project-clj (load-project-clj)
           build-output (build project-clj build-id namespaces)]
       (run-tests build-output options)))))
