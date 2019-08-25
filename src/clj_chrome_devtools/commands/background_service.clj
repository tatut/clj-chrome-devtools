(ns clj-chrome-devtools.commands.background-service
  "Defines events for background web platform features."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
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
  (cmd/command
   connection
   "BackgroundService"
   "startObserving"
   params
   {:service "service"})))

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
  (cmd/command
   connection
   "BackgroundService"
   "stopObserving"
   params
   {:service "service"})))

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
  (cmd/command
   connection
   "BackgroundService"
   "setRecording"
   params
   {:should-record "shouldRecord", :service "service"})))

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
  (cmd/command
   connection
   "BackgroundService"
   "clearEvents"
   params
   {:service "service"})))

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
