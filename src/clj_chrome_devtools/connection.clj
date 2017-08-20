(ns clj-chrome-devtools.connection
  "The remote debugging WebSocket connection"
  (:require [gniazdo.core :as ws]
            [org.httpkit.client :as http]
            [cheshire.core :as cheshire]))

(defonce current-connection (atom nil))

;; Request id to callback
(defonce requests (atom {}))

(defn get-current-connection []
  (let [c @current-connection]
    (assert c "No current Chrome Devtools connection!")
    c))

(defn- parse-json [string]
  (cheshire/parse-string string true))

(defn- on-receive [msg]
  (try
    (let [{id :id :as response} (parse-json msg)
          callback (@requests id)]
      (swap! requests dissoc id)
      (callback response))
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
      :webSocketDebuggerUrl))

(defn connect
  ([] (connect "localhost" 9222))
  ([host port]
   (let [url (fetch-ws-debugger-url host port)]
     (ws/connect url :on-receive on-receive))))

(defn send-command [connection payload id callback]
  (swap! requests assoc id callback)
  (ws/send-msg connection (cheshire/encode payload)))
