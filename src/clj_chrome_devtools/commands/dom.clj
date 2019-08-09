(ns clj-chrome-devtools.commands.dom
  "This domain exposes DOM read/write operations. Each DOM Node is represented with its mirror object\nthat has an `id`. This `id` can be used to get additional information on the Node, resolve it into\nthe JavaScript object wrapper, etc. It is important that client receives DOM events only for the\nnodes that are known to the client. Backend keeps track of the nodes that were sent to the client\nand never sends the same node twice. It is client's responsibility to collect information about\nthe nodes that were sent to the client.<p>Note that `iframe` owner elements will return\ncorresponding document elements as their child nodes.</p>"
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(s/def
 ::node-id
 integer?)

(s/def
 ::backend-node-id
 integer?)

(s/def
 ::backend-node
 (s/keys
  :req-un
  [::node-type
   ::node-name
   ::backend-node-id]))

(s/def
 ::pseudo-type
 #{"input-list-button" "first-line" "after" "scrollbar-track-piece"
   "backdrop" "first-line-inherited" "resizer" "scrollbar-corner"
   "first-letter" "scrollbar-button" "scrollbar" "scrollbar-track"
   "selection" "before" "scrollbar-thumb"})

(s/def
 ::shadow-root-type
 #{"user-agent" "closed" "open"})

(s/def
 ::node
 (s/keys
  :req-un
  [::node-id
   ::backend-node-id
   ::node-type
   ::node-name
   ::local-name
   ::node-value]
  :opt-un
  [::parent-id
   ::child-node-count
   ::children
   ::attributes
   ::document-url
   ::base-url
   ::public-id
   ::system-id
   ::internal-subset
   ::xml-version
   ::name
   ::value
   ::pseudo-type
   ::shadow-root-type
   ::frame-id
   ::content-document
   ::shadow-roots
   ::template-content
   ::pseudo-elements
   ::imported-document
   ::distributed-nodes
   ::is-svg]))

(s/def
 ::rgba
 (s/keys
  :req-un
  [::r
   ::g
   ::b]
  :opt-un
  [::a]))

(s/def
 ::quad
 (s/coll-of number?))

(s/def
 ::box-model
 (s/keys
  :req-un
  [::content
   ::padding
   ::border
   ::margin
   ::width
   ::height]
  :opt-un
  [::shape-outside]))

(s/def
 ::shape-outside-info
 (s/keys
  :req-un
  [::bounds
   ::shape
   ::margin-shape]))

(s/def
 ::rect
 (s/keys
  :req-un
  [::x
   ::y
   ::width
   ::height]))
(defn
 collect-class-names-from-subtree
 "Collects class names for the node with given id and all of it's child nodes.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node to collect class names.\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :class-names | Class name list."
 ([]
  (collect-class-names-from-subtree
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id]}]
  (collect-class-names-from-subtree
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.collectClassNamesFromSubtree"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId"})]
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
 collect-class-names-from-subtree
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id])))
 :ret
 (s/keys
  :req-un
  [::class-names]))

(defn
 copy-to
 "Creates a deep copy of the specified node and places it into the target container before the\ngiven anchor.\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :node-id               | Id of the node to copy.\n  :target-node-id        | Id of the element to drop the copy into.\n  :insert-before-node-id | Drop the copy before this node (if absent, the copy becomes the last child of\n`targetNodeId`). (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node clone."
 ([]
  (copy-to
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id target-node-id insert-before-node-id]}]
  (copy-to
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [node-id target-node-id insert-before-node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.copyTo"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :target-node-id "targetNodeId",
      :insert-before-node-id "insertBeforeNodeId"})]
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
 copy-to
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id
     ::target-node-id]
    :opt-un
    [::insert-before-node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id
     ::target-node-id]
    :opt-un
    [::insert-before-node-id])))
 :ret
 (s/keys
  :req-un
  [::node-id]))

