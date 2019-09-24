(ns clj-chrome-devtools.impl.connection
  "The remote debugging WebSocket connection"
  (:require [gniazdo.core :as ws]
            [org.httpkit.client :as http]
            [cheshire.core :as cheshire]
            [clj-chrome-devtools.impl.util :refer [camel->clojure]]
            [clojure.core.async :as async]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(defrecord Connection [ws-connection requests event-chan event-pub]
  java.lang.AutoCloseable
  (close [{ws-conn :ws-connection}] 
    (when ws-conn 
      (ws/close ws-conn))))


(defn connection? [c]
  (instance? Connection c))

(defonce current-connection (atom nil))

(defn get-current-connection []
  (let [c @current-connection]
    (assert c "No current Chrome Devtools connection!")
    c))

(def ws-close-status-code->reason
  ^{:source "https://tools.ietf.org/html/rfc6455#section-7.4"}
  {1000 "Normal"
   1001 "Going away"
   1002 "Protocol error"
   1003 "Unsupported"
   1005 "No status code"
   1006 "Abnormal"
   1007 "Unsupported payload"
   1008 "Policy violation"
   1009 "Too large"
   1010 "Mandatory extension"
   1011 "Server error"
   1012 "Service restart"
   1013 "Try again later"
   1014 "Bad gateway"
   1015 "TLS handshake fail"})

(defn- parse-json [string]
  (cheshire/parse-string string (comp keyword camel->clojure)))

(defn- publish-event [event-chan msg]
  (let [[domain event] (map (comp keyword camel->clojure)
                            (str/split (:method msg) #"\."))
        event {:domain domain
               :event  event
               :params (:params msg)}]
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
      (log/error "Exception in devtools WebSocket receive, msg: " msg
                 ", throwable: " t))))

(defn- on-close [code reason]
  (log/log
   ; The library that gniazdo wraps (the Jetty WebSocket API/Client) registers a
   ; JVM shutdown hook to (I think) close the connection when the JVM shuts
   ; down. This causes the on-close hook to be called during JVM shutdown. Since
   ; this case (code 1001 and reason "Shutdown") is a normal, uninteresting
   ; case, we want to use the :info log level. Same for when the code is 1000,
   ; which is specifically for normal closures. Otherwise, we’ll use :warn
   ; because the closure would appear to be abnormal.
   (if (or (= code 1000)
           (and (= code 1001) (re-seq #"(?i)shutdown" reason)))
     :info :warn)
   "WebSocket connection closed with status code"
   code
   (str "(" (ws-close-status-code->reason code "UNKNOWN") ")")
   "and reason:"
   reason))

(defn- wait-for-remote-debugging-port [host port max-wait-time-ms]
  (let [wait-until (+ (System/currentTimeMillis) max-wait-time-ms)
        url        (str "http://" host ":" port "/json/version")]
    (loop [response @(http/head url)]
      (cond
        (= (:status response) 200)
        :ok

        (> (System/currentTimeMillis) wait-until)
        (throw (ex-info "Chrome remote debugging port not up"
                        {:host             host :port port
                         :max-wait-time-ms max-wait-time-ms}))

        :default
        (do (Thread/sleep 100)
            (recur @(http/head url)))))))

(defn inspectable-pages
  "Collect the list of inspectable pages returned by the DevTools protocol."
  ([host port]
    (inspectable-pages host port 1000))
  ([host port max-wait-time-ms]
   (wait-for-remote-debugging-port host port max-wait-time-ms)
   (let [response @(http/get (str "http://" host ":" port "/json/list"))]
     (some->> response
              :body
              parse-json))))

(defn- first-inspectable-page [pages]
  (or (some->> pages
               (filter (comp #{"page"} :type))
               (keep :web-socket-debugger-url)
               first)
      (throw (ex-info "No debuggable pages found"
                      {:pages pages}))))

(defn make-ws-client
  "Constructs ws client. Idle timeout defaults to 0, which means keep it
  alive for the session. The `max-msg-size-mb` defaults to 1MB."
  [ & [{:keys [idle-timeout max-msg-size-mb]
        :or {idle-timeout 0
             max-msg-size-mb (* 1024 1024)}}]]
  (let [client (ws/client)]
    (doto (.getPolicy client)
      (.setIdleTimeout idle-timeout)
      (.setMaxTextMessageSize max-msg-size-mb))
    client))

(defn connect-url
  "Establish a websocket connection to web-socket-debugger-url, as given by
   inspectable-pages."
  [web-socket-debugger-url & [ws-client]]
  (assert web-socket-debugger-url)
  (let [client     (or ws-client
                       (make-ws-client))
        ;; Request id to callback
        requests   (atom {})

        ;; Event pub/sub channel
        event-chan (async/chan)
        event-pub  (async/pub event-chan (juxt :domain :event))]

    (.start client)
    (->Connection (ws/connect web-socket-debugger-url
                              :on-receive (partial on-receive requests event-chan)
                              :on-error #(log/error
                                          "Error in devtools WebSocket connection; throwable:" %)
                              :on-close on-close
                              :client client
                              :gniazdo.core/cleanup #(.stop client)) 
                  requests
                  event-chan
                  event-pub)))

(defn connect
  "Establish a websocket connection to the DevTools protocol at host:port.
   Selects the first inspectable page returned; to attach to a specific page,
   see connect-url. Initial connection will be retried for up to
   max-wait-time-ms milliseconds (default 1000) before giving up."
  ([]
   (connect "localhost" 9222))
  ([host port]
   (connect host port 1000))
  ([host port max-wait-time-ms]
   (connect host port max-wait-time-ms (make-ws-client)))
  ([host port max-wait-time-ms ws-client]
   (when max-wait-time-ms
     (wait-for-remote-debugging-port host port max-wait-time-ms))
    (let [url (-> (inspectable-pages host port)
                  (first-inspectable-page))]
      (connect-url url ws-client))))

(defn send-command [{requests :requests con :ws-connection} payload id callback]
  (swap! requests assoc id callback)
  (ws/send-msg con (cheshire/encode payload)))
