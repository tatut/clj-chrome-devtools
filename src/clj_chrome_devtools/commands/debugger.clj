(ns clj-chrome-devtools.commands.debugger
  "Debugger domain exposes JavaScript debugging capabilities. It allows setting and removing\nbreakpoints, stepping through execution, exploring stack traces, etc."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::breakpoint-id
 string?)

(s/def
 ::call-frame-id
 string?)

(s/def
 ::location
 (s/keys
  :req-un
  [::script-id
   ::line-number]
  :opt-un
  [::column-number]))

(s/def
 ::script-position
 (s/keys
  :req-un
  [::line-number
   ::column-number]))

(s/def
 ::call-frame
 (s/keys
  :req-un
  [::call-frame-id
   ::function-name
   ::location
   ::url
   ::scope-chain
   ::this]
  :opt-un
  [::function-location
   ::return-value]))

(s/def
 ::scope
 (s/keys
  :req-un
  [::type
   ::object]
  :opt-un
  [::name
   ::start-location
   ::end-location]))

(s/def
 ::search-match
 (s/keys
  :req-un
  [::line-number
   ::line-content]))

(s/def
 ::break-location
 (s/keys
  :req-un
  [::script-id
   ::line-number]
  :opt-un
  [::column-number
   ::type]))
(defn
 continue-to-location
 "Continues execution until specific location is reached.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :location           | Location to continue to.\n  :target-call-frames | null (optional)"
 ([]
  (continue-to-location
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [location target-call-frames]}]
  (continue-to-location
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [location target-call-frames]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.continueToLocation"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:location "location", :target-call-frames "targetCallFrames"})]
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
 continue-to-location
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::location]
    :opt-un
    [::target-call-frames]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::location]
    :opt-un
    [::target-call-frames])))
 :ret
 (s/keys))

(defn
 disable
 "Disables debugger for given page."
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
    "Debugger.disable"
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
 "Enables debugger for the given page. Clients should not assume that the debugging has been\nenabled until the result for this command is received.\n\nParameters map keys:\n\n\n  Key                     | Description \n  ------------------------|------------ \n  :max-scripts-cache-size | The maximum size in bytes of collected scripts (not referenced by other heap objects)\nthe debugger can hold. Puts no limit if paramter is omitted. (optional)\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :debugger-id | Unique identifier of the debugger."
 ([]
  (enable
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [max-scripts-cache-size]}]
  (enable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [max-scripts-cache-size]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.enable"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:max-scripts-cache-size "maxScriptsCacheSize"})]
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
  (s/cat
   :params
   (s/keys
    :opt-un
    [::max-scripts-cache-size]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::max-scripts-cache-size])))
 :ret
 (s/keys
  :req-un
  [::debugger-id]))

(defn
 evaluate-on-call-frame
 "Evaluates expression on a given call frame.\n\nParameters map keys:\n\n\n  Key                       | Description \n  --------------------------|------------ \n  :call-frame-id            | Call frame identifier to evaluate on.\n  :expression               | Expression to evaluate.\n  :object-group             | String object group name to put result into (allows rapid releasing resulting object handles\nusing `releaseObjectGroup`). (optional)\n  :include-command-line-api | Specifies whether command line API should be available to the evaluated expression, defaults\nto false. (optional)\n  :silent                   | In silent mode exceptions thrown during evaluation are not reported and do not pause\nexecution. Overrides `setPauseOnException` state. (optional)\n  :return-by-value          | Whether the result is expected to be a JSON object that should be sent by value. (optional)\n  :generate-preview         | Whether preview should be generated for the result. (optional)\n  :throw-on-side-effect     | Whether to throw an exception if side effect cannot be ruled out during evaluation. (optional)\n  :timeout                  | Terminate execution after timing out (number of milliseconds). (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :result            | Object wrapper for the evaluation result.\n  :exception-details | Exception details. (optional)"
 ([]
  (evaluate-on-call-frame
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [call-frame-id
     expression
     object-group
     include-command-line-api
     silent
     return-by-value
     generate-preview
     throw-on-side-effect
     timeout]}]
  (evaluate-on-call-frame
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [call-frame-id
     expression
     object-group
     include-command-line-api
     silent
     return-by-value
     generate-preview
     throw-on-side-effect
     timeout]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.evaluateOnCallFrame"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:object-group "objectGroup",
      :expression "expression",
      :silent "silent",
      :throw-on-side-effect "throwOnSideEffect",
      :call-frame-id "callFrameId",
      :return-by-value "returnByValue",
      :timeout "timeout",
      :generate-preview "generatePreview",
      :include-command-line-api "includeCommandLineAPI"})]
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
 evaluate-on-call-frame
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::call-frame-id
     ::expression]
    :opt-un
    [::object-group
     ::include-command-line-api
     ::silent
     ::return-by-value
     ::generate-preview
     ::throw-on-side-effect
     ::timeout]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::call-frame-id
     ::expression]
    :opt-un
    [::object-group
     ::include-command-line-api
     ::silent
     ::return-by-value
     ::generate-preview
     ::throw-on-side-effect
     ::timeout])))
 :ret
 (s/keys
  :req-un
  [::result]
  :opt-un
  [::exception-details]))

