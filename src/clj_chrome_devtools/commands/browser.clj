(ns clj-chrome-devtools.commands.browser
  "The Browser domain defines methods and events for browser managing."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::browser-context-id
 string?)

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
 #{"clipboardReadWrite" "paymentHandler" "notifications" "audioCapture"
   "idleDetection" "nfc" "geolocation" "wakeLockScreen" "sensors"
   "videoCapture" "accessibilityEvents" "wakeLockSystem"
   "backgroundSync" "displayCapture" "durableStorage" "midi"
   "videoCapturePanTiltZoom" "flash" "periodicBackgroundSync"
   "clipboardSanitizedWrite" "backgroundFetch" "midiSysex"
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
   ::allow-without-sanitization
   ::pan-tilt-zoom]))

(s/def
 ::browser-command-id
 #{"closeTabSearch" "openTabSearch"})

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
 "Set permission settings for given origin.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :permission         | Descriptor of permission to override.\n  :setting            | Setting of the permission.\n  :origin             | Origin the permission applies to, all origins if not specified. (optional)\n  :browser-context-id | Context to override. When omitted, default browser context is used. (optional)"
 ([]
  (set-permission
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [permission setting origin browser-context-id]}]
  (set-permission
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [permission setting origin browser-context-id]}]
  (cmd/command
   connection
   "Browser"
   "setPermission"
   params
   {:permission "permission",
    :setting "setting",
    :origin "origin",
    :browser-context-id "browserContextId"})))

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
    [::permission
     ::setting]
    :opt-un
    [::origin
     ::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::permission
     ::setting]
    :opt-un
    [::origin
     ::browser-context-id])))
 :ret
 (s/keys))

(defn
 grant-permissions
 "Grant specific permissions to the given origin and reject all others.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :permissions        | null\n  :origin             | Origin the permission applies to, all origins if not specified. (optional)\n  :browser-context-id | BrowserContext to override permissions. When omitted, default browser context is used. (optional)"
 ([]
  (grant-permissions
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [permissions origin browser-context-id]}]
  (grant-permissions
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [permissions origin browser-context-id]}]
  (cmd/command
   connection
   "Browser"
   "grantPermissions"
   params
   {:permissions "permissions",
    :origin "origin",
    :browser-context-id "browserContextId"})))

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
    [::permissions]
    :opt-un
    [::origin
     ::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::permissions]
    :opt-un
    [::origin
     ::browser-context-id])))
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
  (cmd/command
   connection
   "Browser"
   "resetPermissions"
   params
   {:browser-context-id "browserContextId"})))

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
 set-download-behavior
 "Set the behavior when downloading a file.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :behavior           | Whether to allow all or deny all download requests, or use default Chrome behavior if\navailable (otherwise deny). |allowAndName| allows download and names files according to\ntheir dowmload guids.\n  :browser-context-id | BrowserContext to set download behavior. When omitted, default browser context is used. (optional)\n  :download-path      | The default path to save downloaded files to. This is required if behavior is set to 'allow'\nor 'allowAndName'. (optional)\n  :events-enabled     | Whether to emit download events (defaults to false). (optional)"
 ([]
  (set-download-behavior
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [behavior browser-context-id download-path events-enabled]}]
  (set-download-behavior
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [behavior browser-context-id download-path events-enabled]}]
  (cmd/command
   connection
   "Browser"
   "setDownloadBehavior"
   params
   {:behavior "behavior",
    :browser-context-id "browserContextId",
    :download-path "downloadPath",
    :events-enabled "eventsEnabled"})))

(s/fdef
 set-download-behavior
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::behavior]
    :opt-un
    [::browser-context-id
     ::download-path
     ::events-enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::behavior]
    :opt-un
    [::browser-context-id
     ::download-path
     ::events-enabled])))
 :ret
 (s/keys))

(defn
 cancel-download
 "Cancel a download if in progress\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :guid               | Global unique identifier of the download.\n  :browser-context-id | BrowserContext to perform the action in. When omitted, default browser context is used. (optional)"
 ([]
  (cancel-download
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [guid browser-context-id]}]
  (cancel-download
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [guid browser-context-id]}]
  (cmd/command
   connection
   "Browser"
   "cancelDownload"
   params
   {:guid "guid", :browser-context-id "browserContextId"})))

(s/fdef
 cancel-download
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::guid]
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
    [::guid]
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
  (cmd/command
   connection
   "Browser"
   "close"
   params
   {})))

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
  (cmd/command
   connection
   "Browser"
   "crash"
   params
   {})))

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
  (cmd/command
   connection
   "Browser"
   "crashGpuProcess"
   params
   {})))

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
  (cmd/command
   connection
   "Browser"
   "getVersion"
   params
   {})))

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
  (cmd/command
   connection
   "Browser"
   "getBrowserCommandLine"
   params
   {})))

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
  (cmd/command
   connection
   "Browser"
   "getHistograms"
   params
   {:query "query", :delta "delta"})))

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
  (cmd/command
   connection
   "Browser"
   "getHistogram"
   params
   {:name "name", :delta "delta"})))

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
  (cmd/command
   connection
   "Browser"
   "getWindowBounds"
   params
   {:window-id "windowId"})))

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
  (cmd/command
   connection
   "Browser"
   "getWindowForTarget"
   params
   {:target-id "targetId"})))

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
  (cmd/command
   connection
   "Browser"
   "setWindowBounds"
   params
   {:window-id "windowId", :bounds "bounds"})))

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
 "Set dock tile details, platform-specific.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :badge-label | null (optional)\n  :image       | Png encoded image. (Encoded as a base64 string when passed over JSON) (optional)"
 ([]
  (set-dock-tile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [badge-label image]}]
  (set-dock-tile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [badge-label image]}]
  (cmd/command
   connection
   "Browser"
   "setDockTile"
   params
   {:badge-label "badgeLabel", :image "image"})))

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

(defn
 execute-browser-command
 "Invoke custom browser commands used by telemetry.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :command-id | null"
 ([]
  (execute-browser-command
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [command-id]}]
  (execute-browser-command
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [command-id]}]
  (cmd/command
   connection
   "Browser"
   "executeBrowserCommand"
   params
   {:command-id "commandId"})))

(s/fdef
 execute-browser-command
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::command-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::command-id])))
 :ret
 (s/keys))
