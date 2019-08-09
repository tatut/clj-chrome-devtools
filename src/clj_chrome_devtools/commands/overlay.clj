(ns clj-chrome-devtools.commands.overlay
  "This domain provides various functionality related to drawing atop the inspected page."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(s/def
 ::highlight-config
 (s/keys
  :opt-un
  [::show-info
   ::show-styles
   ::show-rulers
   ::show-extension-lines
   ::content-color
   ::padding-color
   ::border-color
   ::margin-color
   ::event-target-color
   ::shape-color
   ::shape-margin-color
   ::css-grid-color]))

(s/def
 ::inspect-mode
 #{"none" "searchForUAShadowDOM" "captureAreaScreenshot"
   "showDistances" "searchForNode"})
(defn
 disable
 "Disables domain notifications."
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
    "Overlay.disable"
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
 "Enables domain notifications."
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
    "Overlay.enable"
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
 get-highlight-object-for-test
 "For testing.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :node-id          | Id of the node to get highlight object for.\n  :include-distance | Whether to include distance info. (optional)\n  :include-style    | Whether to include style info. (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :highlight | Highlight data for the node."
 ([]
  (get-highlight-object-for-test
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id include-distance include-style]}]
  (get-highlight-object-for-test
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [node-id include-distance include-style]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.getHighlightObjectForTest"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :include-distance "includeDistance",
      :include-style "includeStyle"})]
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
 get-highlight-object-for-test
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id]
    :opt-un
    [::include-distance
     ::include-style]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id]
    :opt-un
    [::include-distance
     ::include-style])))
 :ret
 (s/keys
  :req-un
  [::highlight]))

(defn
 hide-highlight
 "Hides any highlight."
 ([]
  (hide-highlight
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (hide-highlight
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.hideHighlight"
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
 hide-highlight
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
 highlight-frame
 "Highlights owner element of the frame with given id.\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :frame-id              | Identifier of the frame to highlight.\n  :content-color         | The content box highlight fill color (default: transparent). (optional)\n  :content-outline-color | The content box highlight outline color (default: transparent). (optional)"
 ([]
  (highlight-frame
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id content-color content-outline-color]}]
  (highlight-frame
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [frame-id content-color content-outline-color]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.highlightFrame"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:frame-id "frameId",
      :content-color "contentColor",
      :content-outline-color "contentOutlineColor"})]
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
 highlight-frame
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id]
    :opt-un
    [::content-color
     ::content-outline-color]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id]
    :opt-un
    [::content-color
     ::content-outline-color])))
 :ret
 (s/keys))

(defn
 highlight-node
 "Highlights DOM node with given id or with the given JavaScript object wrapper. Either nodeId or\nobjectId must be specified.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :highlight-config | A descriptor for the highlight appearance.\n  :node-id          | Identifier of the node to highlight. (optional)\n  :backend-node-id  | Identifier of the backend node to highlight. (optional)\n  :object-id        | JavaScript object id of the node to be highlighted. (optional)\n  :selector         | Selectors to highlight relevant nodes. (optional)"
 ([]
  (highlight-node
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [highlight-config node-id backend-node-id object-id selector]}]
  (highlight-node
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [highlight-config node-id backend-node-id object-id selector]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.highlightNode"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:highlight-config "highlightConfig",
      :node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId",
      :selector "selector"})]
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
 highlight-node
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::highlight-config]
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id
     ::selector]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::highlight-config]
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id
     ::selector])))
 :ret
 (s/keys))

(defn
 highlight-quad
 "Highlights given quad. Coordinates are absolute with respect to the main frame viewport.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :quad          | Quad to highlight\n  :color         | The highlight fill color (default: transparent). (optional)\n  :outline-color | The highlight outline color (default: transparent). (optional)"
 ([]
  (highlight-quad
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [quad color outline-color]}]
  (highlight-quad
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [quad color outline-color]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.highlightQuad"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:quad "quad", :color "color", :outline-color "outlineColor"})]
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
 highlight-quad
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::quad]
    :opt-un
    [::color
     ::outline-color]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::quad]
    :opt-un
    [::color
     ::outline-color])))
 :ret
 (s/keys))

(defn
 highlight-rect
 "Highlights given rectangle. Coordinates are absolute with respect to the main frame viewport.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :x             | X coordinate\n  :y             | Y coordinate\n  :width         | Rectangle width\n  :height        | Rectangle height\n  :color         | The highlight fill color (default: transparent). (optional)\n  :outline-color | The highlight outline color (default: transparent). (optional)"
 ([]
  (highlight-rect
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [x y width height color outline-color]}]
  (highlight-rect
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [x y width height color outline-color]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.highlightRect"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:x "x",
      :y "y",
      :width "width",
      :height "height",
      :color "color",
      :outline-color "outlineColor"})]
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
 highlight-rect
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::x
     ::y
     ::width
     ::height]
    :opt-un
    [::color
     ::outline-color]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::x
     ::y
     ::width
     ::height]
    :opt-un
    [::color
     ::outline-color])))
 :ret
 (s/keys))

