(ns clj-chrome-devtools.commands.overlay
  "This domain provides various functionality related to drawing atop the inspected page."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
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
  (cmd/command
   connection
   "Overlay"
   "disable"
   params
   {})))

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
  (cmd/command
   connection
   "Overlay"
   "enable"
   params
   {})))

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
  (cmd/command
   connection
   "Overlay"
   "getHighlightObjectForTest"
   params
   {:node-id "nodeId",
    :include-distance "includeDistance",
    :include-style "includeStyle"})))

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
  (cmd/command
   connection
   "Overlay"
   "hideHighlight"
   params
   {})))

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
  (cmd/command
   connection
   "Overlay"
   "highlightFrame"
   params
   {:frame-id "frameId",
    :content-color "contentColor",
    :content-outline-color "contentOutlineColor"})))

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
  (cmd/command
   connection
   "Overlay"
   "highlightNode"
   params
   {:highlight-config "highlightConfig",
    :node-id "nodeId",
    :backend-node-id "backendNodeId",
    :object-id "objectId",
    :selector "selector"})))

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
  (cmd/command
   connection
   "Overlay"
   "highlightQuad"
   params
   {:quad "quad", :color "color", :outline-color "outlineColor"})))

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
  (cmd/command
   connection
   "Overlay"
   "highlightRect"
   params
   {:x "x",
    :y "y",
    :width "width",
    :height "height",
    :color "color",
    :outline-color "outlineColor"})))

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
  (cmd/command
   connection
   "Overlay"
   "setInspectMode"
   params
   {:mode "mode", :highlight-config "highlightConfig"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowAdHighlights"
   params
   {:show "show"})))

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
  (cmd/command
   connection
   "Overlay"
   "setPausedInDebuggerMessage"
   params
   {:message "message"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowDebugBorders"
   params
   {:show "show"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowFPSCounter"
   params
   {:show "show"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowPaintRects"
   params
   {:result "result"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowLayoutShiftRegions"
   params
   {:result "result"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowScrollBottleneckRects"
   params
   {:show "show"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowHitTestBorders"
   params
   {:show "show"})))

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
  (cmd/command
   connection
   "Overlay"
   "setShowViewportSizeOnResize"
   params
   {:show "show"})))

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
