(ns clj-chrome-devtools.commands.dom-debugger
  "DOM debugging allows setting breakpoints on particular DOM operations and events. JavaScript\nexecution will stop on these operations as if there was a regular breakpoint set."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::dom-breakpoint-type
 #{"attribute-modified" "subtree-modified" "node-removed"})

(s/def
 ::csp-violation-type
 #{"trustedtype-sink-violation" "trustedtype-policy-violation"})

(s/def
 ::event-listener
 (s/keys
  :req-un
  [::type
   ::use-capture
   ::passive
   ::once
   ::script-id
   ::line-number
   ::column-number]
  :opt-un
  [::handler
   ::original-handler
   ::backend-node-id]))
(defn
 get-event-listeners
 "Returns event listeners of the given object.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :object-id | Identifier of the object to return listeners for.\n  :depth     | The maximum depth at which Node children should be retrieved, defaults to 1. Use -1 for the\nentire subtree or provide an integer larger than 0. (optional)\n  :pierce    | Whether or not iframes and shadow roots should be traversed when returning the subtree\n(default is false). Reports listeners for all contexts if pierce is enabled. (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :listeners | Array of relevant listeners."
 ([]
  (get-event-listeners
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id depth pierce]}]
  (get-event-listeners
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id depth pierce]}]
  (cmd/command
   connection
   "DOMDebugger"
   "getEventListeners"
   params
   {:object-id "objectId", :depth "depth", :pierce "pierce"})))

(s/fdef
 get-event-listeners
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::object-id]
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
    [::object-id]
    :opt-un
    [::depth
     ::pierce])))
 :ret
 (s/keys
  :req-un
  [::listeners]))

(defn
 remove-dom-breakpoint
 "Removes DOM breakpoint that was set using `setDOMBreakpoint`.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Identifier of the node to remove breakpoint from.\n  :type    | Type of the breakpoint to remove."
 ([]
  (remove-dom-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id type]}]
  (remove-dom-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id type]}]
  (cmd/command
   connection
   "DOMDebugger"
   "removeDOMBreakpoint"
   params
   {:node-id "nodeId", :type "type"})))

(s/fdef
 remove-dom-breakpoint
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
     ::type]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id
     ::type])))
 :ret
 (s/keys))

(defn
 remove-event-listener-breakpoint
 "Removes breakpoint on particular DOM event.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :event-name  | Event name.\n  :target-name | EventTarget interface name. (optional)"
 ([]
  (remove-event-listener-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [event-name target-name]}]
  (remove-event-listener-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [event-name target-name]}]
  (cmd/command
   connection
   "DOMDebugger"
   "removeEventListenerBreakpoint"
   params
   {:event-name "eventName", :target-name "targetName"})))

(s/fdef
 remove-event-listener-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::event-name]
    :opt-un
    [::target-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::event-name]
    :opt-un
    [::target-name])))
 :ret
 (s/keys))

(defn
 remove-instrumentation-breakpoint
 "Removes breakpoint on particular native event.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :event-name | Instrumentation name to stop on."
 ([]
  (remove-instrumentation-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [event-name]}]
  (remove-instrumentation-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [event-name]}]
  (cmd/command
   connection
   "DOMDebugger"
   "removeInstrumentationBreakpoint"
   params
   {:event-name "eventName"})))

(s/fdef
 remove-instrumentation-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::event-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::event-name])))
 :ret
 (s/keys))

(defn
 remove-xhr-breakpoint
 "Removes breakpoint from XMLHttpRequest.\n\nParameters map keys:\n\n\n  Key  | Description \n  -----|------------ \n  :url | Resource URL substring."
 ([]
  (remove-xhr-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [url]}]
  (remove-xhr-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [url]}]
  (cmd/command
   connection
   "DOMDebugger"
   "removeXHRBreakpoint"
   params
   {:url "url"})))

(s/fdef
 remove-xhr-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::url])))
 :ret
 (s/keys))

(defn
 set-break-on-csp-violation
 "Sets breakpoint on particular CSP violations.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :violation-types | CSP Violations to stop upon."
 ([]
  (set-break-on-csp-violation
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [violation-types]}]
  (set-break-on-csp-violation
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [violation-types]}]
  (cmd/command
   connection
   "DOMDebugger"
   "setBreakOnCSPViolation"
   params
   {:violation-types "violationTypes"})))

(s/fdef
 set-break-on-csp-violation
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::violation-types]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::violation-types])))
 :ret
 (s/keys))

(defn
 set-dom-breakpoint
 "Sets breakpoint on particular operation with DOM.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :node-id | Identifier of the node to set breakpoint on.\n  :type    | Type of the operation to stop upon."
 ([]
  (set-dom-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [node-id type]}]
  (set-dom-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [node-id type]}]
  (cmd/command
   connection
   "DOMDebugger"
   "setDOMBreakpoint"
   params
   {:node-id "nodeId", :type "type"})))

(s/fdef
 set-dom-breakpoint
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
     ::type]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::node-id
     ::type])))
 :ret
 (s/keys))

(defn
 set-event-listener-breakpoint
 "Sets breakpoint on particular DOM event.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :event-name  | DOM Event name to stop on (any DOM event will do).\n  :target-name | EventTarget interface name to stop on. If equal to `\"*\"` or not provided, will stop on any\nEventTarget. (optional)"
 ([]
  (set-event-listener-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [event-name target-name]}]
  (set-event-listener-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [event-name target-name]}]
  (cmd/command
   connection
   "DOMDebugger"
   "setEventListenerBreakpoint"
   params
   {:event-name "eventName", :target-name "targetName"})))

(s/fdef
 set-event-listener-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::event-name]
    :opt-un
    [::target-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::event-name]
    :opt-un
    [::target-name])))
 :ret
 (s/keys))

(defn
 set-instrumentation-breakpoint
 "Sets breakpoint on particular native event.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :event-name | Instrumentation name to stop on."
 ([]
  (set-instrumentation-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [event-name]}]
  (set-instrumentation-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [event-name]}]
  (cmd/command
   connection
   "DOMDebugger"
   "setInstrumentationBreakpoint"
   params
   {:event-name "eventName"})))

(s/fdef
 set-instrumentation-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::event-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::event-name])))
 :ret
 (s/keys))

(defn
 set-xhr-breakpoint
 "Sets breakpoint on XMLHttpRequest.\n\nParameters map keys:\n\n\n  Key  | Description \n  -----|------------ \n  :url | Resource URL substring. All XHRs having this substring in the URL will get stopped upon."
 ([]
  (set-xhr-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [url]}]
  (set-xhr-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [url]}]
  (cmd/command
   connection
   "DOMDebugger"
   "setXHRBreakpoint"
   params
   {:url "url"})))

(s/fdef
 set-xhr-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::url])))
 :ret
 (s/keys))
