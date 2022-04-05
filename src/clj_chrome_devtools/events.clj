(ns clj-chrome-devtools.events
  "Listening to events coming from Chrome."
  (:require [clj-chrome-devtools.impl.connection :as connection]))

(def default-wait-ms 30000)

(defn listen
  "Subscribe to listen to given domain event. Both domain and event are keywords.
  Returns a 0-arity function that removes the listener when invoked."
  ([domain event callback]
   (listen (connection/get-current-connection) domain event callback))
  ([connection domain event callback]
   (connection/listen connection domain event callback)))

(defn listen-once
  "Synchronously listen for an event once, remove the listener and return the event."
  ([domain event]
   (listen-once (connection/get-current-connection) domain event))
  ([connection domain event]
   (let [p (promise)
         unlisten (listen connection domain event #(deliver p %))
         val @p]
     (unlisten)
     val)))

(defn unlisten
  "Remove the listening callback from the subscribers of the specified
  domain event type."
  ([domain event callback]
   (unlisten (connection/get-current-connection) domain event callback))
  ([connection domain event callback]
   (connection/unlisten connection domain event callback)))

(defn wait-for-event
  "Synchronously wait for an event with specific parameters."
  ([domain event params]
   (wait-for-event (connection/get-current-connection) domain event params))
  ([connection domain event params]
   (wait-for-event connection domain event params default-wait-ms))
  ([connection domain event params timeout-ms]
   (wait-for-event connection domain event params timeout-ms nil))
  ([connection domain event params timeout-ms after-attach-listener-fn]
   (let [p (promise)
         callback #(when (= params (select-keys (:params %) (keys params)))
                     (deliver p (:params %)))
         unlisten (listen connection domain event callback)
         _ (when after-attach-listener-fn
             (after-attach-listener-fn))
         val (deref p timeout-ms ::timeout)]
     (unlisten)
     (if (= val ::timeout)
       (throw (ex-info "Timeout! Specified event was not received."
                       {:domain domain :event event :params params
                        :timeout timeout-ms}))
       val))))

(defmacro with-event-wait
  "Execute body that causes some later events and wait for the event before returning.
  If the event is not received after waiting timeout-ms milliseconds, an exception is thrown."
  ([domain event body]
   `(with-event-wait (connection/get-current-connection) ~domain ~event ~body))
  ([connection domain event body]
   `(with-event-wait ~connection ~domain ~event default-wait-ms ~body))
  ([connection domain event timeout-ms body]
   `(wait-for-event ~connection ~domain ~event {} ~timeout-ms
                    (fn []
                      ~@body))))
