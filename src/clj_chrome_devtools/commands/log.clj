(ns clj-chrome-devtools.commands.log
  "Provides access to log entries."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::log-entry
 (s/keys
  :req-un
  [::source
   ::level
   ::text
   ::timestamp]
  :opt-un
  [::category
   ::url
   ::line-number
   ::stack-trace
   ::network-request-id
   ::worker-id
   ::args]))

(s/def
 ::violation-setting
 (s/keys
  :req-un
  [::name
   ::threshold]))
(defn
 clear
 "Clears the log."
 ([]
  (clear
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Log"
   "clear"
   params
   {})))

(s/fdef
 clear
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
 "Disables log domain, prevents further log entries from being reported to the client."
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
   "Log"
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
 "Enables log domain, sends the entries collected so far to the client by means of the\n`entryAdded` notification."
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
   "Log"
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
 start-violations-report
 "start violation reporting.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :config | Configuration for violations."
 ([]
  (start-violations-report
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [config]}]
  (start-violations-report
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [config]}]
  (cmd/command
   connection
   "Log"
   "startViolationsReport"
   params
   {:config "config"})))

(s/fdef
 start-violations-report
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::config]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::config])))
 :ret
 (s/keys))

(defn
 stop-violations-report
 "Stop violation reporting."
 ([]
  (stop-violations-report
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-violations-report
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Log"
   "stopViolationsReport"
   params
   {})))

(s/fdef
 stop-violations-report
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
