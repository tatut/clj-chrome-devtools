(ns clj-chrome-devtools.automation.launcher
  "Launch a headless Chrome for automation purposes."
  (:require [clojure.java.shell :as sh]
            [clojure.string :as str]
            [clojure.tools.logging :as log]
            [clj-chrome-devtools.automation :as automation]
            [clj-chrome-devtools.impl.util :as util]
            [clj-chrome-devtools.impl.connection :as connection]))

(set! *warn-on-reflection* true)

(def possible-chrome-binaries
  ["/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
   "google-chrome-stable"
   "google-chrome"
   "chromium-browser"
   "chromium"
   "chrome.exe"])

(defn- is-windows? []
  (when-let [os (System/getenv "os")]
    (str/starts-with? os "Windows")))

(defn binary-path [candidate]
  (let [{:keys [exit out]}
        (if (is-windows?)
          ;; Note: usually this is the default location
          ;; "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"
          ;; "C:/Program Files (x86)/Google/Chrome Beta/Application/chrome.exe"
          (sh/sh "where.exe" candidate)
          ;; Assume Linux/MacOS
          (sh/sh "which" candidate))]
    (when (= exit 0)
      (str/trim-newline out))))

(defn find-chrome-binary []
  (some binary-path possible-chrome-binaries))

(defn launch-chrome [binary-path remote-debugging-port options]
  (log/trace "Launching Chrome headless, binary: " binary-path
             ", remote debugging port: " remote-debugging-port
             ", options: " (pr-str options))
  (let [args (->> [binary-path
                   (when (:headless? options) "--headless")
                   (when (:no-sandbox? options) "--no-sandbox")
                   (when-not (:enable-gpu? options) "--disable-gpu")
                   (str "--remote-debugging-port=" remote-debugging-port)
                   (:args options)]
                  flatten
                  (remove nil?))]
    (.exec (Runtime/getRuntime)
           ^"[Ljava.lang.String;" (into-array String args))))

(defn default-options []
  {:chrome-binary (find-chrome-binary)
   :remote-debugging-port nil
   :headless? true})

(defn launch-automation [options]
  (let [options (merge (default-options) options)
        {:keys [chrome-binary remote-debugging-port headless?]} options
        port (or remote-debugging-port (util/random-free-port))
        ^java.lang.Process process (launch-chrome chrome-binary port options)]
    (automation/create-automation
     (connection/connect "localhost" port 30000)
     (fn [_]
       (.destroy process)))))
