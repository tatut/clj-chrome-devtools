(ns clj-chrome-devtools.impl.connection
  "The remote debugging WebSocket connection"
  (:require [cheshire.core :as cheshire]
            [java-http-clj.core :as http]
            [java-http-clj.websocket :as ws]
            [clj-chrome-devtools.impl.util :refer [camel->clojure]]
            [clojure.core.async :as async]
            [clojure.string :as str]
            [clojure.tools.logging :as log])
  (:import (java.util.concurrent Executors ExecutorService)))

(set! *warn-on-reflection* true)

(def ^:private executor (Executors/newCachedThreadPool))


(defn- submit! [^Runnable func]
  (.submit ^ExecutorService executor func))

(defrecord Connection [ws-connection requests event-listeners]
  #?@(:bb [] ;; babashka doesn't support implementing interfaces at this time
      :clj [java.lang.AutoCloseable
            (close [{ws-conn :ws-connection}]
                   (when ws-conn
                     (ws/close ws-conn)))]))

(defn connection? [c]
  (instance? Connection c))

(defonce current-connection (atom nil))

(defn get-current-connection []
  (let [c @current-connection]
    (assert c "No current Chrome Devtools connection!")
    c))

(defn- parse-json [string]
  (cheshire/parse-string string (comp keyword camel->clojure)))

(defn- publish-event [event-listeners msg]
  (let [[domain event :as event-key]
        (map (comp keyword camel->clojure)
             (str/split (:method msg) #"\."))
        listeners (get event-listeners event-key)]
    (when (seq listeners)
      (let [event {:domain domain
                   :event  event
                   :params (:params msg)}]
        (submit! #(doseq [listener listeners]
                    (listener event)))))))

(defn unlisten [connection domain event callback]
  (swap! (:event-listeners connection) update [domain event]
         #(disj (or % #{}) callback)))

(defn listen
  "Listen to an event in the specific domain. Invokes callback when an event is
  received.

  Returns a 0-arity function that will remove the listener when invoked."
  [connection domain event callback]

  (swap! (:event-listeners connection) update [domain event]
         #(conj (or % #{}) callback))
  #(unlisten connection domain event callback))


(defn- on-receive-text [requests event-listeners-atom]
  (let [builder (StringBuilder.)]
    (fn [_ws msg last?]
      (.append builder msg)
      (when last?
        (try
          (let [{id :id :as json-msg} (parse-json (str builder))]
            (if id
              ;; Has id: this is a response to our previously sent command
              (let [callback (@requests id)]
                (swap! requests dissoc id)
                (submit! #(callback json-msg)))
              ;; This is an event
              (publish-event @event-listeners-atom json-msg)))
          (catch Throwable t
            (log/error "Exception in devtools WebSocket receive, msg: " msg
                       ", throwable: " t))
          (finally
            (.setLength builder 0)))))))

(defn- on-close
  [_ws code reason]
  (log/info "WebSocket connection closed with status code and reason:" code reason))

(defn- wait-for-remote-debugging-port [host port max-wait-time-ms]
  (let [wait-until (+ (System/currentTimeMillis) max-wait-time-ms)
        url        (str "http://" host ":" port "/json/version")]
    (loop [response (http/get url)]
      (cond
        (= (:status response) 200)
        :ok

        (> (System/currentTimeMillis) wait-until)
        (throw (ex-info "Chrome remote debugging port not up"
                        {:host             host :port port
                         :max-wait-time-ms max-wait-time-ms}))

        :else
        (do (Thread/sleep 100)
            (recur (http/get url)))))))

(defn inspectable-pages
  "Collect the list of inspectable pages returned by the DevTools protocol."
  ([host port]
    (inspectable-pages host port 1000))
  ([host port max-wait-time-ms]
   (wait-for-remote-debugging-port host port max-wait-time-ms)
   (let [response (http/get (str "http://" host ":" port "/json/list"))]
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

#_(defn make-ws-client
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
  [web-socket-debugger-url & [ws-builder-opts]]
  (assert web-socket-debugger-url)
  (let [;; Request id to callback
        requests   (atom {})

        ;; Event listeners by event domain and type
        event-listeners (atom {})]

    (->Connection
     (ws/build-websocket web-socket-debugger-url
                         {:on-text (on-receive-text requests event-listeners)
                          :on-binary (fn [& args] (println "should not receive binary messages, args: " args))
                          :on-error #(log/error
                                      "Error in devtools WebSocket connection; throwable:" %)
                          :on-close on-close}
                         (or ws-builder-opts {}))
     requests
     event-listeners)))

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
   (connect host port max-wait-time-ms {}))
  ([host port max-wait-time-ms ws-builder-options]
   (when max-wait-time-ms
     (wait-for-remote-debugging-port host port max-wait-time-ms))
    (let [url (-> (inspectable-pages host port)
                  (first-inspectable-page))]
      (connect-url url ws-builder-options))))

(defn send-command-async
  "Send command and return a promise for its result."
  [{requests :requests con :ws-connection} payload id]
  (let [p (promise)]
    (swap! requests assoc id #(deliver p %))
    (ws/send con (cheshire/encode payload))
    p))

(defn send-command
  "Send command synchronously. Blocks until result is done and returns it."
  [connection payload id]
  @(send-command-async connection payload id))
