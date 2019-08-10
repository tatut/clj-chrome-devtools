(ns clj-chrome-devtools.commands.database
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::database-id
 string?)

(s/def
 ::database
 (s/keys
  :req-un
  [::id
   ::domain
   ::name
   ::version]))

(s/def
 ::error
 (s/keys
  :req-un
  [::message
   ::code]))
(defn
 disable
 "Disables database tracking, prevents database events from being sent to the client."
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
   "Database"
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
 "Enables database tracking, database events will now be delivered to the client."
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
   "Database"
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
 execute-sql
 "\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :database-id | null\n  :query       | null\n\nReturn map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :column-names | null (optional)\n  :values       | null (optional)\n  :sql-error    | null (optional)"
 ([]
  (execute-sql
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [database-id query]}]
  (execute-sql
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [database-id query]}]
  (cmd/command
   connection
   "Database"
   "executeSQL"
   params
   {:database-id "databaseId", :query "query"})))

(s/fdef
 execute-sql
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::database-id
     ::query]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::database-id
     ::query])))
 :ret
 (s/keys
  :opt-un
  [::column-names
   ::values
   ::sql-error]))

(defn
 get-database-table-names
 "\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :database-id | null\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :table-names | null"
 ([]
  (get-database-table-names
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [database-id]}]
  (get-database-table-names
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [database-id]}]
  (cmd/command
   connection
   "Database"
   "getDatabaseTableNames"
   params
   {:database-id "databaseId"})))

(s/fdef
 get-database-table-names
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::database-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::database-id])))
 :ret
 (s/keys
  :req-un
  [::table-names]))