(defn
 describe-node
 "Describes node given its id, does not require domain to be enabled. Does not start tracking any\nobjects, can be used for automation.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node. (optional)\n  :backend-node-id | Identifier of the backend node. (optional)\n  :object-id       | JavaScript object id of the node wrapper. (optional)\n  :depth           | The maximum depth at which children should be retrieved, defaults to 1. Use -1 for the\nentire subtree or provide an integer larger than 0. (optional)\n  :pierce          | Whether or not iframes and shadow roots should be traversed when returning the subtree\n(default is false). (optional)\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :node | Node description."
 ([]
  (describe-node
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [node-id backend-node-id object-id depth pierce]}]
  (describe-node
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [node-id backend-node-id object-id depth pierce]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.describeNode"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId",
      :depth "depth",
      :pierce "pierce"})]
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
 describe-node
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
     ::depth
     ::pierce]))
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
     ::depth
     ::pierce])))
 :ret
 (s/keys
  :req-un
  [::node]))

(defn
 disable
 "Disables DOM agent for the given page."
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
    "DOM.disable"
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
 discard-search-results
 "Discards search results from the session with the given id. `getSearchResults` should no longer\nbe called for that search.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :search-id | Unique search session identifier."
 ([]
  (discard-search-results
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [search-id]}]
  (discard-search-results
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [search-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.discardSearchResults"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:search-id "searchId"})]
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
 discard-search-results
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::search-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::search-id])))
 :ret
 (s/keys))

(defn
 enable
 "Enables DOM agent for the given page."
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
    "DOM.enable"
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
 focus
 "Focuses the given element.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node. (optional)\n  :backend-node-id | Identifier of the backend node. (optional)\n  :object-id       | JavaScript object id of the node wrapper. (optional)"
 ([]
  (focus
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id backend-node-id object-id]}]
  (focus
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id backend-node-id object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.focus"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId"})]
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
 focus
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
 (s/keys))

(defn
 get-attributes
 "Returns attributes for the specified node.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node to retrieve attibutes for.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :attributes | An interleaved array of node attribute names and values."
 ([]
  (get-attributes
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id]}]
  (get-attributes
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getAttributes"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId"})]
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
 get-attributes
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id])))
 :ret
 (s/keys
  :req-un
  [::attributes]))

(defn
 get-box-model
 "Returns boxes for the given node.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node. (optional)\n  :backend-node-id | Identifier of the backend node. (optional)\n  :object-id       | JavaScript object id of the node wrapper. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :model | Box model for the node."
 ([]
  (get-box-model
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id backend-node-id object-id]}]
  (get-box-model
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id backend-node-id object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getBoxModel"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId"})]
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
 get-box-model
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
  [::model]))

(defn
 get-content-quads
 "Returns quads that describe node position on the page. This method\nmight return multiple quads for inline nodes.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node. (optional)\n  :backend-node-id | Identifier of the backend node. (optional)\n  :object-id       | JavaScript object id of the node wrapper. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :quads | Quads that describe node layout relative to viewport."
 ([]
  (get-content-quads
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id backend-node-id object-id]}]
  (get-content-quads
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id backend-node-id object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getContentQuads"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId"})]
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
 get-content-quads
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
  [::quads]))

(defn
 get-document
 "Returns the root DOM node (and optionally the subtree) to the caller.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :depth  | The maximum depth at which children should be retrieved, defaults to 1. Use -1 for the\nentire subtree or provide an integer larger than 0. (optional)\n  :pierce | Whether or not iframes and shadow roots should be traversed when returning the subtree\n(default is false). (optional)\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :root | Resulting node."
 ([]
  (get-document
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [depth pierce]}]
  (get-document
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [depth pierce]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getDocument"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:depth "depth", :pierce "pierce"})]
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
 get-document
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
     ::pierce]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::depth
     ::pierce])))
 :ret
 (s/keys
  :req-un
  [::root]))

(defn
 get-flattened-document
 "Returns the root DOM node (and optionally the subtree) to the caller.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :depth  | The maximum depth at which children should be retrieved, defaults to 1. Use -1 for the\nentire subtree or provide an integer larger than 0. (optional)\n  :pierce | Whether or not iframes and shadow roots should be traversed when returning the subtree\n(default is false). (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :nodes | Resulting node."
 ([]
  (get-flattened-document
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [depth pierce]}]
  (get-flattened-document
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [depth pierce]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getFlattenedDocument"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:depth "depth", :pierce "pierce"})]
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
 get-flattened-document
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
     ::pierce]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::depth
     ::pierce])))
 :ret
 (s/keys
  :req-un
  [::nodes]))

