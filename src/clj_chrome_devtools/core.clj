(ns clj-chrome-devtools.core
  (:require [clj-chrome-devtools.impl.connection :as connection]))


(def current-connection connection/current-connection)

(defn set-current-connection!
  "Set the globally used current connection"
  [c]
  (reset! connection/current-connection c))

(def connect connection/connect)
