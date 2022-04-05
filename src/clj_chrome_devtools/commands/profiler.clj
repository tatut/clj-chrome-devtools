(ns clj-chrome-devtools.commands.profiler
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::profile-node
 (s/keys
  :req-un
  [::id
   ::call-frame]
  :opt-un
  [::hit-count
   ::children
   ::deopt-reason
   ::position-ticks]))

(s/def
 ::profile
 (s/keys
  :req-un
  [::nodes
   ::start-time
   ::end-time]
  :opt-un
  [::samples
   ::time-deltas]))

(s/def
 ::position-tick-info
 (s/keys
  :req-un
  [::line
   ::ticks]))

(s/def
 ::coverage-range
 (s/keys
  :req-un
  [::start-offset
   ::end-offset
   ::count]))

(s/def
 ::function-coverage
 (s/keys
  :req-un
  [::function-name
   ::ranges
   ::is-block-coverage]))

(s/def
 ::script-coverage
 (s/keys
  :req-un
  [::script-id
   ::url
   ::functions]))

(s/def
 ::type-object
 (s/keys
  :req-un
  [::name]))

(s/def
 ::type-profile-entry
 (s/keys
  :req-un
  [::offset
   ::types]))

(s/def
 ::script-type-profile
 (s/keys
  :req-un
  [::script-id
   ::url
   ::entries]))
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
   "Profiler"
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
   "Profiler"
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
 get-best-effort-coverage
 "Collect coverage data for the current isolate. The coverage data may be incomplete due to\ngarbage collection.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | Coverage data for the current isolate."
 ([]
  (get-best-effort-coverage
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-best-effort-coverage
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "getBestEffortCoverage"
   params
   {})))

(s/fdef
 get-best-effort-coverage
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
  [::result]))

(defn
 set-sampling-interval
 "Changes CPU profiler sampling interval. Must be called before CPU profiles recording started.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :interval | New sampling interval in microseconds."
 ([]
  (set-sampling-interval
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [interval]}]
  (set-sampling-interval
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [interval]}]
  (cmd/command
   connection
   "Profiler"
   "setSamplingInterval"
   params
   {:interval "interval"})))

(s/fdef
 set-sampling-interval
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::interval]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::interval])))
 :ret
 (s/keys))

(defn
 start
 ""
 ([]
  (start
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (start
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "start"
   params
   {})))

(s/fdef
 start
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
 start-precise-coverage
 "Enable precise code coverage. Coverage data for JavaScript executed before enabling precise code\ncoverage may be incomplete. Enabling prevents running optimized code and resets execution\ncounters.\n\nParameters map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :call-count              | Collect accurate call counts beyond simple 'covered' or 'not covered'. (optional)\n  :detailed                | Collect block-based coverage. (optional)\n  :allow-triggered-updates | Allow the backend to send updates on its own initiative (optional)\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :timestamp | Monotonically increasing time (in seconds) when the coverage update was taken in the backend."
 ([]
  (start-precise-coverage
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [call-count detailed allow-triggered-updates]}]
  (start-precise-coverage
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [call-count detailed allow-triggered-updates]}]
  (cmd/command
   connection
   "Profiler"
   "startPreciseCoverage"
   params
   {:call-count "callCount",
    :detailed "detailed",
    :allow-triggered-updates "allowTriggeredUpdates"})))

(s/fdef
 start-precise-coverage
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::call-count
     ::detailed
     ::allow-triggered-updates]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::call-count
     ::detailed
     ::allow-triggered-updates])))
 :ret
 (s/keys
  :req-un
  [::timestamp]))

(defn
 start-type-profile
 "Enable type profile."
 ([]
  (start-type-profile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (start-type-profile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "startTypeProfile"
   params
   {})))

(s/fdef
 start-type-profile
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
 stop
 "\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :profile | Recorded profile."
 ([]
  (stop
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "stop"
   params
   {})))

(s/fdef
 stop
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
 stop-precise-coverage
 "Disable precise code coverage. Disabling releases unnecessary execution count records and allows\nexecuting optimized code."
 ([]
  (stop-precise-coverage
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-precise-coverage
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "stopPreciseCoverage"
   params
   {})))

(s/fdef
 stop-precise-coverage
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
 stop-type-profile
 "Disable type profile. Disabling releases type profile data collected so far."
 ([]
  (stop-type-profile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-type-profile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "stopTypeProfile"
   params
   {})))

(s/fdef
 stop-type-profile
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
 take-precise-coverage
 "Collect coverage data for the current isolate, and resets execution counters. Precise code\ncoverage needs to have started.\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :result    | Coverage data for the current isolate.\n  :timestamp | Monotonically increasing time (in seconds) when the coverage update was taken in the backend."
 ([]
  (take-precise-coverage
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (take-precise-coverage
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "takePreciseCoverage"
   params
   {})))

(s/fdef
 take-precise-coverage
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
  [::result
   ::timestamp]))

(defn
 take-type-profile
 "Collect type profile.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | Type profile for all scripts since startTypeProfile() was turned on."
 ([]
  (take-type-profile
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (take-type-profile
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Profiler"
   "takeTypeProfile"
   params
   {})))

(s/fdef
 take-type-profile
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
  [::result]))