(defn
 get-possible-breakpoints
 "Returns possible locations for breakpoint. scriptId in start and end range locations should be\nthe same.\n\nParameters map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :start                | Start of range to search possible breakpoint locations in.\n  :end                  | End of range to search possible breakpoint locations in (excluding). When not specified, end\nof scripts is used as end of range. (optional)\n  :restrict-to-function | Only consider locations which are in the same (non-nested) function as start. (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :locations | List of the possible breakpoint locations."
 ([]
  (get-possible-breakpoints
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [start end restrict-to-function]}]
  (get-possible-breakpoints
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [start end restrict-to-function]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.getPossibleBreakpoints"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:start "start",
      :end "end",
      :restrict-to-function "restrictToFunction"})]
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
 get-possible-breakpoints
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::start]
    :opt-un
    [::end
     ::restrict-to-function]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::start]
    :opt-un
    [::end
     ::restrict-to-function])))
 :ret
 (s/keys
  :req-un
  [::locations]))

(defn
 get-script-source
 "Returns source for the script with given id.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :script-id | Id of the script to get source for.\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :script-source | Script source."
 ([]
  (get-script-source
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [script-id]}]
  (get-script-source
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [script-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.getScriptSource"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:script-id "scriptId"})]
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
 get-script-source
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::script-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::script-id])))
 :ret
 (s/keys
  :req-un
  [::script-source]))

(defn
 get-stack-trace
 "Returns stack trace with given `stackTraceId`.\n\nParameters map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :stack-trace-id | null\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :stack-trace | null"
 ([]
  (get-stack-trace
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [stack-trace-id]}]
  (get-stack-trace
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [stack-trace-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.getStackTrace"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:stack-trace-id "stackTraceId"})]
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
 get-stack-trace
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::stack-trace-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::stack-trace-id])))
 :ret
 (s/keys
  :req-un
  [::stack-trace]))

(defn
 pause
 "Stops on the next JavaScript statement."
 ([]
  (pause
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (pause
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.pause"
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
 pause
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
 pause-on-async-call
 "\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :parent-stack-trace-id | Debugger will pause when async call with given stack trace is started."
 ([]
  (pause-on-async-call
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [parent-stack-trace-id]}]
  (pause-on-async-call
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [parent-stack-trace-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.pauseOnAsyncCall"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:parent-stack-trace-id "parentStackTraceId"})]
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
 pause-on-async-call
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::parent-stack-trace-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::parent-stack-trace-id])))
 :ret
 (s/keys))

(defn
 remove-breakpoint
 "Removes JavaScript breakpoint.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :breakpoint-id | null"
 ([]
  (remove-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [breakpoint-id]}]
  (remove-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [breakpoint-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.removeBreakpoint"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:breakpoint-id "breakpointId"})]
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
 remove-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::breakpoint-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::breakpoint-id])))
 :ret
 (s/keys))

