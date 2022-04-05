(ns clj-chrome-devtools.commands.dom-snapshot
  "This domain facilitates obtaining document snapshots with DOM, layout, and style information."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::dom-node
 (s/keys
  :req-un
  [::node-type
   ::node-name
   ::node-value
   ::backend-node-id]
  :opt-un
  [::text-value
   ::input-value
   ::input-checked
   ::option-selected
   ::child-node-indexes
   ::attributes
   ::pseudo-element-indexes
   ::layout-node-index
   ::document-url
   ::base-url
   ::content-language
   ::document-encoding
   ::public-id
   ::system-id
   ::frame-id
   ::content-document-index
   ::pseudo-type
   ::shadow-root-type
   ::is-clickable
   ::event-listeners
   ::current-source-url
   ::origin-url
   ::scroll-offset-x
   ::scroll-offset-y]))

(s/def
 ::inline-text-box
 (s/keys
  :req-un
  [::bounding-box
   ::start-character-index
   ::num-characters]))

(s/def
 ::layout-tree-node
 (s/keys
  :req-un
  [::dom-node-index
   ::bounding-box]
  :opt-un
  [::layout-text
   ::inline-text-nodes
   ::style-index
   ::paint-order
   ::is-stacking-context]))

(s/def
 ::computed-style
 (s/keys
  :req-un
  [::properties]))

(s/def
 ::name-value
 (s/keys
  :req-un
  [::name
   ::value]))

(s/def
 ::string-index
 integer?)

(s/def
 ::array-of-strings
 (s/coll-of any?))

(s/def
 ::rare-string-data
 (s/keys
  :req-un
  [::index
   ::value]))

(s/def
 ::rare-boolean-data
 (s/keys
  :req-un
  [::index]))

(s/def
 ::rare-integer-data
 (s/keys
  :req-un
  [::index
   ::value]))

(s/def
 ::rectangle
 (s/coll-of number?))

(s/def
 ::document-snapshot
 (s/keys
  :req-un
  [::document-url
   ::title
   ::base-url
   ::content-language
   ::encoding-name
   ::public-id
   ::system-id
   ::frame-id
   ::nodes
   ::layout
   ::text-boxes]
  :opt-un
  [::scroll-offset-x
   ::scroll-offset-y
   ::content-width
   ::content-height]))

(s/def
 ::node-tree-snapshot
 (s/keys
  :opt-un
  [::parent-index
   ::node-type
   ::shadow-root-type
   ::node-name
   ::node-value
   ::backend-node-id
   ::attributes
   ::text-value
   ::input-value
   ::input-checked
   ::option-selected
   ::content-document-index
   ::pseudo-type
   ::is-clickable
   ::current-source-url
   ::origin-url]))

(s/def
 ::layout-tree-snapshot
 (s/keys
  :req-un
  [::node-index
   ::styles
   ::bounds
   ::text
   ::stacking-contexts]
  :opt-un
  [::paint-orders
   ::offset-rects
   ::scroll-rects
   ::client-rects
   ::blended-background-colors
   ::text-color-opacities]))

(s/def
 ::text-box-snapshot
 (s/keys
  :req-un
  [::layout-index
   ::bounds
   ::start
   ::length]))
