(ns clj-chrome-devtools.commands.web-audio
  "This domain allows inspection of Web Audio API.\nhttps://webaudio.github.io/web-audio-api/"
  (:require [clojure.spec.alpha :as s]))
(s/def
 ::graph-object-id
 string?)

(s/def
 ::context-type
 #{"realtime" "offline"})

(s/def
 ::context-state
 #{"running" "closed" "suspended"})

(s/def
 ::node-type
 string?)

(s/def
 ::channel-count-mode
 #{"max" "clamped-max" "explicit"})

(s/def
 ::channel-interpretation
 #{"speakers" "discrete"})

(s/def
 ::param-type
 string?)

(s/def
 ::automation-rate
 #{"k-rate" "a-rate"})

(s/def
 ::context-realtime-data
 (s/keys
  :req-un
  [::current-time
   ::render-capacity
   ::callback-interval-mean
   ::callback-interval-variance]))

(s/def
 ::base-audio-context
 (s/keys
  :req-un
  [::context-id
   ::context-type
   ::context-state
   ::callback-buffer-size
   ::max-output-channel-count
   ::sample-rate]
  :opt-un
  [::realtime-data]))

(s/def
 ::audio-listener
 (s/keys
  :req-un
  [::listener-id
   ::context-id]))

(s/def
 ::audio-node
 (s/keys
  :req-un
  [::node-id
   ::context-id
   ::node-type
   ::number-of-inputs
   ::number-of-outputs
   ::channel-count
   ::channel-count-mode
   ::channel-interpretation]))

(s/def
 ::audio-param
 (s/keys
  :req-un
  [::param-id
   ::node-id
   ::context-id
   ::param-type
   ::rate
   ::default-value
   ::min-value
   ::max-value]))
(defn
 enable
 "Enables the WebAudio domain and starts sending context lifetime events."
 ([]
  (enable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (enable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "WebAudio.enable"
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
 enable
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
 disable
 "Disables the WebAudio domain."
 ([]
  (disable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (disable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "WebAudio.disable"
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
 disable
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
 get-realtime-data
 "Fetch the realtime data from the registered contexts.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :context-id | null\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :realtime-data | null"
 ([]
  (get-realtime-data
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [context-id]}]
  (get-realtime-data
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [context-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "WebAudio.getRealtimeData"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:context-id "contextId"})]
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
 get-realtime-data
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::context-id])))
 :ret
 (s/keys
  :req-un
  [::realtime-data]))
