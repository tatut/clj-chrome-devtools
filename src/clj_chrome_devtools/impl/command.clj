(ns clj-chrome-devtools.impl.command
  "Implementation utils for commands"
  (:require [clojure.set :as set]
            [clj-chrome-devtools.impl.connection :as connection]))

(defonce command-id (atom 0))

(defn next-command-id! []
  (swap! command-id inc))

(defn command-payload [id name params parameter-names]
  {:id id
   :method name
   :params (set/rename-keys params parameter-names)})

(defn command [connection domain name params param-names]
  (let [id (next-command-id!)
        method (str domain "." name)
        payload (command-payload id method params param-names)
        result (connection/send-command connection payload id)]
    (if-let [error (:error result)]
      (throw (ex-info (str "Error in command " method ": " (:message error))
                      {:request payload
                       :error error}))
      (:result result))))
