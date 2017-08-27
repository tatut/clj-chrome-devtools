(ns clj-chrome-devtools.impl.connection
  "The remote debugging WebSocket connection"
  (:require [gniazdo.core :as ws]
            [org.httpkit.client :as http]
            [cheshire.core :as cheshire]
            [clj-chrome-devtools.impl.util :refer [camel->clojure]]
            [clojure.core.async :as async]
            [clojure.string :as str]))

(defrecord Connection [ws-connection requests event-chan event-pub])

(defn connection? [c]
  (instance? Connection c))

(defonce current-connection (atom nil))

(defn get-current-connection []
  (let [c @current-connection]
    (assert c "No current Chrome Devtools connection!")
    c))

(defn- parse-json [string]
  (cheshire/parse-string string (comp keyword camel->clojure)))

(defn- publish-event [event-chan msg]
  (let [[domain event] (map (comp keyword camel->clojure)
                            (str/split (:method msg) #"\."))
        event {:domain domain
               :event event
               :params (:params msg)}]
    ;(println "PUBLISH: " event)
    (async/go
      (async/>! event-chan event))))

(defn listen [{event-pub :event-pub} domain event]
  (let [ch (async/chan)]
    (async/sub event-pub [domain event] ch)
    ch))

(defn unlisten [{event-pub :event-pub} domain event listening-ch]
  (async/unsub event-pub [domain event] listening-ch))

(defn- on-receive [requests event-chan msg]
  (try
    (let [{id :id :as json-msg} (parse-json msg)]
      (if id
        ;; Has id: this is a response to our previously sent command
        (let [callback (@requests id)]
          (swap! requests dissoc id)
          (async/thread (callback json-msg)))
        ;; This is an event
        (publish-event event-chan json-msg)))
    (catch Throwable t
      (println "Exception in devtools WebSocket receive, msg: " msg
               ", throwable: " t))))

(defn- fetch-ws-debugger-url [host port]
  (let [response @(http/get (str "http://" host ":" port "/json/list"))
        url (some-> response :body parse-json first :web-socket-debugger-url)]
    (when-not url
      (throw (ex-info "No chrome remote debugging URL found"
                      {:host host :port port
                       :response response})))
    url))

(defn- wait-for-remote-debugging-port [host port max-wait-time-ms]
  (let [wait-until (+ (System/currentTimeMillis) max-wait-time-ms)
        url (str "http://" host ":" port "/json/version")]
    (loop [response @(http/head url)]
      (cond
        (= (:status response) 200)
        :ok

        (> (System/currentTimeMillis) wait-until)
        (throw (ex-info "Chrome remote debugging port not up"
                        {:host host :port port
                         :max-wait-time-ms max-wait-time-ms}))

        :default
        (do (Thread/sleep 100)
            (recur @(http/head url)))))))

(defn connect
  "Connect to a running headless Chrome "
  ([] (connect "localhost" 9222))
  ([host port] (connect host port nil))
  ([host port max-wait-time-ms]
   (when max-wait-time-ms
     (wait-for-remote-debugging-port host port max-wait-time-ms))
   (let [client (ws/client)
         url (fetch-ws-debugger-url host port)

         ;; Request id to callback
         requests (atom {})

         ;; Event pub/sub channel
         event-chan (async/chan)
         event-pub (async/pub event-chan (juxt :domain :event))]

     ;; Configure max message size to 1mb (default 64kb is way too small)
     (doto (.getPolicy client)
       (.setMaxTextMessageSize (* 1024 1024)))

     (.start client)
     (->Connection (ws/connect url
                     :on-receive (partial on-receive requests event-chan)
                     :client client)
                   requests
                   event-chan
                   event-pub))))

(defn send-command [{requests :requests con :ws-connection} payload id callback]
  (swap! requests assoc id callback)
  (ws/send-msg con (cheshire/encode payload)))
