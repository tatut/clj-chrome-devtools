(ns clj-chrome-devtools.impl.command
  "Implementation utils for commands"
  (:require [clojure.set :as set]))

(defonce command-id (atom 0))

(defn next-command-id! []
  (swap! command-id inc))

(defn command-payload [id name params parameter-names]
  {:id id
   :method name
   :params (set/rename-keys params parameter-names)})
