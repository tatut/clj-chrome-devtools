(ns clj-chrome-devtools.commands.target
  "Supports additional targets discovery and allows to attach to them."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::target-id
 string?)

(s/def
 ::session-id
 string?)

(s/def
 ::target-info
 (s/keys
  :req-un
  [::target-id
   ::type
   ::title
   ::url
   ::attached
   ::can-access-opener]
  :opt-un
  [::opener-id
   ::opener-frame-id
   ::browser-context-id]))

(s/def
 ::remote-location
 (s/keys
  :req-un
  [::host
   ::port]))
(defn
 activate-target
 "Activates (focuses) the target.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :target-id | null"
 ([]
  (activate-target
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [target-id]}]
  (activate-target
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [target-id]}]
  (cmd/command
   connection
   "Target"
   "activateTarget"
   params
   {:target-id "targetId"})))

(s/fdef
 activate-target
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::target-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::target-id])))
 :ret
 (s/keys))

(defn
 attach-to-target
 "Attaches to the target with given id.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :target-id | null\n  :flatten   | Enables \"flat\" access to the session via specifying sessionId attribute in the commands.\nWe plan to make this the default, deprecate non-flattened mode,\nand eventually retire it. See crbug.com/991325. (optional)\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :session-id | Id assigned to the session."
 ([]
  (attach-to-target
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [target-id flatten]}]
  (attach-to-target
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [target-id flatten]}]
  (cmd/command
   connection
   "Target"
   "attachToTarget"
   params
   {:target-id "targetId", :flatten "flatten"})))

(s/fdef
 attach-to-target
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::target-id]
    :opt-un
    [::flatten]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::target-id]
    :opt-un
    [::flatten])))
 :ret
 (s/keys
  :req-un
  [::session-id]))

(defn
 attach-to-browser-target
 "Attaches to the browser target, only uses flat sessionId mode.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :session-id | Id assigned to the session."
 ([]
  (attach-to-browser-target
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (attach-to-browser-target
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Target"
   "attachToBrowserTarget"
   params
   {})))

(s/fdef
 attach-to-browser-target
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
  [::session-id]))

(defn
 close-target
 "Closes the target. If the target is a page that gets closed too.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :target-id | null\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :success | Always set to true. If an error occurs, the response indicates protocol error."
 ([]
  (close-target
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [target-id]}]
  (close-target
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [target-id]}]
  (cmd/command
   connection
   "Target"
   "closeTarget"
   params
   {:target-id "targetId"})))

(s/fdef
 close-target
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::target-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::target-id])))
 :ret
 (s/keys
  :req-un
  [::success]))

(defn
 expose-dev-tools-protocol
 "Inject object to the target's main frame that provides a communication\nchannel with browser target.\n\nInjected object will be available as `window[bindingName]`.\n\nThe object has the follwing API:\n- `binding.send(json)` - a method to send messages over the remote debugging protocol\n- `binding.onmessage = json => handleMessage(json)` - a callback that will be called for the protocol notifications and command responses.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :target-id    | null\n  :binding-name | Binding name, 'cdp' if not specified. (optional)"
 ([]
  (expose-dev-tools-protocol
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [target-id binding-name]}]
  (expose-dev-tools-protocol
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [target-id binding-name]}]
  (cmd/command
   connection
   "Target"
   "exposeDevToolsProtocol"
   params
   {:target-id "targetId", :binding-name "bindingName"})))

(s/fdef
 expose-dev-tools-protocol
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::target-id]
    :opt-un
    [::binding-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::target-id]
    :opt-un
    [::binding-name])))
 :ret
 (s/keys))

(defn
 create-browser-context
 "Creates a new empty BrowserContext. Similar to an incognito profile but you can have more than\none.\n\nParameters map keys:\n\n\n  Key                                    | Description \n  ---------------------------------------|------------ \n  :dispose-on-detach                     | If specified, disposes this context when debugging session disconnects. (optional)\n  :proxy-server                          | Proxy server, similar to the one passed to --proxy-server (optional)\n  :proxy-bypass-list                     | Proxy bypass list, similar to the one passed to --proxy-bypass-list (optional)\n  :origins-with-universal-network-access | An optional list of origins to grant unlimited cross-origin access to.\nParts of the URL other than those constituting origin are ignored. (optional)\n\nReturn map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :browser-context-id | The id of the context created."
 ([]
  (create-browser-context
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [dispose-on-detach
     proxy-server
     proxy-bypass-list
     origins-with-universal-network-access]}]
  (create-browser-context
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [dispose-on-detach
     proxy-server
     proxy-bypass-list
     origins-with-universal-network-access]}]
  (cmd/command
   connection
   "Target"
   "createBrowserContext"
   params
   {:dispose-on-detach "disposeOnDetach",
    :proxy-server "proxyServer",
    :proxy-bypass-list "proxyBypassList",
    :origins-with-universal-network-access
    "originsWithUniversalNetworkAccess"})))

