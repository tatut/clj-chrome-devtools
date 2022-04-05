(ns clj-chrome-devtools.commands.accessibility
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::ax-node-id
 string?)

(s/def
 ::ax-value-type
 #{"boolean" "domRelation" "role" "token" "string" "tokenList"
   "valueUndefined" "tristate" "number" "idref" "integer"
   "internalRole" "computedString" "idrefList" "nodeList"
   "booleanOrUndefined" "node"})

(s/def
 ::ax-value-source-type
 #{"placeholder" "attribute" "implicit" "style" "contents"
   "relatedElement"})

(s/def
 ::ax-value-native-source-type
 #{"label" "legend" "labelwrapped" "figcaption" "rubyannotation"
   "title" "tablecaption" "other" "labelfor" "description"})

(s/def
 ::ax-value-source
 (s/keys
  :req-un
  [::type]
  :opt-un
  [::value
   ::attribute
   ::attribute-value
   ::superseded
   ::native-source
   ::native-source-value
   ::invalid
   ::invalid-reason]))

(s/def
 ::ax-related-node
 (s/keys
  :req-un
  [::backend-dom-node-id]
  :opt-un
  [::idref
   ::text]))

(s/def
 ::ax-property
 (s/keys
  :req-un
  [::name
   ::value]))

(s/def
 ::ax-value
 (s/keys
  :req-un
  [::type]
  :opt-un
  [::value
   ::related-nodes
   ::sources]))

(s/def
 ::ax-property-name
 #{"editable" "readonly" "activedescendant" "selected" "hasPopup"
   "autocomplete" "pressed" "multiline" "settable" "multiselectable"
   "valuetext" "modal" "root" "invalid" "valuemin" "level"
   "describedby" "busy" "valuemax" "focused" "hiddenRoot" "required"
   "controls" "details" "hidden" "roledescription" "atomic" "flowto"
   "disabled" "relevant" "keyshortcuts" "owns" "live" "labelledby"
   "expanded" "checked" "errormessage" "orientation" "focusable"})

(s/def
 ::ax-node
 (s/keys
  :req-un
  [::node-id
   ::ignored]
  :opt-un
  [::ignored-reasons
   ::role
   ::name
   ::description
   ::value
   ::properties
   ::parent-id
   ::child-ids
   ::backend-dom-node-id
   ::frame-id]))
(defn
 disable
 "Disables the accessibility domain."
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
   "Accessibility"
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
 "Enables the accessibility domain which causes `AXNodeId`s to remain consistent between method calls.\nThis turns on accessibility for the page, which can impact performance until accessibility is disabled."
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
   "Accessibility"
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
 get-partial-ax-tree
 "Fetches the accessibility node and partial accessibility tree for this DOM node, if it exists.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node to get the partial accessibility tree for. (optional)\n  :backend-node-id | Identifier of the backend node to get the partial accessibility tree for. (optional)\n  :object-id       | JavaScript object id of the node wrapper to get the partial accessibility tree for. (optional)\n  :fetch-relatives | Whether to fetch this nodes ancestors, siblings and children. Defaults to true. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :nodes | The `Accessibility.AXNode` for this DOM node, if it exists, plus its ancestors, siblings and\nchildren, if requested."
 ([]
  (get-partial-ax-tree
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [node-id backend-node-id object-id fetch-relatives]}]
  (get-partial-ax-tree
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [node-id backend-node-id object-id fetch-relatives]}]
  (cmd/command
   connection
   "Accessibility"
   "getPartialAXTree"
   params
   {:node-id "nodeId",
    :backend-node-id "backendNodeId",
    :object-id "objectId",
    :fetch-relatives "fetchRelatives"})))

(s/fdef
 get-partial-ax-tree
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id
     ::fetch-relatives]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id
     ::fetch-relatives])))
 :ret
 (s/keys
  :req-un
  [::nodes]))

