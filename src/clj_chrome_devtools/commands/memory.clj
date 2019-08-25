(ns clj-chrome-devtools.commands.memory
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::pressure-level
 #{"critical" "moderate"})

(s/def
 ::sampling-profile-node
 (s/keys
  :req-un
  [::size
   ::total
   ::stack]))

(s/def
 ::sampling-profile
 (s/keys
  :req-un
  [::samples
   ::modules]))

(s/def
 ::module
 (s/keys
  :req-un
  [::name
   ::uuid
   ::base-address
   ::size]))
(defn
 get-dom-counters
 "\n\nReturn map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :documents          | null\n  :nodes              | null\n  :js-event-listeners | null"
 ([]
  (get-dom-counters
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-dom-counters
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Memory"
   "getDOMCounters"
   params
   {})))

(s/fdef
 get-dom-counters
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::documents
   ::nodes
   ::js-event-listeners]))

(defn
 prepare-for-leak-detection
 ""
 ([]
  (prepare-for-leak-detection
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (prepare-for-leak-detection
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Memory"
   "prepareForLeakDetection"
   params
   {})))

(s/fdef
 prepare-for-leak-detection
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 forcibly-purge-java-script-memory
 "Simulate OomIntervention by purging V8 memory."
 ([]
  (forcibly-purge-java-script-memory
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (forcibly-purge-java-script-memory
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Memory"
   "forciblyPurgeJavaScriptMemory"
   params
   {})))

(s/fdef
 forcibly-purge-java-script-memory
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 set-pressure-notifications-suppressed
 "Enable/disable suppressing memory pressure notifications in all processes.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :suppressed | If true, memory pressure notifications will be suppressed."
 ([]
  (set-pressure-notifications-suppressed
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [suppressed]}]
  (set-pressure-notifications-suppressed
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [suppressed]}]
  (cmd/command
   connection
   "Memory"
   "setPressureNotificationsSuppressed"
   params
   {:suppressed "suppressed"})))

(s/fdef
 set-pressure-notifications-suppressed
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::suppressed]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::suppressed])))
 :ret
 (s/keys))

(defn
 simulate-pressure-notification
 "Simulate a memory pressure notification in all processes.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :level | Memory pressure level of the notification."
 ([]
  (simulate-pressure-notification
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [level]}]
  (simulate-pressure-notification
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [level]}]
  (cmd/command
   connection
   "Memory"
   "simulatePressureNotification"
   params
   {:level "level"})))

(s/fdef
 simulate-pressure-notification
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::level]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::level])))
 :ret
 (s/keys))

(defn
 start-sampling
 "Start collecting native memory profile.\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :sampling-interval   | Average number of bytes between samples. (optional)\n  :suppress-randomness | Do not randomize intervals between samples. (optional)"
 ([]
  (start-sampling
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [sampling-interval suppress-randomness]}]
  (start-sampling
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [sampling-interval suppress-randomness]}]
  (cmd/command
   connection
   "Memory"
   "startSampling"
   params
   {:sampling-interval "samplingInterval",
    :suppress-randomness "suppressRandomness"})))

(s/fdef
 start-sampling
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::sampling-interval
     ::suppress-randomness]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::sampling-interval
     ::suppress-randomness])))
 :ret
 (s/keys))

(defn
 stop-sampling
 "Stop collecting native memory profile."
 ([]
  (stop-sampling
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-sampling
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Memory"
   "stopSampling"
   params
   {})))

(s/fdef
 stop-sampling
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 get-all-time-sampling-profile
 "Retrieve native memory allocations profile\ncollected since renderer process startup.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :profile | null"
 ([]
  (get-all-time-sampling-profile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-all-time-sampling-profile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Memory"
   "getAllTimeSamplingProfile"
   params
   {})))

(s/fdef
 get-all-time-sampling-profile
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::profile]))

(defn
 get-browser-sampling-profile
 "Retrieve native memory allocations profile\ncollected since browser process startup.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :profile | null"
 ([]
  (get-browser-sampling-profile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-browser-sampling-profile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Memory"
   "getBrowserSamplingProfile"
   params
   {})))

(s/fdef
 get-browser-sampling-profile
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::profile]))

(defn
 get-sampling-profile
 "Retrieve native memory allocations profile collected since last\n`startSampling` call.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :profile | null"
 ([]
  (get-sampling-profile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-sampling-profile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Memory"
   "getSamplingProfile"
   params
   {})))

(s/fdef
 get-sampling-profile
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::profile]))