(s/fdef
 create-browser-context
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::dispose-on-detach
     ::proxy-server
     ::proxy-bypass-list
     ::origins-with-universal-network-access]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::dispose-on-detach
     ::proxy-server
     ::proxy-bypass-list
     ::origins-with-universal-network-access])))
 :ret
 (s/keys
  :req-un
  [::browser-context-id]))

(defn
 get-browser-contexts
 "Returns all browser contexts created with `Target.createBrowserContext` method.\n\nReturn map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :browser-context-ids | An array of browser context ids."
 ([]
  (get-browser-contexts
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-browser-contexts
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Target"
   "getBrowserContexts"
   params
   {})))

(s/fdef
 get-browser-contexts
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
  [::browser-context-ids]))

(defn
 create-target
 "Creates a new page.\n\nParameters map keys:\n\n\n  Key                         | Description \n  ----------------------------|------------ \n  :url                        | The initial URL the page will be navigated to. An empty string indicates about:blank.\n  :width                      | Frame width in DIP (headless chrome only). (optional)\n  :height                     | Frame height in DIP (headless chrome only). (optional)\n  :browser-context-id         | The browser context to create the page in. (optional)\n  :enable-begin-frame-control | Whether BeginFrames for this target will be controlled via DevTools (headless chrome only,\nnot supported on MacOS yet, false by default). (optional)\n  :new-window                 | Whether to create a new Window or Tab (chrome-only, false by default). (optional)\n  :background                 | Whether to create the target in background or foreground (chrome-only,\nfalse by default). (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :target-id | The id of the page opened."
 ([]
  (create-target
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [url
     width
     height
     browser-context-id
     enable-begin-frame-control
     new-window
     background]}]
  (create-target
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [url
     width
     height
     browser-context-id
     enable-begin-frame-control
     new-window
     background]}]
  (cmd/command
   connection
   "Target"
   "createTarget"
   params
   {:url "url",
    :width "width",
    :height "height",
    :browser-context-id "browserContextId",
    :enable-begin-frame-control "enableBeginFrameControl",
    :new-window "newWindow",
    :background "background"})))

(s/fdef
 create-target
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::url]
    :opt-un
    [::width
     ::height
     ::browser-context-id
     ::enable-begin-frame-control
     ::new-window
     ::background]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::url]
    :opt-un
    [::width
     ::height
     ::browser-context-id
     ::enable-begin-frame-control
     ::new-window
     ::background])))
 :ret
 (s/keys
  :req-un
  [::target-id]))

(defn
 detach-from-target
 "Detaches session with given id.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :session-id | Session to detach. (optional)\n  :target-id  | Deprecated. (optional)"
 ([]
  (detach-from-target
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [session-id target-id]}]
  (detach-from-target
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [session-id target-id]}]
  (cmd/command
   connection
   "Target"
   "detachFromTarget"
   params
   {:session-id "sessionId", :target-id "targetId"})))

(s/fdef
 detach-from-target
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::session-id
     ::target-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::session-id
     ::target-id])))
 :ret
 (s/keys))

(defn
 dispose-browser-context
 "Deletes a BrowserContext. All the belonging pages will be closed without calling their\nbeforeunload hooks.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :browser-context-id | null"
 ([]
  (dispose-browser-context
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [browser-context-id]}]
  (dispose-browser-context
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [browser-context-id]}]
  (cmd/command
   connection
   "Target"
   "disposeBrowserContext"
   params
   {:browser-context-id "browserContextId"})))

(s/fdef
 dispose-browser-context
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::browser-context-id])))
 :ret
 (s/keys))

(defn
 get-target-info
 "Returns information about a target.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :target-id | null (optional)\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :target-info | null"
 ([]
  (get-target-info
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [target-id]}]
  (get-target-info
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [target-id]}]
  (cmd/command
   connection
   "Target"
   "getTargetInfo"
   params
   {:target-id "targetId"})))

(s/fdef
 get-target-info
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
  [::target-info]))

(defn
 get-targets
 "Retrieves a list of available targets.\n\nReturn map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :target-infos | The list of targets."
 ([]
  (get-targets
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-targets
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Target"
   "getTargets"
   params
   {})))

(s/fdef
 get-targets
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
  [::target-infos]))