(defn
 get-node-for-location
 "Returns node id at given location. Depending on whether DOM domain is enabled, nodeId is\neither returned or not.\n\nParameters map keys:\n\n\n  Key                            | Description \n  -------------------------------|------------ \n  :x                             | X coordinate.\n  :y                             | Y coordinate.\n  :include-user-agent-shadow-dom | False to skip to the nearest non-UA shadow root ancestor (default: false). (optional)\n\nReturn map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :backend-node-id | Resulting node.\n  :node-id         | Id of the node at given coordinates, only when enabled and requested document. (optional)"
 ([]
  (get-node-for-location
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [x y include-user-agent-shadow-dom]}]
  (get-node-for-location
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [x y include-user-agent-shadow-dom]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getNodeForLocation"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:x "x",
      :y "y",
      :include-user-agent-shadow-dom "includeUserAgentShadowDOM"})]
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
 get-node-for-location
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
     ::y]
    :opt-un
    [::include-user-agent-shadow-dom]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::x
     ::y]
    :opt-un
    [::include-user-agent-shadow-dom])))
 :ret
 (s/keys
  :req-un
  [::backend-node-id]
  :opt-un
  [::node-id]))

(defn
 get-outer-html
 "Returns node's HTML markup.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :node-id         | Identifier of the node. (optional)\n  :backend-node-id | Identifier of the backend node. (optional)\n  :object-id       | JavaScript object id of the node wrapper. (optional)\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :outer-html | Outer HTML markup."
 ([]
  (get-outer-html
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id backend-node-id object-id]}]
  (get-outer-html
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id backend-node-id object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getOuterHTML"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId"})]
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
 get-outer-html
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
  [::outer-html]))

(defn
 get-relayout-boundary
 "Returns the id of the nearest ancestor that is a relayout boundary.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Relayout boundary node id for the given node."
 ([]
  (get-relayout-boundary
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id]}]
  (get-relayout-boundary
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getRelayoutBoundary"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId"})]
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
 get-relayout-boundary
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id])))
 :ret
 (s/keys
  :req-un
  [::node-id]))

(defn
 get-search-results
 "Returns search results from given `fromIndex` to given `toIndex` from the search with the given\nidentifier.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :search-id  | Unique search session identifier.\n  :from-index | Start index of the search result to be returned.\n  :to-index   | End index of the search result to be returned.\n\nReturn map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :node-ids | Ids of the search result nodes."
 ([]
  (get-search-results
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [search-id from-index to-index]}]
  (get-search-results
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [search-id from-index to-index]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getSearchResults"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:search-id "searchId",
      :from-index "fromIndex",
      :to-index "toIndex"})]
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
 get-search-results
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::search-id
     ::from-index
     ::to-index]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::search-id
     ::from-index
     ::to-index])))
 :ret
 (s/keys
  :req-un
  [::node-ids]))

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
    "DOM.hideHighlight"
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
 highlight-node
 "Highlights DOM node."
 ([]
  (highlight-node
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (highlight-node
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.highlightNode"
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
 highlight-node
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
 highlight-rect
 "Highlights given rectangle."
 ([]
  (highlight-rect
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (highlight-rect
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.highlightRect"
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
 highlight-rect
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
 mark-undoable-state
 "Marks last undoable state."
 ([]
  (mark-undoable-state
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (mark-undoable-state
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.markUndoableState"
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
 mark-undoable-state
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
 move-to
 "Moves node into the new container, places it before the given anchor.\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :node-id               | Id of the node to move.\n  :target-node-id        | Id of the element to drop the moved node into.\n  :insert-before-node-id | Drop node before this one (if absent, the moved node becomes the last child of\n`targetNodeId`). (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | New id of the moved node."
 ([]
  (move-to
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id target-node-id insert-before-node-id]}]
  (move-to
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [node-id target-node-id insert-before-node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.moveTo"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :target-node-id "targetNodeId",
      :insert-before-node-id "insertBeforeNodeId"})]
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
 move-to
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id
     ::target-node-id]
    :opt-un
    [::insert-before-node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id
     ::target-node-id]
    :opt-un
    [::insert-before-node-id])))
 :ret
 (s/keys
  :req-un
  [::node-id]))

(defn
 perform-search
 "Searches for a given string in the DOM tree. Use `getSearchResults` to access search results or\n`cancelSearch` to end this search session.\n\nParameters map keys:\n\n\n  Key                            | Description \n  -------------------------------|------------ \n  :query                         | Plain text or query selector or XPath search query.\n  :include-user-agent-shadow-dom | True to search in user agent shadow DOM. (optional)\n\nReturn map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :search-id    | Unique search session identifier.\n  :result-count | Number of search results."
 ([]
  (perform-search
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [query include-user-agent-shadow-dom]}]
  (perform-search
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [query include-user-agent-shadow-dom]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.performSearch"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:query "query",
      :include-user-agent-shadow-dom "includeUserAgentShadowDOM"})]
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
 perform-search
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::query]
    :opt-un
    [::include-user-agent-shadow-dom]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::query]
    :opt-un
    [::include-user-agent-shadow-dom])))
 :ret
 (s/keys
  :req-un
  [::search-id
   ::result-count]))

