(ns clj-chrome-devtools.commands.memory
  (:require [clojure.spec.alpha :as s]))
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-dom-counters
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.getDOMCounters"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (prepare-for-leak-detection
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.prepareForLeakDetection"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 forcibly-purge-java-script-memory
 "Simulate OomIntervention by purging V8 memory."
 ([]
  (forcibly-purge-java-script-memory
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (forcibly-purge-java-script-memory
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.forciblyPurgeJavaScriptMemory"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 set-pressure-notifications-suppressed
 "Enable/disable suppressing memory pressure notifications in all processes.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :suppressed | If true, memory pressure notifications will be suppressed."
 ([]
  (set-pressure-notifications-suppressed
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [suppressed]}]
  (set-pressure-notifications-suppressed
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [suppressed]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.setPressureNotificationsSuppressed"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:suppressed "suppressed"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [level]}]
  (simulate-pressure-notification
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [level]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.simulatePressureNotification"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:level "level"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [sampling-interval suppress-randomness]}]
  (start-sampling
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [sampling-interval suppress-randomness]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.startSampling"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:sampling-interval "samplingInterval",
      :suppress-randomness "suppressRandomness"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-sampling
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.stopSampling"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 get-all-time-sampling-profile
 "Retrieve native memory allocations profile\ncollected since renderer process startup.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :profile | null"
 ([]
  (get-all-time-sampling-profile
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-all-time-sampling-profile
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.getAllTimeSamplingProfile"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-browser-sampling-profile
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.getBrowserSamplingProfile"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-sampling-profile
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Memory.getSamplingProfile"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::profile]))