(defn
 send-message-to-target
 "Sends protocol message over session with given id.\nConsider using flat mode instead; see commands attachToTarget, setAutoAttach,\nand crbug.com/991325.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :message    | null\n  :session-id | Identifier of the session. (optional)\n  :target-id  | Deprecated. (optional)"
 ([]
  (send-message-to-target
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [message session-id target-id]}]
  (send-message-to-target
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [message session-id target-id]}]
  (cmd/command
   connection
   "Target"
   "sendMessageToTarget"
   params
   {:message "message",
    :session-id "sessionId",
    :target-id "targetId"})))

(s/fdef
 send-message-to-target
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::message]
    :opt-un
    [::session-id
     ::target-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::message]
    :opt-un
    [::session-id
     ::target-id])))
 :ret
 (s/keys))

(defn
 set-auto-attach
 "Controls whether to automatically attach to new targets which are considered to be related to\nthis one. When turned on, attaches to all existing related targets as well. When turned off,\nautomatically detaches from all currently attached targets.\nThis also clears all targets added by `autoAttachRelated` from the list of targets to watch\nfor creation of related targets.\n\nParameters map keys:\n\n\n  Key                         | Description \n  ----------------------------|------------ \n  :auto-attach                | Whether to auto-attach to related targets.\n  :wait-for-debugger-on-start | Whether to pause new targets when attaching to them. Use `Runtime.runIfWaitingForDebugger`\nto run paused targets.\n  :flatten                    | Enables \"flat\" access to the session via specifying sessionId attribute in the commands.\nWe plan to make this the default, deprecate non-flattened mode,\nand eventually retire it. See crbug.com/991325. (optional)"
 ([]
  (set-auto-attach
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [auto-attach wait-for-debugger-on-start flatten]}]
  (set-auto-attach
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [auto-attach wait-for-debugger-on-start flatten]}]
  (cmd/command
   connection
   "Target"
   "setAutoAttach"
   params
   {:auto-attach "autoAttach",
    :wait-for-debugger-on-start "waitForDebuggerOnStart",
    :flatten "flatten"})))

(s/fdef
 set-auto-attach
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::auto-attach
     ::wait-for-debugger-on-start]
    :opt-un
    [::flatten]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::auto-attach
     ::wait-for-debugger-on-start]
    :opt-un
    [::flatten])))
 :ret
 (s/keys))

(defn
 auto-attach-related
 "Adds the specified target to the list of targets that will be monitored for any related target\ncreation (such as child frames, child workers and new versions of service worker) and reported\nthrough `attachedToTarget`. The specified target is also auto-attached.\nThis cancels the effect of any previous `setAutoAttach` and is also cancelled by subsequent\n`setAutoAttach`. Only available at the Browser target.\n\nParameters map keys:\n\n\n  Key                         | Description \n  ----------------------------|------------ \n  :target-id                  | null\n  :wait-for-debugger-on-start | Whether to pause new targets when attaching to them. Use `Runtime.runIfWaitingForDebugger`\nto run paused targets."
 ([]
  (auto-attach-related
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [target-id wait-for-debugger-on-start]}]
  (auto-attach-related
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [target-id wait-for-debugger-on-start]}]
  (cmd/command
   connection
   "Target"
   "autoAttachRelated"
   params
   {:target-id "targetId",
    :wait-for-debugger-on-start "waitForDebuggerOnStart"})))

(s/fdef
 auto-attach-related
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::target-id
     ::wait-for-debugger-on-start]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::target-id
     ::wait-for-debugger-on-start])))
 :ret
 (s/keys))

(defn
 set-discover-targets
 "Controls whether to discover available targets and notify via\n`targetCreated/targetInfoChanged/targetDestroyed` events.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :discover | Whether to discover available targets."
 ([]
  (set-discover-targets
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [discover]}]
  (set-discover-targets
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [discover]}]
  (cmd/command
   connection
   "Target"
   "setDiscoverTargets"
   params
   {:discover "discover"})))

(s/fdef
 set-discover-targets
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::discover]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::discover])))
 :ret
 (s/keys))

(defn
 set-remote-locations
 "Enables target discovery for the specified locations, when `setDiscoverTargets` was set to\n`true`.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :locations | List of remote locations."
 ([]
  (set-remote-locations
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [locations]}]
  (set-remote-locations
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [locations]}]
  (cmd/command
   connection
   "Target"
   "setRemoteLocations"
   params
   {:locations "locations"})))

(s/fdef
 set-remote-locations
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::locations]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::locations])))
 :ret
 (s/keys))
