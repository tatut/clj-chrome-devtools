(ns clj-chrome-devtools.automation.fixture
  "Provides a `clojure.test` fixture for starting a new Chrome headless instance
  and an automation context for it."
  (:require [clj-chrome-devtools.automation :as automation]
            [clojure.test :as test]
            [clojure.java.shell :as sh]
            [clojure.string :as str]
            [clj-chrome-devtools.impl.connection :as connection]
            [org.httpkit.client :as http]))

(def possible-chrome-binaries
  ["/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
   "google-chrome-stable"
   "google-chrome"
   "chromium-browser"
   "chromium"])

(defn binary-path [candidate]
  ;; PENDING: what about Windows? PR welcome
  (let [{:keys [exit out]} (sh/sh "which" candidate)]
    (when (= exit 0)
      (str/trim-newline out))))

(defn find-chrome-binary []
  (some binary-path possible-chrome-binaries))

(defn random-free-port []
  (let [s (doto (java.net.ServerSocket. 0)
            (.setReuseAddress true))]
    (try
      (.getLocalPort s)
      (finally (.close s)))))

(defn launch-chrome [binary-path remote-debugging-port]
  (.exec (Runtime/getRuntime)
         (into-array String
                     [binary-path
                      "--headless"
                      "--disable-gpu"
                      (str "--remote-debugging-port=" remote-debugging-port)])))

(defn- wait-for-remote-debugging-port [host port]
  (let [wait-until (+ (System/currentTimeMillis) 30000)
        url (str "http://" host ":" port "/json/version")]
    (loop [response @(http/head url)]
      (cond
        (= (:status response) 200)
        :ok

        (> (System/currentTimeMillis) wait-until)
        (throw (ex-info "Chrome remote debugging port not up in 30s"
                        {:host host :port port}))

        :default
        (recur @(http/head url))))))

(defn create-chrome-fixture
  ([] (create-chrome-fixture (find-chrome-binary)))
  ([chrome-binary] (create-chrome-fixture chrome-binary nil))
  ([chrome-binary remote-debugging-port]
   (fn [tests]
     (let [port (or remote-debugging-port (random-free-port))
           process (launch-chrome chrome-binary port)
           _ (wait-for-remote-debugging-port "localhost" port)
           automation (automation/create-automation
                       (connection/connect "localhost" port))
           prev-current-automation @automation/current-automation]
       (reset! automation/current-automation automation)
       (try
         (tests)
         (finally
           (reset! automation/current-automation prev-current-automation)
           (.destroy process)))))))