(defn
 disable
 "Disables DOM snapshot agent for the given page."
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
   "DOMSnapshot"
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
 "Enables DOM snapshot agent for the given page."
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
   "DOMSnapshot"
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
 get-snapshot
 "Returns a document snapshot, including the full DOM tree of the root node (including iframes,\ntemplate contents, and imported documents) in a flattened array, as well as layout and\nwhite-listed computed style information for the nodes. Shadow DOM in the returned DOM tree is\nflattened.\n\nParameters map keys:\n\n\n  Key                             | Description \n  --------------------------------|------------ \n  :computed-style-whitelist       | Whitelist of computed styles to return.\n  :include-event-listeners        | Whether or not to retrieve details of DOM listeners (default false). (optional)\n  :include-paint-order            | Whether to determine and include the paint order index of LayoutTreeNodes (default false). (optional)\n  :include-user-agent-shadow-tree | Whether to include UA shadow tree in the snapshot (default false). (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :dom-nodes         | The nodes in the DOM tree. The DOMNode at index 0 corresponds to the root document.\n  :layout-tree-nodes | The nodes in the layout tree.\n  :computed-styles   | Whitelisted ComputedStyle properties for each node in the layout tree."
 ([]
  (get-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [computed-style-whitelist
     include-event-listeners
     include-paint-order
     include-user-agent-shadow-tree]}]
  (get-snapshot
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [computed-style-whitelist
     include-event-listeners
     include-paint-order
     include-user-agent-shadow-tree]}]
  (cmd/command
   connection
   "DOMSnapshot"
   "getSnapshot"
   params
   {:computed-style-whitelist "computedStyleWhitelist",
    :include-event-listeners "includeEventListeners",
    :include-paint-order "includePaintOrder",
    :include-user-agent-shadow-tree "includeUserAgentShadowTree"})))

(s/fdef
 get-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::computed-style-whitelist]
    :opt-un
    [::include-event-listeners
     ::include-paint-order
     ::include-user-agent-shadow-tree]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::computed-style-whitelist]
    :opt-un
    [::include-event-listeners
     ::include-paint-order
     ::include-user-agent-shadow-tree])))
 :ret
 (s/keys
  :req-un
  [::dom-nodes
   ::layout-tree-nodes
   ::computed-styles]))

(defn
 capture-snapshot
 "Returns a document snapshot, including the full DOM tree of the root node (including iframes,\ntemplate contents, and imported documents) in a flattened array, as well as layout and\nwhite-listed computed style information for the nodes. Shadow DOM in the returned DOM tree is\nflattened.\n\nParameters map keys:\n\n\n  Key                                | Description \n  -----------------------------------|------------ \n  :computed-styles                   | Whitelist of computed styles to return.\n  :include-paint-order               | Whether to include layout object paint orders into the snapshot. (optional)\n  :include-dom-rects                 | Whether to include DOM rectangles (offsetRects, clientRects, scrollRects) into the snapshot (optional)\n  :include-blended-background-colors | Whether to include blended background colors in the snapshot (default: false).\nBlended background color is achieved by blending background colors of all elements\nthat overlap with the current element. (optional)\n  :include-text-color-opacities      | Whether to include text color opacity in the snapshot (default: false).\nAn element might have the opacity property set that affects the text color of the element.\nThe final text color opacity is computed based on the opacity of all overlapping elements. (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :documents | The nodes in the DOM tree. The DOMNode at index 0 corresponds to the root document.\n  :strings   | Shared string table that all string properties refer to with indexes."
 ([]
  (capture-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [computed-styles
     include-paint-order
     include-dom-rects
     include-blended-background-colors
     include-text-color-opacities]}]
  (capture-snapshot
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [computed-styles
     include-paint-order
     include-dom-rects
     include-blended-background-colors
     include-text-color-opacities]}]
  (cmd/command
   connection
   "DOMSnapshot"
   "captureSnapshot"
   params
   {:computed-styles "computedStyles",
    :include-paint-order "includePaintOrder",
    :include-dom-rects "includeDOMRects",
    :include-blended-background-colors
    "includeBlendedBackgroundColors",
    :include-text-color-opacities "includeTextColorOpacities"})))

(s/fdef
 capture-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::computed-styles]
    :opt-un
    [::include-paint-order
     ::include-dom-rects
     ::include-blended-background-colors
     ::include-text-color-opacities]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::computed-styles]
    :opt-un
    [::include-paint-order
     ::include-dom-rects
     ::include-blended-background-colors
     ::include-text-color-opacities])))
 :ret
 (s/keys
  :req-un
  [::documents
   ::strings]))
