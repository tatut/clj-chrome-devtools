(ns clj-chrome-devtools.commands.runtime
  "Runtime domain exposes JavaScript runtime by means of remote evaluation and mirror objects.\nEvaluation results are returned as mirror object that expose object type, string representation\nand unique identifier that can be used for further object reference. Original objects are\nmaintained in memory unless they are either explicitly released or are released along with the\nother objects in their object group."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(s/def
 ::script-id
 string?)

(s/def
 ::remote-object-id
 string?)

(s/def
 ::unserializable-value
 string?)

(s/def
 ::remote-object
 (s/keys
  :req-un
  [::type]
  :opt-un
  [::subtype
   ::class-name
   ::value
   ::unserializable-value
   ::description
   ::object-id
   ::preview
   ::custom-preview]))

(s/def
 ::custom-preview
 (s/keys
  :req-un
  [::header]
  :opt-un
  [::body-getter-id]))

(s/def
 ::object-preview
 (s/keys
  :req-un
  [::type
   ::overflow
   ::properties]
  :opt-un
  [::subtype
   ::description
   ::entries]))

(s/def
 ::property-preview
 (s/keys
  :req-un
  [::name
   ::type]
  :opt-un
  [::value
   ::value-preview
   ::subtype]))

(s/def
 ::entry-preview
 (s/keys
  :req-un
  [::value]
  :opt-un
  [::key]))

(s/def
 ::property-descriptor
 (s/keys
  :req-un
  [::name
   ::configurable
   ::enumerable]
  :opt-un
  [::value
   ::writable
   ::get
   ::set
   ::was-thrown
   ::is-own
   ::symbol]))

(s/def
 ::internal-property-descriptor
 (s/keys
  :req-un
  [::name]
  :opt-un
  [::value]))

(s/def
 ::private-property-descriptor
 (s/keys
  :req-un
  [::name
   ::value]))

(s/def
 ::call-argument
 (s/keys
  :opt-un
  [::value
   ::unserializable-value
   ::object-id]))

(s/def
 ::execution-context-id
 integer?)

(s/def
 ::execution-context-description
 (s/keys
  :req-un
  [::id
   ::origin
   ::name]
  :opt-un
  [::aux-data]))

(s/def
 ::exception-details
 (s/keys
  :req-un
  [::exception-id
   ::text
   ::line-number
   ::column-number]
  :opt-un
  [::script-id
   ::url
   ::stack-trace
   ::exception
   ::execution-context-id]))

(s/def
 ::timestamp
 number?)

(s/def
 ::time-delta
 number?)

(s/def
 ::call-frame
 (s/keys
  :req-un
  [::function-name
   ::script-id
   ::url
   ::line-number
   ::column-number]))

(s/def
 ::stack-trace
 (s/keys
  :req-un
  [::call-frames]
  :opt-un
  [::description
   ::parent
   ::parent-id]))

(s/def
 ::unique-debugger-id
 string?)

(s/def
 ::stack-trace-id
 (s/keys
  :req-un
  [::id]
  :opt-un
  [::debugger-id]))
(defn
 await-promise
 "Add handler to promise with given promise object id.\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :promise-object-id | Identifier of the promise.\n  :return-by-value   | Whether the result is expected to be a JSON object that should be sent by value. (optional)\n  :generate-preview  | Whether preview should be generated for the result. (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :result            | Promise result. Will contain rejected value if promise was rejected.\n  :exception-details | Exception details if stack strace is available. (optional)"
 ([]
  (await-promise
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [promise-object-id return-by-value generate-preview]}]
  (await-promise
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [promise-object-id return-by-value generate-preview]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.awaitPromise"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:promise-object-id "promiseObjectId",
      :return-by-value "returnByValue",
      :generate-preview "generatePreview"})]
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
 await-promise
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::promise-object-id]
    :opt-un
    [::return-by-value
     ::generate-preview]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::promise-object-id]
    :opt-un
    [::return-by-value
     ::generate-preview])))
 :ret
 (s/keys
  :req-un
  [::result]
  :opt-un
  [::exception-details]))

