(ns clj-chrome-devtools.commands.heap-profiler
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::heap-snapshot-object-id
 string?)

(s/def
 ::sampling-heap-profile-node
 (s/keys
  :req-un
  [::call-frame
   ::self-size
   ::id
   ::children]))

(s/def
 ::sampling-heap-profile-sample
 (s/keys
  :req-un
  [::size
   ::node-id
   ::ordinal]))

(s/def
 ::sampling-heap-profile
 (s/keys
  :req-un
  [::head
   ::samples]))
(defn
 add-inspected-heap-object
 "Enables console to refer to the node with given id via $x (see Command Line API for more details\n$x functions).\n\nParameters map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :heap-object-id | Heap snapshot object id to be accessible by means of $x command line API."
 ([]
  (add-inspected-heap-object
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [heap-object-id]}]
  (add-inspected-heap-object
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [heap-object-id]}]
  (cmd/command
   connection
   "HeapProfiler"
   "addInspectedHeapObject"
   params
   {:heap-object-id "heapObjectId"})))

(s/fdef
 add-inspected-heap-object
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::heap-object-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::heap-object-id])))
 :ret
 (s/keys))

(defn
 collect-garbage
 ""
 ([]
  (collect-garbage
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (collect-garbage
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "HeapProfiler"
   "collectGarbage"
   params
   {})))

(s/fdef
 collect-garbage
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
 disable
 ""
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
   "HeapProfiler"
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
 ""
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
   "HeapProfiler"
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
 get-heap-object-id
 "\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :object-id | Identifier of the object to get heap object id for.\n\nReturn map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :heap-snapshot-object-id | Id of the heap snapshot object corresponding to the passed remote object id."
 ([]
  (get-heap-object-id
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id]}]
  (get-heap-object-id
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id]}]
  (cmd/command
   connection
   "HeapProfiler"
   "getHeapObjectId"
   params
   {:object-id "objectId"})))

(s/fdef
 get-heap-object-id
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
  [::heap-snapshot-object-id]))

(defn
 get-object-by-heap-object-id
 "\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :object-id    | null\n  :object-group | Symbolic group name that can be used to release multiple objects. (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | Evaluation result."
 ([]
  (get-object-by-heap-object-id
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id object-group]}]
  (get-object-by-heap-object-id
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id object-group]}]
  (cmd/command
   connection
   "HeapProfiler"
   "getObjectByHeapObjectId"
   params
   {:object-id "objectId", :object-group "objectGroup"})))

(s/fdef
 get-object-by-heap-object-id
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
    [::object-group]))
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
    [::object-group])))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 get-sampling-profile
 "\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :profile | Return the sampling profile being collected."
 ([]
  (get-sampling-profile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-sampling-profile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "HeapProfiler"
   "getSamplingProfile"
   params
   {})))

(s/fdef
 get-sampling-profile
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
  [::profile]))

(defn
 start-sampling
 "\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :sampling-interval | Average sample interval in bytes. Poisson distribution is used for the intervals. The\ndefault value is 32768 bytes. (optional)"
 ([]
  (start-sampling
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [sampling-interval]}]
  (start-sampling
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [sampling-interval]}]
  (cmd/command
   connection
   "HeapProfiler"
   "startSampling"
   params
   {:sampling-interval "samplingInterval"})))

(s/fdef
 start-sampling
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::sampling-interval]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::sampling-interval])))
 :ret
 (s/keys))

(defn
 start-tracking-heap-objects
 "\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :track-allocations | null (optional)"
 ([]
  (start-tracking-heap-objects
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [track-allocations]}]
  (start-tracking-heap-objects
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [track-allocations]}]
  (cmd/command
   connection
   "HeapProfiler"
   "startTrackingHeapObjects"
   params
   {:track-allocations "trackAllocations"})))

(s/fdef
 start-tracking-heap-objects
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::track-allocations]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::track-allocations])))
 :ret
 (s/keys))

(defn
 stop-sampling
 "\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :profile | Recorded sampling heap profile."
 ([]
  (stop-sampling
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-sampling
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "HeapProfiler"
   "stopSampling"
   params
   {})))

(s/fdef
 stop-sampling
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
  [::profile]))

(defn
 stop-tracking-heap-objects
 "\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :report-progress | If true 'reportHeapSnapshotProgress' events will be generated while snapshot is being taken\nwhen the tracking is stopped. (optional)"
 ([]
  (stop-tracking-heap-objects
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [report-progress]}]
  (stop-tracking-heap-objects
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [report-progress]}]
  (cmd/command
   connection
   "HeapProfiler"
   "stopTrackingHeapObjects"
   params
   {:report-progress "reportProgress"})))

(s/fdef
 stop-tracking-heap-objects
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::report-progress]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::report-progress])))
 :ret
 (s/keys))

(defn
 take-heap-snapshot
 "\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :report-progress | If true 'reportHeapSnapshotProgress' events will be generated while snapshot is being taken. (optional)"
 ([]
  (take-heap-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [report-progress]}]
  (take-heap-snapshot
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [report-progress]}]
  (cmd/command
   connection
   "HeapProfiler"
   "takeHeapSnapshot"
   params
   {:report-progress "reportProgress"})))

(s/fdef
 take-heap-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::report-progress]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::report-progress])))
 :ret
 (s/keys))
