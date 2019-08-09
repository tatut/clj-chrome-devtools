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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.clearObjectStore"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:security-origin "securityOrigin",
      :database-name "databaseName",
      :object-store-name "objectStoreName"})]
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.deleteDatabase"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:security-origin "securityOrigin",
      :database-name "databaseName"})]
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.deleteObjectStoreEntries"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:security-origin "securityOrigin",
      :database-name "databaseName",
      :object-store-name "objectStoreName",
      :key-range "keyRange"})]
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.disable"
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.enable"
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.requestData"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:security-origin "securityOrigin",
      :database-name "databaseName",
      :object-store-name "objectStoreName",
      :index-name "indexName",
      :skip-count "skipCount",
      :page-size "pageSize",
      :key-range "keyRange"})]
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.getMetadata"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:security-origin "securityOrigin",
      :database-name "databaseName",
      :object-store-name "objectStoreName"})]
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.requestDatabase"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:security-origin "securityOrigin",
      :database-name "databaseName"})]
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "IndexedDB.requestDatabaseNames"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:security-origin "securityOrigin"})]
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
