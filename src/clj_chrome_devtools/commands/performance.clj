(ns clj-chrome-devtools.commands.performance
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::metric
 (s/keys
  :req-un
  [::name
   ::value]))
(defn
 disable
 "Disable collecting and reporting metrics."
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
   "Performance"
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
 "Enable collecting and reporting metrics."
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
   "Performance"
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
 set-time-domain
 "Sets time domain to use for collecting and reporting duration metrics.\nNote that this must be called before enabling metrics collection. Calling\nthis method while metrics collection is enabled returns an error.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :time-domain | Time domain"
 ([]
  (set-time-domain
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [time-domain]}]
  (set-time-domain
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [time-domain]}]
  (cmd/command
   connection
   "Performance"
   "setTimeDomain"
   params
   {:time-domain "timeDomain"})))

(s/fdef
 set-time-domain
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::time-domain]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::time-domain])))
 :ret
 (s/keys))

(defn
 get-metrics
 "Retrieve current values of run-time metrics.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :metrics | Current values for run-time metrics."
 ([]
  (get-metrics
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-metrics
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Performance"
   "getMetrics"
   params
   {})))

(s/fdef
 get-metrics
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
  [::metrics]))