(defn
 call-function-on
 "Calls function with given declaration on the given object. Object group of the result is\ninherited from the target object.\n\nParameters map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :function-declaration | Declaration of the function to call.\n  :object-id            | Identifier of the object to call function on. Either objectId or executionContextId should\nbe specified. (optional)\n  :arguments            | Call arguments. All call arguments must belong to the same JavaScript world as the target\nobject. (optional)\n  :silent               | In silent mode exceptions thrown during evaluation are not reported and do not pause\nexecution. Overrides `setPauseOnException` state. (optional)\n  :return-by-value      | Whether the result is expected to be a JSON object which should be sent by value. (optional)\n  :generate-preview     | Whether preview should be generated for the result. (optional)\n  :user-gesture         | Whether execution should be treated as initiated by user in the UI. (optional)\n  :await-promise        | Whether execution should `await` for resulting value and return once awaited promise is\nresolved. (optional)\n  :execution-context-id | Specifies execution context which global object will be used to call function on. Either\nexecutionContextId or objectId should be specified. (optional)\n  :object-group         | Symbolic group name that can be used to release multiple objects. If objectGroup is not\nspecified and objectId is, objectGroup will be inherited from object. (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :result            | Call result.\n  :exception-details | Exception details. (optional)"
 ([]
  (call-function-on
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [function-declaration
     object-id
     arguments
     silent
     return-by-value
     generate-preview
     user-gesture
     await-promise
     execution-context-id
     object-group]}]
  (call-function-on
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [function-declaration
     object-id
     arguments
     silent
     return-by-value
     generate-preview
     user-gesture
     await-promise
     execution-context-id
     object-group]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.callFunctionOn"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:object-group "objectGroup",
      :object-id "objectId",
      :silent "silent",
      :arguments "arguments",
      :await-promise "awaitPromise",
      :function-declaration "functionDeclaration",
      :return-by-value "returnByValue",
      :execution-context-id "executionContextId",
      :generate-preview "generatePreview",
      :user-gesture "userGesture"})]
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
 call-function-on
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::function-declaration]
    :opt-un
    [::object-id
     ::arguments
     ::silent
     ::return-by-value
     ::generate-preview
     ::user-gesture
     ::await-promise
     ::execution-context-id
     ::object-group]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::function-declaration]
    :opt-un
    [::object-id
     ::arguments
     ::silent
     ::return-by-value
     ::generate-preview
     ::user-gesture
     ::await-promise
     ::execution-context-id
     ::object-group])))
 :ret
 (s/keys
  :req-un
  [::result]
  :opt-un
  [::exception-details]))

(defn
 compile-script
 "Compiles expression.\n\nParameters map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :expression           | Expression to compile.\n  :source-url           | Source url to be set for the script.\n  :persist-script       | Specifies whether the compiled script should be persisted.\n  :execution-context-id | Specifies in which execution context to perform script run. If the parameter is omitted the\nevaluation will be performed in the context of the inspected page. (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :script-id         | Id of the script. (optional)\n  :exception-details | Exception details. (optional)"
 ([]
  (compile-script
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [expression source-url persist-script execution-context-id]}]
  (compile-script
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [expression source-url persist-script execution-context-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.compileScript"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:expression "expression",
      :source-url "sourceURL",
      :persist-script "persistScript",
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
 compile-script
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::expression
     ::source-url
     ::persist-script]
    :opt-un
    [::execution-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::expression
     ::source-url
     ::persist-script]
    :opt-un
    [::execution-context-id])))
 :ret
 (s/keys
  :opt-un
  [::script-id
   ::exception-details]))