(defn
 push-node-by-path-to-frontend
 "Requests that the node is sent to the caller given its path. // FIXME, use XPath\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :path | Path to node in the proprietary format.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node for given path."
 ([]
  (push-node-by-path-to-frontend
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [path]}]
  (push-node-by-path-to-frontend
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [path]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.pushNodeByPathToFrontend"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:path "path"})]
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
 push-node-by-path-to-frontend
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::path]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::path])))
 :ret
 (s/keys
  :req-un
  [::node-id]))

(defn
 push-nodes-by-backend-ids-to-frontend
 "Requests that a batch of nodes is sent to the caller given their backend node ids.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :backend-node-ids | The array of backend node ids.\n\nReturn map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :node-ids | The array of ids of pushed nodes that correspond to the backend ids specified in\nbackendNodeIds."
 ([]
  (push-nodes-by-backend-ids-to-frontend
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [backend-node-ids]}]
  (push-nodes-by-backend-ids-to-frontend
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [backend-node-ids]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.pushNodesByBackendIdsToFrontend"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:backend-node-ids "backendNodeIds"})]
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
 push-nodes-by-backend-ids-to-frontend
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::backend-node-ids]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::backend-node-ids])))
 :ret
 (s/keys
  :req-un
  [::node-ids]))

(defn
 query-selector
 "Executes `querySelector` on a given node.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :node-id  | Id of the node to query upon.\n  :selector | Selector string.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Query selector result."
 ([]
  (query-selector
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id selector]}]
  (query-selector
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id selector]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.querySelector"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :selector "selector"})]
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
 query-selector
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id
     ::selector]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id
     ::selector])))
 :ret
 (s/keys
  :req-un
  [::node-id]))

(defn
 query-selector-all
 "Executes `querySelectorAll` on a given node.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :node-id  | Id of the node to query upon.\n  :selector | Selector string.\n\nReturn map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :node-ids | Query selector result."
 ([]
  (query-selector-all
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id selector]}]
  (query-selector-all
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id selector]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.querySelectorAll"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :selector "selector"})]
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
 query-selector-all
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id
     ::selector]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id
     ::selector])))
 :ret
 (s/keys
  :req-un
  [::node-ids]))

(defn
 redo
 "Re-does the last undone action."
 ([]
  (redo
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (redo
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.redo"
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
 redo
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
 remove-attribute
 "Removes attribute with given name from an element with given id.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the element to remove attribute from.\n  :name    | Name of the attribute to remove."
 ([]
  (remove-attribute
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id name]}]
  (remove-attribute
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id name]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.removeAttribute"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :name "name"})]
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
 remove-attribute
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id
     ::name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id
     ::name])))
 :ret
 (s/keys))