(defn
 set-inspect-mode
 "Enters the 'inspect' mode. In this mode, elements that user is hovering over are highlighted.\nBackend then generates 'inspectNodeRequested' event upon element selection.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :mode             | Set an inspection mode.\n  :highlight-config | A descriptor for the highlight appearance of hovered-over nodes. May be omitted if `enabled\n== false`. (optional)"
 ([]
  (set-inspect-mode
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [mode highlight-config]}]
  (set-inspect-mode
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [mode highlight-config]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setInspectMode"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:mode "mode", :highlight-config "highlightConfig"})]
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
 set-inspect-mode
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::mode]
    :opt-un
    [::highlight-config]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::mode]
    :opt-un
    [::highlight-config])))
 :ret
 (s/keys))

(defn
 set-show-ad-highlights
 "Highlights owner element of all frames detected to be ads.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :show | True for showing ad highlights"
 ([]
  (set-show-ad-highlights
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [show]}]
  (set-show-ad-highlights
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [show]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowAdHighlights"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:show "show"})]
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
 set-show-ad-highlights
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::show]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::show])))
 :ret
 (s/keys))

(defn
 set-paused-in-debugger-message
 "\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :message | The message to display, also triggers resume and step over controls. (optional)"
 ([]
  (set-paused-in-debugger-message
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [message]}]
  (set-paused-in-debugger-message
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [message]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setPausedInDebuggerMessage"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:message "message"})]
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
 set-paused-in-debugger-message
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::message]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::message])))
 :ret
 (s/keys))

(defn
 set-show-debug-borders
 "Requests that backend shows debug borders on layers\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :show | True for showing debug borders"
 ([]
  (set-show-debug-borders
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [show]}]
  (set-show-debug-borders
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [show]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowDebugBorders"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:show "show"})]
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
 set-show-debug-borders
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::show]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::show])))
 :ret
 (s/keys))

(defn
 set-show-fps-counter
 "Requests that backend shows the FPS counter\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :show | True for showing the FPS counter"
 ([]
  (set-show-fps-counter
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [show]}]
  (set-show-fps-counter
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [show]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowFPSCounter"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:show "show"})]
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
 set-show-fps-counter
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::show]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::show])))
 :ret
 (s/keys))

(defn
 set-show-paint-rects
 "Requests that backend shows paint rectangles\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | True for showing paint rectangles"
 ([]
  (set-show-paint-rects
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [result]}]
  (set-show-paint-rects
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [result]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowPaintRects"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:result "result"})]
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
 set-show-paint-rects
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::result]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::result])))
 :ret
 (s/keys))

(defn
 set-show-layout-shift-regions
 "Requests that backend shows layout shift regions\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | True for showing layout shift regions"
 ([]
  (set-show-layout-shift-regions
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [result]}]
  (set-show-layout-shift-regions
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [result]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowLayoutShiftRegions"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:result "result"})]
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
 set-show-layout-shift-regions
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::result]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::result])))
 :ret
 (s/keys))

(defn
 set-show-scroll-bottleneck-rects
 "Requests that backend shows scroll bottleneck rects\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :show | True for showing scroll bottleneck rects"
 ([]
  (set-show-scroll-bottleneck-rects
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [show]}]
  (set-show-scroll-bottleneck-rects
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [show]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowScrollBottleneckRects"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:show "show"})]
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
 set-show-scroll-bottleneck-rects
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::show]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::show])))
 :ret
 (s/keys))

(defn
 set-show-hit-test-borders
 "Requests that backend shows hit-test borders on layers\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :show | True for showing hit-test borders"
 ([]
  (set-show-hit-test-borders
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [show]}]
  (set-show-hit-test-borders
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [show]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowHitTestBorders"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:show "show"})]
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
 set-show-hit-test-borders
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::show]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::show])))
 :ret
 (s/keys))

(defn
 set-show-viewport-size-on-resize
 "Paints viewport size upon main frame resize.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :show | Whether to paint size or not."
 ([]
  (set-show-viewport-size-on-resize
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [show]}]
  (set-show-viewport-size-on-resize
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [show]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Overlay.setShowViewportSizeOnResize"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:show "show"})]
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
 set-show-viewport-size-on-resize
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::show]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::show])))
 :ret
 (s/keys))
