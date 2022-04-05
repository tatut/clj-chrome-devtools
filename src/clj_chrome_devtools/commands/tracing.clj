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

(s/def
 ::memory-dump-level-of-detail
 #{"detailed" "light" "background"})

(s/def
 ::tracing-backend
 #{"auto" "chrome" "system"})
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
  (cmd/command
   connection
   "Tracing"
   "end"
   params
   {})))

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
  (cmd/command
   connection
   "Tracing"
   "getCategories"
   params
   {})))

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
  (cmd/command
   connection
   "Tracing"
   "recordClockSyncMarker"
   params
   {:sync-id "syncId"})))

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
 "Request a global memory dump.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :deterministic   | Enables more deterministic results by forcing garbage collection (optional)\n  :level-of-detail | Specifies level of details in memory dump. Defaults to \"detailed\". (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :dump-guid | GUID of the resulting global memory dump.\n  :success   | True iff the global memory dump succeeded."
 ([]
  (request-memory-dump
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [deterministic level-of-detail]}]
  (request-memory-dump
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [deterministic level-of-detail]}]
  (cmd/command
   connection
   "Tracing"
   "requestMemoryDump"
   params
   {:deterministic "deterministic", :level-of-detail "levelOfDetail"})))

(s/fdef
 request-memory-dump
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::deterministic
     ::level-of-detail]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::deterministic
     ::level-of-detail])))
 :ret
 (s/keys
  :req-un
  [::dump-guid
   ::success]))

(defn
 start
 "Start trace events collection.\n\nParameters map keys:\n\n\n  Key                              | Description \n  ---------------------------------|------------ \n  :categories                      | Category/tag filter (optional)\n  :options                         | Tracing options (optional)\n  :buffer-usage-reporting-interval | If set, the agent will issue bufferUsage events at this interval, specified in milliseconds (optional)\n  :transfer-mode                   | Whether to report trace events as series of dataCollected events or to save trace to a\nstream (defaults to `ReportEvents`). (optional)\n  :stream-format                   | Trace data format to use. This only applies when using `ReturnAsStream`\ntransfer mode (defaults to `json`). (optional)\n  :stream-compression              | Compression format to use. This only applies when using `ReturnAsStream`\ntransfer mode (defaults to `none`) (optional)\n  :trace-config                    | null (optional)\n  :perfetto-config                 | Base64-encoded serialized perfetto.protos.TraceConfig protobuf message\nWhen specified, the parameters `categories`, `options`, `traceConfig`\nare ignored. (Encoded as a base64 string when passed over JSON) (optional)\n  :tracing-backend                 | Backend type (defaults to `auto`) (optional)"
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
     trace-config
     perfetto-config
     tracing-backend]}]
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
     trace-config
     perfetto-config
     tracing-backend]}]
  (cmd/command
   connection
   "Tracing"
   "start"
   params
   {:categories "categories",
    :options "options",
    :buffer-usage-reporting-interval "bufferUsageReportingInterval",
    :transfer-mode "transferMode",
    :stream-format "streamFormat",
    :stream-compression "streamCompression",
    :trace-config "traceConfig",
    :perfetto-config "perfettoConfig",
    :tracing-backend "tracingBackend"})))

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
     ::trace-config
     ::perfetto-config
     ::tracing-backend]))
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
     ::trace-config
     ::perfetto-config
     ::tracing-backend])))
 :ret
 (s/keys))
