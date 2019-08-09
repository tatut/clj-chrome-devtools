(ns clj-chrome-devtools.commands.animation
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(s/def
 ::animation
 (s/keys
  :req-un
  [::id
   ::name
   ::paused-state
   ::play-state
   ::playback-rate
   ::start-time
   ::current-time
   ::type]
  :opt-un
  [::source
   ::css-id]))

(s/def
 ::animation-effect
 (s/keys
  :req-un
  [::delay
   ::end-delay
   ::iteration-start
   ::iterations
   ::duration
   ::direction
   ::fill
   ::easing]
  :opt-un
  [::backend-node-id
   ::keyframes-rule]))

(s/def
 ::keyframes-rule
 (s/keys
  :req-un
  [::keyframes]
  :opt-un
  [::name]))

(s/def
 ::keyframe-style
 (s/keys
  :req-un
  [::offset
   ::easing]))
(defn
 disable
 "Disables animation domain notifications."
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
    "Animation.disable"
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
 enable
 "Enables animation domain notifications."
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
    "Animation.enable"
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
 get-current-time
 "Returns the current time of the an animation.\n\nParameters map keys:\n\n\n  Key | Description \n  ----|------------ \n  :id | Id of animation.\n\nReturn map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :current-time | Current time of the page."
 ([]
  (get-current-time
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [id]}]
  (get-current-time
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.getCurrentTime"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:id "id"})]
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
 get-current-time
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::id])))
 :ret
 (s/keys
  :req-un
  [::current-time]))

(defn
 get-playback-rate
 "Gets the playback rate of the document timeline.\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :playback-rate | Playback rate for animations on page."
 ([]
  (get-playback-rate
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-playback-rate
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.getPlaybackRate"
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
 get-playback-rate
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
  [::playback-rate]))

(defn
 release-animations
 "Releases a set of animations to no longer be manipulated.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :animations | List of animation ids to seek."
 ([]
  (release-animations
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [animations]}]
  (release-animations
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [animations]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.releaseAnimations"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:animations "animations"})]
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
 release-animations
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::animations]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::animations])))
 :ret
 (s/keys))

(defn
 resolve-animation
 "Gets the remote object of the Animation.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :animation-id | Animation id.\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :remote-object | Corresponding remote object."
 ([]
  (resolve-animation
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [animation-id]}]
  (resolve-animation
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [animation-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.resolveAnimation"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:animation-id "animationId"})]
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
 resolve-animation
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::animation-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::animation-id])))
 :ret
 (s/keys
  :req-un
  [::remote-object]))

(defn
 seek-animations
 "Seek a set of animations to a particular time within each animation.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :animations   | List of animation ids to seek.\n  :current-time | Set the current time of each animation."
 ([]
  (seek-animations
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [animations current-time]}]
  (seek-animations
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [animations current-time]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.seekAnimations"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:animations "animations", :current-time "currentTime"})]
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
 seek-animations
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::animations
     ::current-time]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::animations
     ::current-time])))
 :ret
 (s/keys))

(defn
 set-paused
 "Sets the paused state of a set of animations.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :animations | Animations to set the pause state of.\n  :paused     | Paused state to set to."
 ([]
  (set-paused
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [animations paused]}]
  (set-paused
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [animations paused]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.setPaused"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:animations "animations", :paused "paused"})]
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
 set-paused
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::animations
     ::paused]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::animations
     ::paused])))
 :ret
 (s/keys))

(defn
 set-playback-rate
 "Sets the playback rate of the document timeline.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :playback-rate | Playback rate for animations on page"
 ([]
  (set-playback-rate
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [playback-rate]}]
  (set-playback-rate
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [playback-rate]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.setPlaybackRate"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:playback-rate "playbackRate"})]
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
 set-playback-rate
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::playback-rate]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::playback-rate])))
 :ret
 (s/keys))

(defn
 set-timing
 "Sets the timing of an animation node.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :animation-id | Animation id.\n  :duration     | Duration of the animation.\n  :delay        | Delay of the animation."
 ([]
  (set-timing
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [animation-id duration delay]}]
  (set-timing
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [animation-id duration delay]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Animation.setTiming"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:animation-id "animationId",
      :duration "duration",
      :delay "delay"})]
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
 set-timing
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::animation-id
     ::duration
     ::delay]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::animation-id
     ::duration
     ::delay])))
 :ret
 (s/keys))
