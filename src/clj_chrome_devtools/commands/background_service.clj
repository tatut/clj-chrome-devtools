(ns clj-chrome-devtools.commands.background-service
  "Defines events for background web platform features."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(s/def
 ::service-name
 #{"pushMessaging" "paymentHandler" "notifications" "backgroundSync"
   "periodicBackgroundSync" "backgroundFetch"})

(s/def
 ::event-metadata
 (s/keys
  :req-un
  [::key
   ::value]))

(s/def
 ::background-service-event
 (s/keys
  :req-un
  [::timestamp
   ::origin
   ::service-worker-registration-id
   ::service
   ::event-name
   ::instance-id
   ::event-metadata]))
(defn
 start-observing
 "Enables event updates for the service.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :service | null"
 ([]
  (start-observing
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [service]}]
  (start-observing
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [service]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "BackgroundService.startObserving"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:service "service"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 start-observing
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::service]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::service])))
 :ret
 (s/keys))

(defn
 stop-observing
 "Disables event updates for the service.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :service | null"
 ([]
  (stop-observing
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [service]}]
  (stop-observing
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [service]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "BackgroundService.stopObserving"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:service "service"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 stop-observing
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::service]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::service])))
 :ret
 (s/keys))

(defn
 set-recording
 "Set the recording state for the service.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :should-record | null\n  :service       | null"
 ([]
  (set-recording
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [should-record service]}]
  (set-recording
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [should-record service]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "BackgroundService.setRecording"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:should-record "shouldRecord", :service "service"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 set-recording
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::should-record
     ::service]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::should-record
     ::service])))
 :ret
 (s/keys))

(defn
 clear-events
 "Clears all stored data for the service.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :service | null"
 ([]
  (clear-events
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [service]}]
  (clear-events
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [service]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "BackgroundService.clearEvents"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:service "service"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 clear-events
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::service]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::service])))
 :ret
 (s/keys))
