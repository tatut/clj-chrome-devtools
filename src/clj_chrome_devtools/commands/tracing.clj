(ns clj-chrome-devtools.commands.tracing
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::memory-dump-config
 (s/keys))

(s/def
 ::trace-config
 (s/keys
  :opt-un
  [::record-mode
   ::enable-sampling
   ::enable-systrace
   ::enable-argument-filter
   ::included-categories
   ::excluded-categories
   ::synthetic-delays
   ::memory-dump-config]))

(s/def
 ::stream-format
 #{"json" "proto"})

(s/def
 ::stream-compression
 #{"none" "gzip"})
(defn
 end
 "Stop trace events collection."
 ([]
  (end
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (end
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Tracing.end"
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
 end
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
 get-categories
 "Gets supported tracing categories.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :categories | A list of supported tracing categories."
 ([]
  (get-categories
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-categories
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Tracing.getCategories"
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
 get-categories
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
  [::categories]))

(defn
 record-clock-sync-marker
 "Record a clock sync marker in the trace.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :sync-id | The ID of this clock sync marker"
 ([]
  (record-clock-sync-marker
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [sync-id]}]
  (record-clock-sync-marker
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [sync-id]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Tracing.recordClockSyncMarker"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:sync-id "syncId"})]
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
 record-clock-sync-marker
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::sync-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::sync-id])))
 :ret
 (s/keys))

(defn
 request-memory-dump
 "Request a global memory dump.\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :dump-guid | GUID of the resulting global memory dump.\n  :success   | True iff the global memory dump succeeded."
 ([]
  (request-memory-dump
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (request-memory-dump
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Tracing.requestMemoryDump"
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
 request-memory-dump
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
  [::dump-guid
   ::success]))

(defn
 start
 "Start trace events collection.\n\nParameters map keys:\n\n\n  Key                              | Description \n  ---------------------------------|------------ \n  :categories                      | Category/tag filter (optional)\n  :options                         | Tracing options (optional)\n  :buffer-usage-reporting-interval | If set, the agent will issue bufferUsage events at this interval, specified in milliseconds (optional)\n  :transfer-mode                   | Whether to report trace events as series of dataCollected events or to save trace to a\nstream (defaults to `ReportEvents`). (optional)\n  :stream-format                   | Trace data format to use. This only applies when using `ReturnAsStream`\ntransfer mode (defaults to `json`). (optional)\n  :stream-compression              | Compression format to use. This only applies when using `ReturnAsStream`\ntransfer mode (defaults to `none`) (optional)\n  :trace-config                    | null (optional)"
 ([]
  (start
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [categories
     options
     buffer-usage-reporting-interval
     transfer-mode
     stream-format
     stream-compression
     trace-config]}]
  (start
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [categories
     options
     buffer-usage-reporting-interval
     transfer-mode
     stream-format
     stream-compression
     trace-config]}]
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "Tracing.start"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:categories "categories",
      :options "options",
      :buffer-usage-reporting-interval "bufferUsageReportingInterval",
      :transfer-mode "transferMode",
      :stream-format "streamFormat",
      :stream-compression "streamCompression",
      :trace-config "traceConfig"})]
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
 start
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::categories
     ::options
     ::buffer-usage-reporting-interval
     ::transfer-mode
     ::stream-format
     ::stream-compression
     ::trace-config]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::categories
     ::options
     ::buffer-usage-reporting-interval
     ::transfer-mode
     ::stream-format
     ::stream-compression
     ::trace-config])))
 :ret
 (s/keys))
