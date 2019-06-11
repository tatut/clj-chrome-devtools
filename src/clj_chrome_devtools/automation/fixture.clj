(ns clj-chrome-devtools.automation.fixture
  "Provides a `clojure.test` fixture for starting a new Chrome headless instance
  and an automation context for it."
  (:require [clj-chrome-devtools.automation :as automation]
            [clj-chrome-devtools.automation.launcher :as launcher]
            [clj-chrome-devtools.impl.connection :as connection]
            [clj-chrome-devtools.impl.util :refer [random-free-port]]
            [clojure.java.shell :as sh]
            [clojure.string :as str]
            [clojure.test :as test]
            [org.httpkit.client :as http]
            [taoensso.timbre :as log]))

;; Define previously defined public vars here for backwards compatibility
(def possible-chrome-binaries launcher/possible-chrome-binaries)
(def binary-path launcher/binary-path)
(def find-chrome-binary launcher/find-chrome-binary)
(def launch-chrome launcher/launch-chrome)
(def default-options launcher/default-options)

(defn create-chrome-fixture
  ([] (create-chrome-fixture {}))
  ([options]
   (let [options (merge (launcher/default-options) options)
         {:keys [chrome-binary remote-debugging-port headless?]} options]
     (fn [tests]
       (let [port (or remote-debugging-port (random-free-port))
             process (launcher/launch-chrome chrome-binary port options)
             automation (automation/create-automation
                         (connection/connect "localhost" port 30000))
             prev-current-automation @automation/current-automation]
         (reset! automation/current-automation automation)
         (try
           (tests)
           (finally
             (reset! automation/current-automation prev-current-automation)
             (.destroy process))))))))
