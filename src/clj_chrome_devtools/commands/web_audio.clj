(ns clj-chrome-devtools.commands.web-audio
  "This domain allows inspection of Web Audio API.\nhttps://webaudio.github.io/web-audio-api/"
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (enable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "WebAudio.enable"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {})]
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
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 disable
 "Disables the WebAudio domain."
 ([]
  (disable
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (disable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "WebAudio.disable"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {})]
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
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 get-realtime-data
 "Fetch the realtime data from the registered contexts.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :context-id | null\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :realtime-data | null"
 ([]
  (get-realtime-data
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [context-id]}]
  (get-realtime-data
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [context-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "WebAudio.getRealtimeData"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:context-id "contextId"})]
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
    c/connection?)
   :params
   (s/keys
    :req-un
    [::context-id])))
 :ret
 (s/keys
  :req-un
  [::realtime-data]))
