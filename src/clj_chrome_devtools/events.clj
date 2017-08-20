(ns clj-chrome-devtools.events
  "Listening to events coming from Chrome."
  (:require [clj-chrome-devtools.impl.connection :as connection]
            [clojure.core.async :as async :refer [go-loop go <! >! <!! >!!]]))

(def default-wait-ms 30000)

(defn listen-to
  "Subscribe to listen to given domain event. Both domain and event are keywords.
  Returns a core.async channel where events can be read from."
  [domain event]
  (connection/listen-to domain event))


(defn wait-for-event
  "Synchronously wait for an event with specific parameters."
  ([domain event params]
   (wait-for-event domain event params 30000))
  ([domain event params timeout-ms]
   (let [timeout-ch (async/timeout timeout-ms)
         ch (listen-to domain event)
         read! #(async/alts!! [timeout-ch ch])
         cleanup! #(async/close! ch)]
     (loop [[val chan] (read!)]
       (cond
         (= chan timeout-ch)
         (do (cleanup!)
             (throw (ex-info "Timeout! Specified event was not received."
                             {:domain domain :event event :params params
                              :timeout timeout-ms})))

            (= params (select-keys (:params val) (keys params)))
            (do (cleanup!)
                (:params val))

            :default
            (recur (read!)))))))

(defmacro with-event-wait
  "Execute body that causes some later events and wait for the event before returning.
  If the event is not received after waiting timeout-ms milliseconds, an exception is thrown."
  ([domain event body]
   `(with-event-wait ~domain ~event ~default-wait-ms ~body))
  ([domain event timeout-ms body]
   `(let [domain# ~domain
          event# ~event
          timeout-ms# ~timeout-ms
          ch# (listen-to domain# event#)
          p# (async/promise-chan)]
      ;; Fulfill promise channel from event channel
      (go (>! p# (<! ch#)))
      (let [response# ~body
            t# (async/timeout timeout-ms#)
            [_# chan#] (async/alts!! [t# p#])]
        (if (= chan# t#)
          (throw (ex-info "Timeout! Specified event was not received."
                          {:domain domain# :event event# :timeout timeout-ms#}))
          response#)))))