(defn
 restart-frame
 "Restarts particular call frame from the beginning.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :call-frame-id | Call frame identifier to evaluate on.\n\nReturn map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :call-frames          | New stack trace.\n  :async-stack-trace    | Async stack trace, if any. (optional)\n  :async-stack-trace-id | Async stack trace, if any. (optional)"
 ([]
  (restart-frame
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [call-frame-id]}]
  (restart-frame
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [call-frame-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.restartFrame"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:call-frame-id "callFrameId"})]
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
 restart-frame
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::call-frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::call-frame-id])))
 :ret
 (s/keys
  :req-un
  [::call-frames]
  :opt-un
  [::async-stack-trace
   ::async-stack-trace-id]))

(defn
 resume
 "Resumes JavaScript execution."
 ([]
  (resume
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (resume
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.resume"
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
 resume
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
 search-in-content
 "Searches for given string in script content.\n\nParameters map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :script-id      | Id of the script to search in.\n  :query          | String to search for.\n  :case-sensitive | If true, search is case sensitive. (optional)\n  :is-regex       | If true, treats string parameter as regex. (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | List of search matches."
 ([]
  (search-in-content
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [script-id query case-sensitive is-regex]}]
  (search-in-content
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [script-id query case-sensitive is-regex]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.searchInContent"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:script-id "scriptId",
      :query "query",
      :case-sensitive "caseSensitive",
      :is-regex "isRegex"})]
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
 search-in-content
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::script-id
     ::query]
    :opt-un
    [::case-sensitive
     ::is-regex]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::script-id
     ::query]
    :opt-un
    [::case-sensitive
     ::is-regex])))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 set-async-call-stack-depth
 "Enables or disables async call stacks tracking.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :max-depth | Maximum depth of async call stacks. Setting to `0` will effectively disable collecting async\ncall stacks (default)."
 ([]
  (set-async-call-stack-depth
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [max-depth]}]
  (set-async-call-stack-depth
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [max-depth]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setAsyncCallStackDepth"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:max-depth "maxDepth"})]
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
 set-async-call-stack-depth
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::max-depth]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::max-depth])))
 :ret
 (s/keys))

(defn
 set-blackbox-patterns
 "Replace previous blackbox patterns with passed ones. Forces backend to skip stepping/pausing in\nscripts with url matching one of the patterns. VM will try to leave blackboxed script by\nperforming 'step in' several times, finally resorting to 'step out' if unsuccessful.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :patterns | Array of regexps that will be used to check script url for blackbox state."
 ([]
  (set-blackbox-patterns
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [patterns]}]
  (set-blackbox-patterns
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [patterns]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setBlackboxPatterns"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:patterns "patterns"})]
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
 set-blackbox-patterns
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::patterns]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::patterns])))
 :ret
 (s/keys))

(defn
 set-blackboxed-ranges
 "Makes backend skip steps in the script in blackboxed ranges. VM will try leave blacklisted\nscripts by performing 'step in' several times, finally resorting to 'step out' if unsuccessful.\nPositions array contains positions where blackbox state is changed. First interval isn't\nblackboxed. Array should be sorted.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :script-id | Id of the script.\n  :positions | null"
 ([]
  (set-blackboxed-ranges
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [script-id positions]}]
  (set-blackboxed-ranges
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [script-id positions]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setBlackboxedRanges"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:script-id "scriptId", :positions "positions"})]
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
 set-blackboxed-ranges
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::script-id
     ::positions]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::script-id
     ::positions])))
 :ret
 (s/keys))

(defn
 set-breakpoint
 "Sets JavaScript breakpoint at a given location.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :location  | Location to set breakpoint in.\n  :condition | Expression to use as a breakpoint condition. When specified, debugger will only stop on the\nbreakpoint if this expression evaluates to true. (optional)\n\nReturn map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :breakpoint-id   | Id of the created breakpoint for further reference.\n  :actual-location | Location this breakpoint resolved into."
 ([]
  (set-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [location condition]}]
  (set-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [location condition]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setBreakpoint"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:location "location", :condition "condition"})]
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
 set-breakpoint
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::location]
    :opt-un
    [::condition]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::location]
    :opt-un
    [::condition])))
 :ret
 (s/keys
  :req-un
  [::breakpoint-id
   ::actual-location]))

