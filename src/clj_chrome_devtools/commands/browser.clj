(ns clj-chrome-devtools.commands.browser
  "The Browser domain defines methods and events for browser managing."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::window-id
 integer?)

(s/def
 ::window-state
 #{"normal" "fullscreen" "maximized" "minimized"})

(s/def
 ::bounds
 (s/keys
  :opt-un
  [::left
   ::top
   ::width
   ::height
   ::window-state]))

(s/def
 ::permission-type
 #{"paymentHandler" "clipboardWrite" "notifications" "audioCapture"
   "idleDetection" "geolocation" "wakeLockScreen" "sensors"
   "videoCapture" "accessibilityEvents" "wakeLockSystem"
   "backgroundSync" "durableStorage" "midi" "flash" "clipboardRead"
   "periodicBackgroundSync" "backgroundFetch" "midiSysex"
   "protectedMediaIdentifier"})

(s/def
 ::permission-setting
 #{"granted" "prompt" "denied"})

(s/def
 ::permission-descriptor
 (s/keys
  :req-un
  [::name]
  :opt-un
  [::sysex
   ::user-visible-only
   ::type]))

(s/def
 ::bucket
 (s/keys
  :req-un
  [::low
   ::high
   ::count]))

(s/def
 ::histogram
 (s/keys
  :req-un
  [::name
   ::sum
   ::count
   ::buckets]))
(defn
 set-permission
 "Set permission settings for given origin.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :origin             | Origin the permission applies to.\n  :permission         | Descriptor of permission to override.\n  :setting            | Setting of the permission.\n  :browser-context-id | Context to override. When omitted, default browser context is used. (optional)"
 ([]
  (set-permission
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin permission setting browser-context-id]}]
  (set-permission
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [origin permission setting browser-context-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.setPermission"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:origin "origin",
      :permission "permission",
      :setting "setting",
      :browser-context-id "browserContextId"})]
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
 set-permission
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin
     ::permission
     ::setting]
    :opt-un
    [::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin
     ::permission
     ::setting]
    :opt-un
    [::browser-context-id])))
 :ret
 (s/keys))

(defn
 grant-permissions
 "Grant specific permissions to the given origin and reject all others.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :origin             | null\n  :permissions        | null\n  :browser-context-id | BrowserContext to override permissions. When omitted, default browser context is used. (optional)"
 ([]
  (grant-permissions
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin permissions browser-context-id]}]
  (grant-permissions
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [origin permissions browser-context-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.grantPermissions"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:origin "origin",
      :permissions "permissions",
      :browser-context-id "browserContextId"})]
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
 grant-permissions
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin
     ::permissions]
    :opt-un
    [::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin
     ::permissions]
    :opt-un
    [::browser-context-id])))
 :ret
 (s/keys))

(defn
 reset-permissions
 "Reset all permission management for all origins.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :browser-context-id | BrowserContext to reset permissions. When omitted, default browser context is used. (optional)"
 ([]
  (reset-permissions
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [browser-context-id]}]
  (reset-permissions
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [browser-context-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.resetPermissions"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:browser-context-id "browserContextId"})]
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
 reset-permissions
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::browser-context-id])))
 :ret
 (s/keys))

(defn
 close
 "Close browser gracefully."
 ([]
  (close
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (close
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.close"
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
 close
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
 crash
 "Crashes browser on the main thread."
 ([]
  (crash
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (crash
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.crash"
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
 crash
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
 crash-gpu-process
 "Crashes GPU process."
 ([]
  (crash-gpu-process
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (crash-gpu-process
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.crashGpuProcess"
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
 crash-gpu-process
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
 get-version
 "Returns version information.\n\nReturn map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :protocol-version | Protocol version.\n  :product          | Product name.\n  :revision         | Product revision.\n  :user-agent       | User-Agent.\n  :js-version       | V8 version."
 ([]
  (get-version
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-version
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.getVersion"
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
 get-version
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
  [::protocol-version
   ::product
   ::revision
   ::user-agent
   ::js-version]))

(defn
 get-browser-command-line
 "Returns the command line switches for the browser process if, and only if\n--enable-automation is on the commandline.\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :arguments | Commandline parameters"
 ([]
  (get-browser-command-line
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-browser-command-line
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.getBrowserCommandLine"
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
 get-browser-command-line
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
  [::arguments]))

(defn
 get-histograms
 "Get Chrome histograms.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :query | Requested substring in name. Only histograms which have query as a\nsubstring in their name are extracted. An empty or absent query returns\nall histograms. (optional)\n  :delta | If true, retrieve delta since last call. (optional)\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :histograms | Histograms."
 ([]
  (get-histograms
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [query delta]}]
  (get-histograms
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [query delta]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.getHistograms"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:query "query", :delta "delta"})]
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
 get-histograms
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::query
     ::delta]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::query
     ::delta])))
 :ret
 (s/keys
  :req-un
  [::histograms]))

(defn
 get-histogram
 "Get a Chrome histogram by name.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :name  | Requested histogram name.\n  :delta | If true, retrieve delta since last call. (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :histogram | Histogram."
 ([]
  (get-histogram
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [name delta]}]
  (get-histogram
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [name delta]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.getHistogram"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:name "name", :delta "delta"})]
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
 get-histogram
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::name]
    :opt-un
    [::delta]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::name]
    :opt-un
    [::delta])))
 :ret
 (s/keys
  :req-un
  [::histogram]))

(defn
 get-window-bounds
 "Get position and size of the browser window.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :window-id | Browser window id.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :bounds | Bounds information of the window. When window state is 'minimized', the restored window\nposition and size are returned."
 ([]
  (get-window-bounds
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [window-id]}]
  (get-window-bounds
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [window-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.getWindowBounds"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:window-id "windowId"})]
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
 get-window-bounds
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::window-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::window-id])))
 :ret
 (s/keys
  :req-un
  [::bounds]))

(defn
 get-window-for-target
 "Get the browser window that contains the devtools target.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :target-id | Devtools agent host id. If called as a part of the session, associated targetId is used. (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :window-id | Browser window id.\n  :bounds    | Bounds information of the window. When window state is 'minimized', the restored window\nposition and size are returned."
 ([]
  (get-window-for-target
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [target-id]}]
  (get-window-for-target
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [target-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.getWindowForTarget"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:target-id "targetId"})]
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
 get-window-for-target
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::target-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::target-id])))
 :ret
 (s/keys
  :req-un
  [::window-id
   ::bounds]))

(defn
 set-window-bounds
 "Set position and/or size of the browser window.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :window-id | Browser window id.\n  :bounds    | New window bounds. The 'minimized', 'maximized' and 'fullscreen' states cannot be combined\nwith 'left', 'top', 'width' or 'height'. Leaves unspecified fields unchanged."
 ([]
  (set-window-bounds
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [window-id bounds]}]
  (set-window-bounds
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [window-id bounds]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.setWindowBounds"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:window-id "windowId", :bounds "bounds"})]
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
 set-window-bounds
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::window-id
     ::bounds]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::window-id
     ::bounds])))
 :ret
 (s/keys))

(defn
 set-dock-tile
 "Set dock tile details, platform-specific.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :badge-label | null (optional)\n  :image       | Png encoded image. (optional)"
 ([]
  (set-dock-tile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [badge-label image]}]
  (set-dock-tile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [badge-label image]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Browser.setDockTile"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:badge-label "badgeLabel", :image "image"})]
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
 set-dock-tile
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::badge-label
     ::image]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::badge-label
     ::image])))
 :ret
 (s/keys))