(defn
 disable
 "Disables reporting of execution contexts creation."
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
    "Runtime.disable"
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
 discard-console-entries
 "Discards collected exceptions and console API calls."
 ([]
  (discard-console-entries
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (discard-console-entries
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.discardConsoleEntries"
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
 discard-console-entries
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
 "Enables reporting of execution contexts creation by means of `executionContextCreated` event.\nWhen the reporting gets enabled the event will be sent immediately for each existing execution\ncontext."
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
    "Runtime.enable"
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
 evaluate
 "Evaluates expression on global object.\n\nParameters map keys:\n\n\n  Key                       | Description \n  --------------------------|------------ \n  :expression               | Expression to evaluate.\n  :object-group             | Symbolic group name that can be used to release multiple objects. (optional)\n  :include-command-line-api | Determines whether Command Line API should be available during the evaluation. (optional)\n  :silent                   | In silent mode exceptions thrown during evaluation are not reported and do not pause\nexecution. Overrides `setPauseOnException` state. (optional)\n  :context-id               | Specifies in which execution context to perform evaluation. If the parameter is omitted the\nevaluation will be performed in the context of the inspected page. (optional)\n  :return-by-value          | Whether the result is expected to be a JSON object that should be sent by value. (optional)\n  :generate-preview         | Whether preview should be generated for the result. (optional)\n  :user-gesture             | Whether execution should be treated as initiated by user in the UI. (optional)\n  :await-promise            | Whether execution should `await` for resulting value and return once awaited promise is\nresolved. (optional)\n  :throw-on-side-effect     | Whether to throw an exception if side effect cannot be ruled out during evaluation. (optional)\n  :timeout                  | Terminate execution after timing out (number of milliseconds). (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :result            | Evaluation result.\n  :exception-details | Exception details. (optional)"
 ([]
  (evaluate
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [expression
     object-group
     include-command-line-api
     silent
     context-id
     return-by-value
     generate-preview
     user-gesture
     await-promise
     throw-on-side-effect
     timeout]}]
  (evaluate
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [expression
     object-group
     include-command-line-api
     silent
     context-id
     return-by-value
     generate-preview
     user-gesture
     await-promise
     throw-on-side-effect
     timeout]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.evaluate"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:object-group "objectGroup",
      :expression "expression",
      :silent "silent",
      :throw-on-side-effect "throwOnSideEffect",
      :await-promise "awaitPromise",
      :return-by-value "returnByValue",
      :timeout "timeout",
      :generate-preview "generatePreview",
      :include-command-line-api "includeCommandLineAPI",
      :user-gesture "userGesture",
      :context-id "contextId"})]
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
 evaluate
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::expression]
    :opt-un
    [::object-group
     ::include-command-line-api
     ::silent
     ::context-id
     ::return-by-value
     ::generate-preview
     ::user-gesture
     ::await-promise
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
    [::expression]
    :opt-un
    [::object-group
     ::include-command-line-api
     ::silent
     ::context-id
     ::return-by-value
     ::generate-preview
     ::user-gesture
     ::await-promise
     ::throw-on-side-effect
     ::timeout])))
 :ret
 (s/keys
  :req-un
  [::result]
  :opt-un
  [::exception-details]))