(defn
 remove-node
 "Removes node with given id.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node to remove."
 ([]
  (remove-node
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id]}]
  (remove-node
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.removeNode"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId"})]
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
 remove-node
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id])))
 :ret
 (s/keys))

(defn
 request-child-nodes
 "Requests that children of the node with given id are returned to the caller in form of\n`setChildNodes` events where not only immediate children are retrieved, but all children down to\nthe specified depth.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node to get children for.\n  :depth   | The maximum depth at which children should be retrieved, defaults to 1. Use -1 for the\nentire subtree or provide an integer larger than 0. (optional)\n  :pierce  | Whether or not iframes and shadow roots should be traversed when returning the sub-tree\n(default is false). (optional)"
 ([]
  (request-child-nodes
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id depth pierce]}]
  (request-child-nodes
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id depth pierce]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.requestChildNodes"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :depth "depth", :pierce "pierce"})]
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
 request-child-nodes
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
    [::depth
     ::pierce]))
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
    [::depth
     ::pierce])))
 :ret
 (s/keys))

(defn
 request-node
 "Requests that the node is sent to the caller given the JavaScript node object reference. All\nnodes that form the path from the node to the root are also sent to the client as a series of\n`setChildNodes` notifications.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :object-id | JavaScript object id to convert into node.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Node id for given object."
 ([]
  (request-node
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id]}]
  (request-node
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.requestNode"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:object-id "objectId"})]
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
 request-node
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::object-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::object-id])))
 :ret
 (s/keys
  :req-un
  [::node-id]))

(defn
 resolve-node
 "Resolves the JavaScript node object for a given NodeId or BackendNodeId.\n\nParameters map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :node-id              | Id of the node to resolve. (optional)\n  :backend-node-id      | Backend identifier of the node to resolve. (optional)\n  :object-group         | Symbolic group name that can be used to release multiple objects. (optional)\n  :execution-context-id | Execution context in which to resolve the node. (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :object | JavaScript object wrapper for given node."
 ([]
  (resolve-node
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [node-id backend-node-id object-group execution-context-id]}]
  (resolve-node
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [node-id backend-node-id object-group execution-context-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.resolveNode"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-group "objectGroup",
      :execution-context-id "executionContextId"})]
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
 resolve-node
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
     ::object-group
     ::execution-context-id]))
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
     ::object-group
     ::execution-context-id])))
 :ret
 (s/keys
  :req-un
  [::object]))

(defn
 set-attribute-value
 "Sets attribute for an element with given id.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the element to set attribute for.\n  :name    | Attribute name.\n  :value   | Attribute value."
 ([]
  (set-attribute-value
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id name value]}]
  (set-attribute-value
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id name value]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setAttributeValue"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :name "name", :value "value"})]
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
 set-attribute-value
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/name
     :clj-chrome-devtools.impl.define/value]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/name
     :clj-chrome-devtools.impl.define/value])))
 :ret
 (s/keys))

(defn
 set-attributes-as-text
 "Sets attributes on element with given id. This method is useful when user edits some existing\nattribute value and types in several attribute name/value pairs.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the element to set attributes for.\n  :text    | Text with a number of attributes. Will parse this text using HTML parser.\n  :name    | Attribute name to replace with new attributes derived from text in case text parsed\nsuccessfully. (optional)"
 ([]
  (set-attributes-as-text
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id text name]}]
  (set-attributes-as-text
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id text name]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setAttributesAsText"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :text "text", :name "name"})]
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
 set-attributes-as-text
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/text]
    :opt-un
    [:clj-chrome-devtools.impl.define/name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/text]
    :opt-un
    [:clj-chrome-devtools.impl.define/name])))
 :ret
 (s/keys))

(defn
 set-file-input-files
 "Sets files for the given file input element.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :files           | Array of file paths to set.\n  :node-id         | Identifier of the node. (optional)\n  :backend-node-id | Identifier of the backend node. (optional)\n  :object-id       | JavaScript object id of the node wrapper. (optional)"
 ([]
  (set-file-input-files
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [files node-id backend-node-id object-id]}]
  (set-file-input-files
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [files node-id backend-node-id object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setFileInputFiles"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:files "files",
      :node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId"})]
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
 set-file-input-files
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/files]
    :opt-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/backend-node-id
     :clj-chrome-devtools.impl.define/object-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/files]
    :opt-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/backend-node-id
     :clj-chrome-devtools.impl.define/object-id])))
 :ret
 (s/keys))

