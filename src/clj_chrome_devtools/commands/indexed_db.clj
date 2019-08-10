(ns clj-chrome-devtools.commands.indexed-db
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::database-with-object-stores
 (s/keys
  :req-un
  [::name
   ::version
   ::object-stores]))

(s/def
 ::object-store
 (s/keys
  :req-un
  [::name
   ::key-path
   ::auto-increment
   ::indexes]))

(s/def
 ::object-store-index
 (s/keys
  :req-un
  [::name
   ::key-path
   ::unique
   ::multi-entry]))

(s/def
 ::key
 (s/keys
  :req-un
  [::type]
  :opt-un
  [::number
   ::string
   ::date
   ::array]))

(s/def
 ::key-range
 (s/keys
  :req-un
  [::lower-open
   ::upper-open]
  :opt-un
  [::lower
   ::upper]))

(s/def
 ::data-entry
 (s/keys
  :req-un
  [::key
   ::primary-key
   ::value]))

(s/def
 ::key-path
 (s/keys
  :req-un
  [::type]
  :opt-un
  [::string
   ::array]))
(defn
 clear-object-store
 "Clears all entries from an object store.\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :security-origin   | Security origin.\n  :database-name     | Database name.\n  :object-store-name | Object store name."
 ([]
  (clear-object-store
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [security-origin database-name object-store-name]}]
  (clear-object-store
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [security-origin database-name object-store-name]}]
  (cmd/command
   connection
   "IndexedDB"
   "clearObjectStore"
   params
   {:security-origin "securityOrigin",
    :database-name "databaseName",
    :object-store-name "objectStoreName"})))

(s/fdef
 clear-object-store
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name])))
 :ret
 (s/keys))

(defn
 delete-database
 "Deletes a database.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :security-origin | Security origin.\n  :database-name   | Database name."
 ([]
  (delete-database
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [security-origin database-name]}]
  (delete-database
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [security-origin database-name]}]
  (cmd/command
   connection
   "IndexedDB"
   "deleteDatabase"
   params
   {:security-origin "securityOrigin", :database-name "databaseName"})))

(s/fdef
 delete-database
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name])))
 :ret
 (s/keys))

(defn
 delete-object-store-entries
 "Delete a range of entries from an object store\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :security-origin   | null\n  :database-name     | null\n  :object-store-name | null\n  :key-range         | Range of entry keys to delete"
 ([]
  (delete-object-store-entries
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [security-origin database-name object-store-name key-range]}]
  (delete-object-store-entries
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [security-origin database-name object-store-name key-range]}]
  (cmd/command
   connection
   "IndexedDB"
   "deleteObjectStoreEntries"
   params
   {:security-origin "securityOrigin",
    :database-name "databaseName",
    :object-store-name "objectStoreName",
    :key-range "keyRange"})))

(s/fdef
 delete-object-store-entries
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name
     ::key-range]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name
     ::key-range])))
 :ret
 (s/keys))

(defn
 disable
 "Disables events from backend."
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
   "IndexedDB"
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
 "Enables events from backend."
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
   "IndexedDB"
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
 request-data
 "Requests data from object store or index.\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :security-origin   | Security origin.\n  :database-name     | Database name.\n  :object-store-name | Object store name.\n  :index-name        | Index name, empty string for object store data requests.\n  :skip-count        | Number of records to skip.\n  :page-size         | Number of records to fetch.\n  :key-range         | Key range. (optional)\n\nReturn map keys:\n\n\n  Key                        | Description \n  ---------------------------|------------ \n  :object-store-data-entries | Array of object store data entries.\n  :has-more                  | If true, there are more entries to fetch in the given range."
 ([]
  (request-data
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [security-origin
     database-name
     object-store-name
     index-name
     skip-count
     page-size
     key-range]}]
  (request-data
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [security-origin
     database-name
     object-store-name
     index-name
     skip-count
     page-size
     key-range]}]
  (cmd/command
   connection
   "IndexedDB"
   "requestData"
   params
   {:security-origin "securityOrigin",
    :database-name "databaseName",
    :object-store-name "objectStoreName",
    :index-name "indexName",
    :skip-count "skipCount",
    :page-size "pageSize",
    :key-range "keyRange"})))

(s/fdef
 request-data
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name
     ::index-name
     ::skip-count
     ::page-size]
    :opt-un
    [::key-range]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name
     ::index-name
     ::skip-count
     ::page-size]
    :opt-un
    [::key-range])))
 :ret
 (s/keys
  :req-un
  [::object-store-data-entries
   ::has-more]))

(defn
 get-metadata
 "Gets metadata of an object store\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :security-origin   | Security origin.\n  :database-name     | Database name.\n  :object-store-name | Object store name.\n\nReturn map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :entries-count       | the entries count\n  :key-generator-value | the current value of key generator, to become the next inserted\nkey into the object store. Valid if objectStore.autoIncrement\nis true."
 ([]
  (get-metadata
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [security-origin database-name object-store-name]}]
  (get-metadata
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [security-origin database-name object-store-name]}]
  (cmd/command
   connection
   "IndexedDB"
   "getMetadata"
   params
   {:security-origin "securityOrigin",
    :database-name "databaseName",
    :object-store-name "objectStoreName"})))

(s/fdef
 get-metadata
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name
     ::object-store-name])))
 :ret
 (s/keys
  :req-un
  [::entries-count
   ::key-generator-value]))

(defn
 request-database
 "Requests database with given name in given frame.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :security-origin | Security origin.\n  :database-name   | Database name.\n\nReturn map keys:\n\n\n  Key                          | Description \n  -----------------------------|------------ \n  :database-with-object-stores | Database with an array of object stores."
 ([]
  (request-database
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [security-origin database-name]}]
  (request-database
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [security-origin database-name]}]
  (cmd/command
   connection
   "IndexedDB"
   "requestDatabase"
   params
   {:security-origin "securityOrigin", :database-name "databaseName"})))

(s/fdef
 request-database
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::security-origin
     ::database-name])))
 :ret
 (s/keys
  :req-un
  [::database-with-object-stores]))

(defn
 request-database-names
 "Requests database names for given security origin.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :security-origin | Security origin.\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :database-names | Database names for origin."
 ([]
  (request-database-names
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [security-origin]}]
  (request-database-names
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [security-origin]}]
  (cmd/command
   connection
   "IndexedDB"
   "requestDatabaseNames"
   params
   {:security-origin "securityOrigin"})))

(s/fdef
 request-database-names
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::security-origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::security-origin])))
 :ret
 (s/keys
  :req-un
  [::database-names]))
