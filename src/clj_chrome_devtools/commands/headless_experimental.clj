(ns clj-chrome-devtools.commands.headless-experimental
  "This domain provides experimental commands only supported in headless mode."
  (:require [clojure.spec.alpha :as s]))
(s/def
 ::screenshot-params
 (s/keys
  :opt-un
  [::format
   ::quality]))
(defn
 begin-frame
 "Sends a BeginFrame to the target and returns when the frame was completed. Optionally captures a\nscreenshot from the resulting frame. Requires that the target was created with enabled\nBeginFrameControl. Designed for use with --run-all-compositor-stages-before-draw, see also\nhttps://goo.gl/3zHXhB for more background.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :frame-time-ticks   | Timestamp of this BeginFrame in Renderer TimeTicks (milliseconds of uptime). If not set,\nthe current time will be used. (optional)\n  :interval           | The interval between BeginFrames that is reported to the compositor, in milliseconds.\nDefaults to a 60 frames/second interval, i.e. about 16.666 milliseconds. (optional)\n  :no-display-updates | Whether updates should not be committed and drawn onto the display. False by default. If\ntrue, only side effects of the BeginFrame will be run, such as layout and animations, but\nany visual updates may not be visible on the display or in screenshots. (optional)\n  :screenshot         | If set, a screenshot of the frame will be captured and returned in the response. Otherwise,\nno screenshot will be captured. Note that capturing a screenshot can fail, for example,\nduring renderer initialization. In such a case, no screenshot data will be returned. (optional)\n\nReturn map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :has-damage      | Whether the BeginFrame resulted in damage and, thus, a new frame was committed to the\ndisplay. Reported for diagnostic uses, may be removed in the future.\n  :screenshot-data | Base64-encoded image data of the screenshot, if one was requested and successfully taken. (optional)"
 ([]
  (begin-frame
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys [frame-time-ticks interval no-display-updates screenshot]}]
  (begin-frame
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [frame-time-ticks interval no-display-updates screenshot]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "HeadlessExperimental.beginFrame"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:frame-time-ticks "frameTimeTicks",
      :interval "interval",
      :no-display-updates "noDisplayUpdates",
      :screenshot "screenshot"})]
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
 begin-frame
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::frame-time-ticks
     ::interval
     ::no-display-updates
     ::screenshot]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [::frame-time-ticks
     ::interval
     ::no-display-updates
     ::screenshot])))
 :ret
 (s/keys
  :req-un
  [::has-damage]
  :opt-un
  [::screenshot-data]))

(defn
 disable
 "Disables headless events for the target."
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
    "HeadlessExperimental.disable"
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
 enable
 "Enables headless events for the target."
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
    "HeadlessExperimental.enable"
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