(defn
 set-node-stack-traces-enabled
 "Sets if stack traces should be captured for Nodes. See `Node.getNodeStackTraces`. Default is disabled.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :enable | Enable or disable."
 ([]
  (set-node-stack-traces-enabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enable]}]
  (set-node-stack-traces-enabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enable]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setNodeStackTracesEnabled"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:enable "enable"})]
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
 set-node-stack-traces-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enable]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enable])))
 :ret
 (s/keys))

(defn
 get-node-stack-traces
 "Gets stack traces associated with a Node. As of now, only provides stack trace for Node creation.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node to get stack traces for.\n\nReturn map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :creation | Creation stack trace, if available. (optional)"
 ([]
  (get-node-stack-traces
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id]}]
  (get-node-stack-traces
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getNodeStackTraces"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId"})]
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
 get-node-stack-traces
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id])))
 :ret
 (s/keys
  :opt-un
  [:clj-chrome-devtools.impl.define/creation]))

(defn
 get-file-info
 "Returns file information for the given\nFile wrapper.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :object-id | JavaScript object id of the node wrapper.\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :path | null"
 ([]
  (get-file-info
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id]}]
  (get-file-info
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getFileInfo"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:object-id "objectId"})]
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
 get-file-info
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/object-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/object-id])))
 :ret
 (s/keys
  :req-un
  [:clj-chrome-devtools.impl.define/path]))

(defn
 set-inspected-node
 "Enables console to refer to the node with given id via $x (see Command Line API for more details\n$x functions).\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | DOM node id to be accessible by means of $x command line API."
 ([]
  (set-inspected-node
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id]}]
  (set-inspected-node
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setInspectedNode"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId"})]
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
 set-inspected-node
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id])))
 :ret
 (s/keys))

(defn
 set-node-name
 "Sets node name for a node with given id.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node to set name for.\n  :name    | New node's name.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | New node's id."
 ([]
  (set-node-name
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id name]}]
  (set-node-name
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id name]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setNodeName"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :name "name"})]
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
 set-node-name
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/name])))
 :ret
 (s/keys
  :req-un
  [:clj-chrome-devtools.impl.define/node-id]))

(defn
 set-node-value
 "Sets node value for a node with given id.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Id of the node to set value for.\n  :value   | New node's value."
 ([]
  (set-node-value
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id value]}]
  (set-node-value
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id value]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setNodeValue"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :value "value"})]
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
 set-node-value
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/value]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/value])))
 :ret
 (s/keys))

(defn
 set-outer-html
 "Sets node HTML markup, returns new node id.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :node-id    | Id of the node to set markup for.\n  :outer-html | Outer HTML markup to set."
 ([]
  (set-outer-html
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id outer-html]}]
  (set-outer-html
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id outer-html]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.setOuterHTML"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:node-id "nodeId", :outer-html "outerHTML"})]
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
 set-outer-html
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/outer-html]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/node-id
     :clj-chrome-devtools.impl.define/outer-html])))
 :ret
 (s/keys))

(defn
 undo
 "Undoes the last performed action."
 ([]
  (undo
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (undo
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.undo"
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
 undo
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
 get-frame-owner
 "Returns iframe node that owns iframe with the given domain.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | null\n\nReturn map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :backend-node-id | Resulting node.\n  :node-id         | Id of the node at given coordinates, only when enabled and requested document. (optional)"
 ([]
  (get-frame-owner
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id]}]
  (get-frame-owner
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "DOM.getFrameOwner"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:frame-id "frameId"})]
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
 get-frame-owner
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/frame-id])))
 :ret
 (s/keys
  :req-un
  [:clj-chrome-devtools.impl.define/backend-node-id]
  :opt-un
  [:clj-chrome-devtools.impl.define/node-id]))