(defn
 get-isolate-id
 "Returns the isolate id.\n\nReturn map keys:\n\n\n  Key | Description \n  ----|------------ \n  :id | The isolate id."
 ([]
  (get-isolate-id
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-isolate-id
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.getIsolateId"
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
 get-isolate-id
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
  [::id]))

(defn
 get-heap-usage
 "Returns the JavaScript heap usage.\nIt is the total usage of the corresponding isolate not scoped to a particular Runtime.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :used-size  | Used heap size in bytes.\n  :total-size | Allocated heap size in bytes."
 ([]
  (get-heap-usage
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-heap-usage
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.getHeapUsage"
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
 get-heap-usage
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
  [::used-size
   ::total-size]))

(defn
 get-properties
 "Returns properties of a given object. Object group of the result is inherited from the target\nobject.\n\nParameters map keys:\n\n\n  Key                       | Description \n  --------------------------|------------ \n  :object-id                | Identifier of the object to return properties for.\n  :own-properties           | If true, returns properties belonging only to the element itself, not to its prototype\nchain. (optional)\n  :accessor-properties-only | If true, returns accessor properties (with getter/setter) only; internal properties are not\nreturned either. (optional)\n  :generate-preview         | Whether preview should be generated for the results. (optional)\n\nReturn map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :result              | Object properties.\n  :internal-properties | Internal object properties (only of the element itself). (optional)\n  :private-properties  | Object private properties. (optional)\n  :exception-details   | Exception details. (optional)"
 ([]
  (get-properties
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [object-id
     own-properties
     accessor-properties-only
     generate-preview]}]
  (get-properties
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [object-id
     own-properties
     accessor-properties-only
     generate-preview]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.getProperties"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:object-id "objectId",
      :own-properties "ownProperties",
      :accessor-properties-only "accessorPropertiesOnly",
      :generate-preview "generatePreview"})]
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
 get-properties
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
    [::own-properties
     ::accessor-properties-only
     ::generate-preview]))
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
    [::own-properties
     ::accessor-properties-only
     ::generate-preview])))
 :ret
 (s/keys
  :req-un
  [::result]
  :opt-un
  [::internal-properties
   ::private-properties
   ::exception-details]))

(defn
 global-lexical-scope-names
 "Returns all let, const and class variables from global scope.\n\nParameters map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :execution-context-id | Specifies in which execution context to lookup global scope variables. (optional)\n\nReturn map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :names | null"
 ([]
  (global-lexical-scope-names
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [execution-context-id]}]
  (global-lexical-scope-names
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [execution-context-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.globalLexicalScopeNames"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:execution-context-id "executionContextId"})]
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
 global-lexical-scope-names
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::execution-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::execution-context-id])))
 :ret
 (s/keys
  :req-un
  [::names]))

(defn
 query-objects
 "\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :prototype-object-id | Identifier of the prototype to return objects for.\n  :object-group        | Symbolic group name that can be used to release the results. (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :objects | Array with objects."
 ([]
  (query-objects
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [prototype-object-id object-group]}]
  (query-objects
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [prototype-object-id object-group]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.queryObjects"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:prototype-object-id "prototypeObjectId",
      :object-group "objectGroup"})]
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
 query-objects
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::prototype-object-id]
    :opt-un
    [::object-group]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::prototype-object-id]
    :opt-un
    [::object-group])))
 :ret
 (s/keys
  :req-un
  [::objects]))

(defn
 release-object
 "Releases remote object with given id.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :object-id | Identifier of the object to release."
 ([]
  (release-object
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id]}]
  (release-object
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.releaseObject"
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
 release-object
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
 (s/keys))

(defn
 release-object-group
 "Releases all remote objects that belong to a given group.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :object-group | Symbolic object group name."
 ([]
  (release-object-group
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-group]}]
  (release-object-group
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-group]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.releaseObjectGroup"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:object-group "objectGroup"})]
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
 release-object-group
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::object-group]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::object-group])))
 :ret
 (s/keys))