(defn
 set-instrumentation-breakpoint
 "Sets instrumentation breakpoint.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :instrumentation | Instrumentation name.\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :breakpoint-id | Id of the created breakpoint for further reference."
 ([]
  (set-instrumentation-breakpoint
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [instrumentation]}]
  (set-instrumentation-breakpoint
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [instrumentation]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setInstrumentationBreakpoint"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:instrumentation "instrumentation"})]
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
    [::instrumentation]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::instrumentation])))
 :ret
 (s/keys
  :req-un
  [::breakpoint-id]))

(defn
 set-breakpoint-by-url
 "Sets JavaScript breakpoint at given location specified either by URL or URL regex. Once this\ncommand is issued, all existing parsed scripts will have breakpoints resolved and returned in\n`locations` property. Further matching script parsing will result in subsequent\n`breakpointResolved` events issued. This logical breakpoint will survive page reloads.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :line-number   | Line number to set breakpoint at.\n  :url           | URL of the resources to set breakpoint on. (optional)\n  :url-regex     | Regex pattern for the URLs of the resources to set breakpoints on. Either `url` or\n`urlRegex` must be specified. (optional)\n  :script-hash   | Script hash of the resources to set breakpoint on. (optional)\n  :column-number | Offset in the line to set breakpoint at. (optional)\n  :condition     | Expression to use as a breakpoint condition. When specified, debugger will only stop on the\nbreakpoint if this expression evaluates to true. (optional)\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :breakpoint-id | Id of the created breakpoint for further reference.\n  :locations     | List of the locations this breakpoint resolved into upon addition."
 ([]
  (set-breakpoint-by-url
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [line-number url url-regex script-hash column-number condition]}]
  (set-breakpoint-by-url
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [line-number url url-regex script-hash column-number condition]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setBreakpointByUrl"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:line-number "lineNumber",
      :url "url",
      :url-regex "urlRegex",
      :script-hash "scriptHash",
      :column-number "columnNumber",
      :condition "condition"})]
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
 set-breakpoint-by-url
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::line-number]
    :opt-un
    [::url
     ::url-regex
     ::script-hash
     ::column-number
     ::condition]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::line-number]
    :opt-un
    [::url
     ::url-regex
     ::script-hash
     ::column-number
     ::condition])))
 :ret
 (s/keys
  :req-un
  [::breakpoint-id
   ::locations]))

(defn
 set-breakpoint-on-function-call
 "Sets JavaScript breakpoint before each call to the given function.\nIf another function was created from the same source as a given one,\ncalling it will also trigger the breakpoint.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :object-id | Function object id.\n  :condition | Expression to use as a breakpoint condition. When specified, debugger will\nstop on the breakpoint if this expression evaluates to true. (optional)\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :breakpoint-id | Id of the created breakpoint for further reference."
 ([]
  (set-breakpoint-on-function-call
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id condition]}]
  (set-breakpoint-on-function-call
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id condition]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setBreakpointOnFunctionCall"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:object-id "objectId", :condition "condition"})]
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
 set-breakpoint-on-function-call
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
    [::condition]))
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
    [::condition])))
 :ret
 (s/keys
  :req-un
  [::breakpoint-id]))

(defn
 set-breakpoints-active
 "Activates / deactivates all breakpoints on the page.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :active | New value for breakpoints active state."
 ([]
  (set-breakpoints-active
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [active]}]
  (set-breakpoints-active
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [active]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setBreakpointsActive"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:active "active"})]
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
 set-breakpoints-active
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::active]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::active])))
 :ret
 (s/keys))