(defn
 get-full-ax-tree
 "Fetches the entire accessibility tree for the root Document\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :depth    | The maximum depth at which descendants of the root node should be retrieved.\nIf omitted, the full tree is returned. (optional)\n  :frame-id | The frame for whose document the AX tree should be retrieved.\nIf omited, the root frame is used. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :nodes | null"
 ([]
  (get-full-ax-tree
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [depth frame-id]}]
  (get-full-ax-tree
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [depth frame-id]}]
  (cmd/command
   connection
   "Accessibility"
   "getFullAXTree"
   params
   {:depth "depth", :frame-id "frameId"})))

(s/fdef
 get-full-ax-tree
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::depth
     ::frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::depth
     ::frame-id])))
 :ret
 (s/keys
  :req-un
  [::nodes]))

(defn
 get-root-ax-node
 "Fetches the root node.\nRequires `enable()` to have been called previously.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | The frame in whose document the node resides.\nIf omitted, the root frame is used. (optional)\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :node | null"
 ([]
  (get-root-ax-node
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id]}]
  (get-root-ax-node
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id]}]
  (cmd/command
   connection
   "Accessibility"
   "getRootAXNode"
   params
   {:frame-id "frameId"})))

(s/fdef
 get-root-ax-node
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::frame-id])))
 :ret
 (s/keys
  :req-un
  [::node]))

(defn
 get-ax-node-and-ancestors
 "Fetches a node and all ancestors up to and including the root.\nRequires `enable()` to have been called previously.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node to get. (optional)\n  :backend-node-id | Identifier of the backend node to get. (optional)\n  :object-id       | JavaScript object id of the node wrapper to get. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :nodes | null"
 ([]
  (get-ax-node-and-ancestors
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id backend-node-id object-id]}]
  (get-ax-node-and-ancestors
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id backend-node-id object-id]}]
  (cmd/command
   connection
   "Accessibility"
   "getAXNodeAndAncestors"
   params
   {:node-id "nodeId",
    :backend-node-id "backendNodeId",
    :object-id "objectId"})))

(s/fdef
 get-ax-node-and-ancestors
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id])))
 :ret
 (s/keys
  :req-un
  [::nodes]))

(defn
 get-child-ax-nodes
 "Fetches a particular accessibility node by AXNodeId.\nRequires `enable()` to have been called previously.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :id       | null\n  :frame-id | The frame in whose document the node resides.\nIf omitted, the root frame is used. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :nodes | null"
 ([]
  (get-child-ax-nodes
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [id frame-id]}]
  (get-child-ax-nodes
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [id frame-id]}]
  (cmd/command
   connection
   "Accessibility"
   "getChildAXNodes"
   params
   {:id "id", :frame-id "frameId"})))

(s/fdef
 get-child-ax-nodes
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::id]
    :opt-un
    [::frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::id]
    :opt-un
    [::frame-id])))
 :ret
 (s/keys
  :req-un
  [::nodes]))

(defn
 query-ax-tree
 "Query a DOM node's accessibility subtree for accessible name and role.\nThis command computes the name and role for all nodes in the subtree, including those that are\nignored for accessibility, and returns those that mactch the specified name and role. If no DOM\nnode is specified, or the DOM node does not exist, the command returns an error. If neither\n`accessibleName` or `role` is specified, it returns all the accessibility nodes in the subtree.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node for the root to query. (optional)\n  :backend-node-id | Identifier of the backend node for the root to query. (optional)\n  :object-id       | JavaScript object id of the node wrapper for the root to query. (optional)\n  :accessible-name | Find nodes with this computed name. (optional)\n  :role            | Find nodes with this computed role. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :nodes | A list of `Accessibility.AXNode` matching the specified attributes,\nincluding nodes that are ignored for accessibility."
 ([]
  (query-ax-tree
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [node-id backend-node-id object-id accessible-name role]}]
  (query-ax-tree
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [node-id backend-node-id object-id accessible-name role]}]
  (cmd/command
   connection
   "Accessibility"
   "queryAXTree"
   params
   {:node-id "nodeId",
    :backend-node-id "backendNodeId",
    :object-id "objectId",
    :accessible-name "accessibleName",
    :role "role"})))

(s/fdef
 query-ax-tree
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id
     ::accessible-name
     ::role]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::node-id
     ::backend-node-id
     ::object-id
     ::accessible-name
     ::role])))
 :ret
 (s/keys
  :req-un
  [::nodes]))