(defn
 run-if-waiting-for-debugger
 "Tells inspected instance to run if it was waiting for debugger to attach."
 ([]
  (run-if-waiting-for-debugger
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (run-if-waiting-for-debugger
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.runIfWaitingForDebugger"
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
 run-if-waiting-for-debugger
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
 run-script
 "Runs script with given id in a given context.\n\nParameters map keys:\n\n\n  Key                       | Description \n  --------------------------|------------ \n  :script-id                | Id of the script to run.\n  :execution-context-id     | Specifies in which execution context to perform script run. If the parameter is omitted the\nevaluation will be performed in the context of the inspected page. (optional)\n  :object-group             | Symbolic group name that can be used to release multiple objects. (optional)\n  :silent                   | In silent mode exceptions thrown during evaluation are not reported and do not pause\nexecution. Overrides `setPauseOnException` state. (optional)\n  :include-command-line-api | Determines whether Command Line API should be available during the evaluation. (optional)\n  :return-by-value          | Whether the result is expected to be a JSON object which should be sent by value. (optional)\n  :generate-preview         | Whether preview should be generated for the result. (optional)\n  :await-promise            | Whether execution should `await` for resulting value and return once awaited promise is\nresolved. (optional)\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :result            | Run result.\n  :exception-details | Exception details. (optional)"
 ([]
  (run-script
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [script-id
     execution-context-id
     object-group
     silent
     include-command-line-api
     return-by-value
     generate-preview
     await-promise]}]
  (run-script
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [script-id
     execution-context-id
     object-group
     silent
     include-command-line-api
     return-by-value
     generate-preview
     await-promise]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.runScript"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:script-id "scriptId",
      :execution-context-id "executionContextId",
      :object-group "objectGroup",
      :silent "silent",
      :include-command-line-api "includeCommandLineAPI",
      :return-by-value "returnByValue",
      :generate-preview "generatePreview",
      :await-promise "awaitPromise"})]
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
 run-script
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::script-id]
    :opt-un
    [::execution-context-id
     ::object-group
     ::silent
     ::include-command-line-api
     ::return-by-value
     ::generate-preview
     ::await-promise]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::script-id]
    :opt-un
    [::execution-context-id
     ::object-group
     ::silent
     ::include-command-line-api
     ::return-by-value
     ::generate-preview
     ::await-promise])))
 :ret
 (s/keys
  :req-un
  [::result]
  :opt-un
  [::exception-details]))

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
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.setAsyncCallStackDepth"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:max-depth "maxDepth"})]
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
 set-custom-object-formatter-enabled
 "\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | null"
 ([]
  (set-custom-object-formatter-enabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-custom-object-formatter-enabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.setCustomObjectFormatterEnabled"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:enabled "enabled"})]
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
 set-custom-object-formatter-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::enabled])))
 :ret
 (s/keys))

(defn
 set-max-call-stack-size-to-capture
 "\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :size | null"
 ([]
  (set-max-call-stack-size-to-capture
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [size]}]
  (set-max-call-stack-size-to-capture
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [size]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.setMaxCallStackSizeToCapture"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:size "size"})]
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
 set-max-call-stack-size-to-capture
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::size]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::size])))
 :ret
 (s/keys))

(defn
 terminate-execution
 "Terminate current or next JavaScript execution.\nWill cancel the termination when the outer-most script execution ends."
 ([]
  (terminate-execution
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (terminate-execution
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.terminateExecution"
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
 terminate-execution
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
 add-binding
 "If executionContextId is empty, adds binding with the given name on the\nglobal objects of all inspected contexts, including those created later,\nbindings survive reloads.\nIf executionContextId is specified, adds binding only on global object of\ngiven execution context.\nBinding function takes exactly one argument, this argument should be string,\nin case of any other input, function throws an exception.\nEach binding function call produces Runtime.bindingCalled notification.\n\nParameters map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :name                 | null\n  :execution-context-id | null (optional)"
 ([]
  (add-binding
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [name execution-context-id]}]
  (add-binding
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [name execution-context-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.addBinding"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:name "name", :execution-context-id "executionContextId"})]
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
 add-binding
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::name]
    :opt-un
    [::execution-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::name]
    :opt-un
    [::execution-context-id])))
 :ret
 (s/keys))

(defn
 remove-binding
 "This method does not remove binding function from global object but\nunsubscribes current runtime agent from Runtime.bindingCalled notifications.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :name | null"
 ([]
  (remove-binding
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [name]}]
  (remove-binding
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [name]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Runtime.removeBinding"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:name "name"})]
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
 remove-binding
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::name])))
 :ret
 (s/keys))