(defn
 set-pause-on-exceptions
 "Defines pause on exceptions state. Can be set to stop on all exceptions, uncaught exceptions or\nno exceptions. Initial pause on exceptions state is `none`.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :state | Pause on exceptions mode."
 ([]
  (set-pause-on-exceptions
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [state]}]
  (set-pause-on-exceptions
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [state]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setPauseOnExceptions"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:state "state"})]
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
 set-pause-on-exceptions
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::state]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::state])))
 :ret
 (s/keys))

(defn
 set-return-value
 "Changes return value in top frame. Available only at return break position.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :new-value | New return value."
 ([]
  (set-return-value
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [new-value]}]
  (set-return-value
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [new-value]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setReturnValue"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:new-value "newValue"})]
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
 set-return-value
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::new-value]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::new-value])))
 :ret
 (s/keys))

(defn
 set-script-source
 "Edits JavaScript source live.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :script-id     | Id of the script to edit.\n  :script-source | New content of the script.\n  :dry-run       | If true the change will not actually be applied. Dry run may be used to get result\ndescription without actually modifying the code. (optional)\n\nReturn map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :call-frames          | New stack trace in case editing has happened while VM was stopped. (optional)\n  :stack-changed        | Whether current call stack  was modified after applying the changes. (optional)\n  :async-stack-trace    | Async stack trace, if any. (optional)\n  :async-stack-trace-id | Async stack trace, if any. (optional)\n  :exception-details    | Exception details if any. (optional)"
 ([]
  (set-script-source
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [script-id script-source dry-run]}]
  (set-script-source
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [script-id script-source dry-run]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setScriptSource"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:script-id "scriptId",
      :script-source "scriptSource",
      :dry-run "dryRun"})]
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
 set-script-source
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::script-id
     ::script-source]
    :opt-un
    [::dry-run]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::script-id
     ::script-source]
    :opt-un
    [::dry-run])))
 :ret
 (s/keys
  :opt-un
  [::call-frames
   ::stack-changed
   ::async-stack-trace
   ::async-stack-trace-id
   ::exception-details]))

(defn
 set-skip-all-pauses
 "Makes page not interrupt on any pauses (breakpoint, exception, dom exception etc).\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :skip | New value for skip pauses state."
 ([]
  (set-skip-all-pauses
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [skip]}]
  (set-skip-all-pauses
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [skip]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setSkipAllPauses"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:skip "skip"})]
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
 set-skip-all-pauses
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::skip]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::skip])))
 :ret
 (s/keys))

(defn
 set-variable-value
 "Changes value of variable in a callframe. Object-based scopes are not supported and must be\nmutated manually.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :scope-number  | 0-based number of scope as was listed in scope chain. Only 'local', 'closure' and 'catch'\nscope types are allowed. Other scopes could be manipulated manually.\n  :variable-name | Variable name.\n  :new-value     | New variable value.\n  :call-frame-id | Id of callframe that holds variable."
 ([]
  (set-variable-value
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [scope-number variable-name new-value call-frame-id]}]
  (set-variable-value
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [scope-number variable-name new-value call-frame-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.setVariableValue"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:scope-number "scopeNumber",
      :variable-name "variableName",
      :new-value "newValue",
      :call-frame-id "callFrameId"})]
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
 set-variable-value
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::scope-number
     ::variable-name
     ::new-value
     ::call-frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::scope-number
     ::variable-name
     ::new-value
     ::call-frame-id])))
 :ret
 (s/keys))

(defn
 step-into
 "Steps into the function call.\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :break-on-async-call | Debugger will issue additional Debugger.paused notification if any async task is scheduled\nbefore next pause. (optional)"
 ([]
  (step-into
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [break-on-async-call]}]
  (step-into
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [break-on-async-call]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.stepInto"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:break-on-async-call "breakOnAsyncCall"})]
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
 step-into
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::break-on-async-call]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::break-on-async-call])))
 :ret
 (s/keys))

(defn
 step-out
 "Steps out of the function call."
 ([]
  (step-out
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (step-out
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.stepOut"
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
 step-out
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
 step-over
 "Steps over the statement."
 ([]
  (step-over
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (step-over
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Debugger.stepOver"
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
 step-over
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
