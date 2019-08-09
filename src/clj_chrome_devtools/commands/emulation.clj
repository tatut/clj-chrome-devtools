(ns clj-chrome-devtools.commands.emulation
  "This domain emulates different environments for the page."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::screen-orientation
 (s/keys
  :req-un
  [::type
   ::angle]))

(s/def
 ::virtual-time-policy
 #{"advance" "pauseIfNetworkFetchesPending" "pause"})
(defn
 can-emulate
 "Tells whether emulation is supported.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | True if emulation is supported."
 ([]
  (can-emulate
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (can-emulate
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.canEmulate"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 can-emulate
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
  [::result]))

(defn
 clear-device-metrics-override
 "Clears the overriden device metrics."
 ([]
  (clear-device-metrics-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-device-metrics-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.clearDeviceMetricsOverride"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 clear-device-metrics-override
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
 clear-geolocation-override
 "Clears the overriden Geolocation Position and Error."
 ([]
  (clear-geolocation-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-geolocation-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.clearGeolocationOverride"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 clear-geolocation-override
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
 reset-page-scale-factor
 "Requests that page scale factor is reset to initial values."
 ([]
  (reset-page-scale-factor
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (reset-page-scale-factor
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.resetPageScaleFactor"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 reset-page-scale-factor
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
 set-focus-emulation-enabled
 "Enables or disables simulating a focused and active page.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | Whether to enable to disable focus emulation."
 ([]
  (set-focus-emulation-enabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-focus-emulation-enabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setFocusEmulationEnabled"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:enabled "enabled"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-focus-emulation-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::enabled])))
 :ret
 (s/keys))

(defn
 set-cpu-throttling-rate
 "Enables CPU throttling to emulate slow CPUs.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :rate | Throttling rate as a slowdown factor (1 is no throttle, 2 is 2x slowdown, etc)."
 ([]
  (set-cpu-throttling-rate
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [rate]}]
  (set-cpu-throttling-rate
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [rate]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setCPUThrottlingRate"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:rate "rate"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-cpu-throttling-rate
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::rate]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::rate])))
 :ret
 (s/keys))

(defn
 set-default-background-color-override
 "Sets or clears an override of the default background color of the frame. This override is used\nif the content does not specify one.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :color | RGBA of the default background color. If not specified, any existing override will be\ncleared. (optional)"
 ([]
  (set-default-background-color-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [color]}]
  (set-default-background-color-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [color]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setDefaultBackgroundColorOverride"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:color "color"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-default-background-color-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::color]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::color])))
 :ret
 (s/keys))

(defn
 set-device-metrics-override
 "Overrides the values of device screen dimensions (window.screen.width, window.screen.height,\nwindow.innerWidth, window.innerHeight, and \"device-width\"/\"device-height\"-related CSS media\nquery results).\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :width                 | Overriding width value in pixels (minimum 0, maximum 10000000). 0 disables the override.\n  :height                | Overriding height value in pixels (minimum 0, maximum 10000000). 0 disables the override.\n  :device-scale-factor   | Overriding device scale factor value. 0 disables the override.\n  :mobile                | Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text\nautosizing and more.\n  :scale                 | Scale to apply to resulting view image. (optional)\n  :screen-width          | Overriding screen width value in pixels (minimum 0, maximum 10000000). (optional)\n  :screen-height         | Overriding screen height value in pixels (minimum 0, maximum 10000000). (optional)\n  :position-x            | Overriding view X position on screen in pixels (minimum 0, maximum 10000000). (optional)\n  :position-y            | Overriding view Y position on screen in pixels (minimum 0, maximum 10000000). (optional)\n  :dont-set-visible-size | Do not set visible view size, rely upon explicit setVisibleSize call. (optional)\n  :screen-orientation    | Screen orientation override. (optional)\n  :viewport              | If set, the visible area of the page will be overridden to this viewport. This viewport\nchange is not observed by the page, e.g. viewport-relative elements do not change positions. (optional)"
 ([]
  (set-device-metrics-override
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [width
     height
     device-scale-factor
     mobile
     scale
     screen-width
     screen-height
     position-x
     position-y
     dont-set-visible-size
     screen-orientation
     viewport]}]
  (set-device-metrics-override
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [width
     height
     device-scale-factor
     mobile
     scale
     screen-width
     screen-height
     position-x
     position-y
     dont-set-visible-size
     screen-orientation
     viewport]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setDeviceMetricsOverride"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:dont-set-visible-size "dontSetVisibleSize",
      :device-scale-factor "deviceScaleFactor",
      :screen-orientation "screenOrientation",
      :scale "scale",
      :width "width",
      :position-y "positionY",
      :position-x "positionX",
      :screen-height "screenHeight",
      :mobile "mobile",
      :viewport "viewport",
      :height "height",
      :screen-width "screenWidth"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-device-metrics-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::width
     ::height
     ::device-scale-factor
     ::mobile]
    :opt-un
    [::scale
     ::screen-width
     ::screen-height
     ::position-x
     ::position-y
     ::dont-set-visible-size
     ::screen-orientation
     ::viewport]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::width
     ::height
     ::device-scale-factor
     ::mobile]
    :opt-un
    [::scale
     ::screen-width
     ::screen-height
     ::position-x
     ::position-y
     ::dont-set-visible-size
     ::screen-orientation
     ::viewport])))
 :ret
 (s/keys))

(defn
 set-scrollbars-hidden
 "\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :hidden | Whether scrollbars should be always hidden."
 ([]
  (set-scrollbars-hidden
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [hidden]}]
  (set-scrollbars-hidden
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [hidden]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setScrollbarsHidden"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:hidden "hidden"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-scrollbars-hidden
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::hidden]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::hidden])))
 :ret
 (s/keys))

(defn
 set-document-cookie-disabled
 "\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :disabled | Whether document.coookie API should be disabled."
 ([]
  (set-document-cookie-disabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [disabled]}]
  (set-document-cookie-disabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [disabled]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setDocumentCookieDisabled"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:disabled "disabled"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-document-cookie-disabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::disabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::disabled])))
 :ret
 (s/keys))

(defn
 set-emit-touch-events-for-mouse
 "\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :enabled       | Whether touch emulation based on mouse input should be enabled.\n  :configuration | Touch/gesture events configuration. Default: current platform. (optional)"
 ([]
  (set-emit-touch-events-for-mouse
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled configuration]}]
  (set-emit-touch-events-for-mouse
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled configuration]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setEmitTouchEventsForMouse"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:enabled "enabled", :configuration "configuration"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-emit-touch-events-for-mouse
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enabled]
    :opt-un
    [::configuration]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::enabled]
    :opt-un
    [::configuration])))
 :ret
 (s/keys))

