(ns clj-chrome-devtools.commands.dom-snapshot
  "This domain facilitates obtaining document snapshots with DOM, layout, and style information."
  (:require [clojure.spec.alpha :as s]))
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
   ::scroll-offset-y]))

(s/def
 ::node-tree-snapshot
 (s/keys
  :opt-un
  [::parent-index
   ::node-type
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
  [::offset-rects
   ::scroll-rects
   ::client-rects]))

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
    "DOMSnapshot.disable"
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
 "Enables DOM snapshot agent for the given page."
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
    "DOMSnapshot.enable"
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

(defn
 get-snapshot
 "Returns a document snapshot, including the full DOM tree of the root node (including iframes,\ntemplate contents, and imported documents) in a flattened array, as well as layout and\nwhite-listed computed style information for the nodes. Shadow DOM in the returned DOM tree is\nflattened.\n\nParameters map keys:\n\n\n  Key                             | Description \n  --------------------------------|------------ \n  :computed-style-whitelist       | Whitelist of computed styles to return.\n  :include-event-listeners        | Whether or not to retrieve details of DOM listeners (default false). (optional)\n  :include-paint-order            | Whether to determine and include the paint order index of LayoutTreeNodes (default false). (optional)\n  :include-user-agent-shadow-tree | Whether to include UA shadow tree in the snapshot (default false). (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :dom-nodes         | The nodes in the DOM tree. The DOMNode at index 0 corresponds to the root document.\n  :layout-tree-nodes | The nodes in the layout tree.\n  :computed-styles   | Whitelisted ComputedStyle properties for each node in the layout tree."
 ([]
  (get-snapshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [computed-style-whitelist
     include-event-listeners
     include-paint-order
     include-user-agent-shadow-tree]}]
  (get-snapshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [computed-style-whitelist
     include-event-listeners
     include-paint-order
     include-user-agent-shadow-tree]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "DOMSnapshot.getSnapshot"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:computed-style-whitelist "computedStyleWhitelist",
      :include-event-listeners "includeEventListeners",
      :include-paint-order "includePaintOrder",
      :include-user-agent-shadow-tree "includeUserAgentShadowTree"})]
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
    clj-chrome-devtools.impl.connection/connection?)
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
 "Returns a document snapshot, including the full DOM tree of the root node (including iframes,\ntemplate contents, and imported documents) in a flattened array, as well as layout and\nwhite-listed computed style information for the nodes. Shadow DOM in the returned DOM tree is\nflattened.\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :computed-styles   | Whitelist of computed styles to return.\n  :include-dom-rects | Whether to include DOM rectangles (offsetRects, clientRects, scrollRects) into the snapshot (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :documents | The nodes in the DOM tree. The DOMNode at index 0 corresponds to the root document.\n  :strings   | Shared string table that all string properties refer to with indexes."
 ([]
  (capture-snapshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [computed-styles include-dom-rects]}]
  (capture-snapshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [computed-styles include-dom-rects]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "DOMSnapshot.captureSnapshot"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:computed-styles "computedStyles",
      :include-dom-rects "includeDOMRects"})]
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
    [::include-dom-rects]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::computed-styles]
    :opt-un
    [::include-dom-rects])))
 :ret
 (s/keys
  :req-un
  [::documents
   ::strings]))
