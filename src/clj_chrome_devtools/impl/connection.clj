(ns clj-chrome-devtools.impl.connection
  "The remote debugging WebSocket connection"
  (:require [gniazdo.core :as ws]
            [org.httpkit.client :as http]
            [cheshire.core :as cheshire]
            [clj-chrome-devtools.impl.util :refer [camel->clojure]]
            [clojure.core.async :as async]
            [clojure.string :as str]))

(defonce current-connection (atom nil))

;; Request id to callback
(defonce requests (atom {}))

;; Event pub/sub channel
(defonce event-chan (async/chan))

(defonce event-pub (async/pub event-chan (juxt :domain :event)))

(defn get-current-connection []
  (let [c @current-connection]
    (assert c "No current Chrome Devtools connection!")
    c))

(defn- parse-json [string]
  (cheshire/parse-string string (comp keyword camel->clojure)))

(defn- publish-event [msg]
  (println "PUBLISH: " msg)
  (let [[domain event] (map (comp keyword camel->clojure)
                            (str/split (:method msg) #"\."))
        event {:domain domain
               :event event
               :params (:params msg)}]
    (println " => " event)
    (async/go
      (async/>! event-chan event))))

(defn listen-to
  "Listen to an event from chrome. Domain and event are keywords.
  Returns channel where events can be read from."
  [domain event]
  (let [ch (async/chan)]
    (async/sub event-pub [domain event] ch)
    ch))

(defn- on-receive [msg]
  (try
    (let [{id :id :as json-msg} (parse-json msg)]
      (if id
        ;; Has id: this is a response to our previously sent command
        (let [callback (@requests id)]
          (swap! requests dissoc id)
          (async/thread (callback json-msg)))
        ;; This is an event
        (publish-event json-msg)))
    (catch Throwable t
      (println "Exception in devtools WebSocket receive, msg: " msg
               ", throwable: " t))))

(defn- fetch-ws-debugger-url [host port]
  (-> (str "http://" host ":" port "/json/list")
      http/get
      deref
      :body
      parse-json
      first
      :web-socket-debugger-url))

(defn connect
  ([] (connect "localhost" 9222))
  ([host port]
   (let [client (ws/client)
         url (fetch-ws-debugger-url host port)]
     (doto (.getPolicy client)
       (.setMaxTextMessageSize (* 1024 1024)))
     (.start client)
     (ws/connect url :on-receive on-receive :client client))))

(defn send-command [connection payload id callback]
  (swap! requests assoc id callback)
  (ws/send-msg connection (cheshire/encode payload)))
