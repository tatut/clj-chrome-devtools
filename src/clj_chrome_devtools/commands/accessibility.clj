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
 #{"label" "legend" "labelwrapped" "figcaption" "title" "tablecaption"
   "other" "labelfor"})

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
   ::child-ids
   ::backend-dom-node-id]))
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Accessibility.disable"
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Accessibility.enable"
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Accessibility.getPartialAXTree"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:node-id "nodeId",
      :backend-node-id "backendNodeId",
      :object-id "objectId",
      :fetch-relatives "fetchRelatives"})]
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
 "Fetches the entire accessibility tree\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :nodes | null"
 ([]
  (get-full-ax-tree
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-full-ax-tree
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Accessibility.getFullAXTree"
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
 get-full-ax-tree
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
  [::nodes]))