(defn
 set-emulated-media
 "Emulates the given media for CSS media queries.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :media | Media type to emulate. Empty string disables the override."
 ([]
  (set-emulated-media
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [media]}]
  (set-emulated-media
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [media]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setEmulatedMedia"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:media "media"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-emulated-media
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::media]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::media])))
 :ret
 (s/keys))

(defn
 set-geolocation-override
 "Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position\nunavailable.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :latitude  | Mock latitude (optional)\n  :longitude | Mock longitude (optional)\n  :accuracy  | Mock accuracy (optional)"
 ([]
  (set-geolocation-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [latitude longitude accuracy]}]
  (set-geolocation-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [latitude longitude accuracy]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setGeolocationOverride"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:latitude "latitude",
      :longitude "longitude",
      :accuracy "accuracy"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-geolocation-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::latitude
     ::longitude
     ::accuracy]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::latitude
     ::longitude
     ::accuracy])))
 :ret
 (s/keys))

(defn
 set-navigator-overrides
 "Overrides value returned by the javascript navigator object.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :platform | The platform navigator.platform should return."
 ([]
  (set-navigator-overrides
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [platform]}]
  (set-navigator-overrides
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [platform]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setNavigatorOverrides"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:platform "platform"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-navigator-overrides
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::platform]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::platform])))
 :ret
 (s/keys))

(defn
 set-page-scale-factor
 "Sets a specified page scale factor.\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :page-scale-factor | Page scale factor."
 ([]
  (set-page-scale-factor
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [page-scale-factor]}]
  (set-page-scale-factor
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [page-scale-factor]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setPageScaleFactor"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:page-scale-factor "pageScaleFactor"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-page-scale-factor
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::page-scale-factor]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::page-scale-factor])))
 :ret
 (s/keys))

