(ns clj-chrome-devtools.automation.fixture
  "Provides a `clojure.test` fixture for starting a new Chrome headless instance
  and an automation context for it."
  (:require [clj-chrome-devtools.automation :as automation]
            [clj-chrome-devtools.automation.launcher :as launcher]))

;; Define previously defined public vars here for backwards compatibility
(def possible-chrome-binaries launcher/possible-chrome-binaries)
(def binary-path launcher/binary-path)
(def find-chrome-binary launcher/find-chrome-binary)
(def launch-chrome launcher/launch-chrome)
(def default-options launcher/default-options)

(defn create-chrome-fixture
  ([] (create-chrome-fixture {}))
  ([options]
   (fn [tests]
     (let [prev-current-automation @automation/current-automation]
       (try
         (with-open [^java.lang.AutoCloseable automation (launcher/launch-automation options)]
           (reset! automation/current-automation automation)
           (tests))
         (finally
           (reset! automation/current-automation prev-current-automation)))))))
