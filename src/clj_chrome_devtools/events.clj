(ns clj-chrome-devtools.events
  "Listening to events coming from Chrome."
  (:require [clj-chrome-devtools.impl.connection :as connection]
            [clojure.core.async :as async :refer [go-loop go <! >! <!! >!!]]))

(def default-wait-ms 30000)

(defn listen
  "Subscribe to listen to given domain event. Both domain and event are keywords.
  Returns a core.async channel where events can be read from."
  ([domain event]
   (listen (connection/get-current-connection) domain event))
  ([connection domain event]
   (connection/listen connection domain event)))

(defn unlisten
  "Remove the listening channel from the subscribers of the specified
  domain event type."
  ([domain event listening-ch]
   (unlisten (connection/get-current-connection) domain event listening-ch))
  ([connection domain event listening-ch]
   (connection/unlisten connection domain event listening-ch)))

(defn wait-for-event
  "Synchronously wait for an event with specific parameters."
  ([domain event params]
   (wait-for-event (connection/get-current-connection) domain event params))
  ([connection domain event params]
   (wait-for-event connection domain event params default-wait-ms))
  ([connection domain event params timeout-ms]
   (let [timeout-ch (async/timeout timeout-ms)
         ch (listen connection domain event)
         read! #(async/alts!! [timeout-ch ch])]
     (try
       (loop [[val chan] (read!)]
         (cond
           (= chan timeout-ch)
           (throw (ex-info "Timeout! Specified event was not received."
                           {:domain domain :event event :params params
                            :timeout timeout-ms}))

           (= params (select-keys (:params val) (keys params)))
           (:params val)

           :default
           (recur (read!))))
       (finally
         (unlisten connection domain event ch)
         (async/close! ch))))))

(defmacro with-event-wait
  "Execute body that causes some later events and wait for the event before returning.
  If the event is not received after waiting timeout-ms milliseconds, an exception is thrown."
  ([domain event body]
   `(with-event-wait (connection/get-current-connection) ~domain ~event ~body))
  ([connection domain event body]
   `(with-event-wait ~connection ~domain ~event default-wait-ms ~body))
  ([connection domain event timeout-ms body]
   `(let [con# ~connection
          domain# ~domain
          event# ~event
          timeout-ms# ~timeout-ms
          ch# (listen con# domain# event#)
          p# (async/promise-chan)]
      ;; Fulfill promise channel from event channel
      (go (>! p# (<! ch#)))
      (try
        (let [response# ~body
              t# (async/timeout timeout-ms#)
              [_# chan#] (async/alts!! [t# p#])]
          (if (= chan# t#)
            (throw (ex-info "Timeout! Specified event was not received."
                            {:domain domain# :event event# :timeout timeout-ms#}))
            response#))
        (finally
          (unlisten con# domain# event# ch#)
          (async/close! ch#))))))