(defn
 set-script-execution-disabled
 "Switches script execution in the page.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :value | Whether script execution should be disabled in the page."
 ([]
  (set-script-execution-disabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [value]}]
  (set-script-execution-disabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [value]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setScriptExecutionDisabled"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:value "value"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-script-execution-disabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::value]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::value])))
 :ret
 (s/keys))

(defn
 set-touch-emulation-enabled
 "Enables touch on platforms which do not support them.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :enabled          | Whether the touch event emulation should be enabled.\n  :max-touch-points | Maximum touch points supported. Defaults to one. (optional)"
 ([]
  (set-touch-emulation-enabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled max-touch-points]}]
  (set-touch-emulation-enabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled max-touch-points]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setTouchEmulationEnabled"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:enabled "enabled", :max-touch-points "maxTouchPoints"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-touch-emulation-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enabled]
    :opt-un
    [::max-touch-points]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::enabled]
    :opt-un
    [::max-touch-points])))
 :ret
 (s/keys))

(defn
 set-virtual-time-policy
 "Turns on virtual time for all frames (replacing real-time with a synthetic time source) and sets\nthe current virtual time policy.  Note this supersedes any previous time budget.\n\nParameters map keys:\n\n\n  Key                                     | Description \n  ----------------------------------------|------------ \n  :policy                                 | null\n  :budget                                 | If set, after this many virtual milliseconds have elapsed virtual time will be paused and a\nvirtualTimeBudgetExpired event is sent. (optional)\n  :max-virtual-time-task-starvation-count | If set this specifies the maximum number of tasks that can be run before virtual is forced\nforwards to prevent deadlock. (optional)\n  :wait-for-navigation                    | If set the virtual time policy change should be deferred until any frame starts navigating.\nNote any previous deferred policy change is superseded. (optional)\n  :initial-virtual-time                   | If set, base::Time::Now will be overriden to initially return this value. (optional)\n\nReturn map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :virtual-time-ticks-base | Absolute timestamp at which virtual time was first enabled (up time in milliseconds)."
 ([]
  (set-virtual-time-policy
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [policy
     budget
     max-virtual-time-task-starvation-count
     wait-for-navigation
     initial-virtual-time]}]
  (set-virtual-time-policy
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [policy
     budget
     max-virtual-time-task-starvation-count
     wait-for-navigation
     initial-virtual-time]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setVirtualTimePolicy"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:policy "policy",
      :budget "budget",
      :max-virtual-time-task-starvation-count
      "maxVirtualTimeTaskStarvationCount",
      :wait-for-navigation "waitForNavigation",
      :initial-virtual-time "initialVirtualTime"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-virtual-time-policy
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::policy]
    :opt-un
    [::budget
     ::max-virtual-time-task-starvation-count
     ::wait-for-navigation
     ::initial-virtual-time]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::policy]
    :opt-un
    [::budget
     ::max-virtual-time-task-starvation-count
     ::wait-for-navigation
     ::initial-virtual-time])))
 :ret
 (s/keys
  :req-un
  [::virtual-time-ticks-base]))

(defn
 set-timezone-override
 "Overrides default host system timezone with the specified one.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :timezone-id | The timezone identifier. If empty, disables the override and\nrestores default host system timezone."
 ([]
  (set-timezone-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [timezone-id]}]
  (set-timezone-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [timezone-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setTimezoneOverride"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:timezone-id "timezoneId"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-timezone-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::timezone-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::timezone-id])))
 :ret
 (s/keys))

(defn
 set-visible-size
 "Resizes the frame/viewport of the page. Note that this does not affect the frame's container\n(e.g. browser window). Can be used to produce screenshots of the specified size. Not supported\non Android.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :width  | Frame width (DIP).\n  :height | Frame height (DIP)."
 ([]
  (set-visible-size
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [width height]}]
  (set-visible-size
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [width height]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setVisibleSize"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:width "width", :height "height"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-visible-size
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::width
     ::height]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::width
     ::height])))
 :ret
 (s/keys))

(defn
 set-user-agent-override
 "Allows overriding user agent with the given string.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :user-agent      | User agent to use.\n  :accept-language | Browser langugage to emulate. (optional)\n  :platform        | The platform navigator.platform should return. (optional)"
 ([]
  (set-user-agent-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [user-agent accept-language platform]}]
  (set-user-agent-override
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [user-agent accept-language platform]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Emulation.setUserAgentOverride"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:user-agent "userAgent",
      :accept-language "acceptLanguage",
      :platform "platform"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

(s/fdef
 set-user-agent-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::user-agent]
    :opt-un
    [::accept-language
     ::platform]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::user-agent]
    :opt-un
    [::accept-language
     ::platform])))
 :ret
 (s/keys))
