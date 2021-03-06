(ns clj-chrome-devtools.commands.storage
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::storage-type
 #{"indexeddb" "file_systems" "service_workers" "local_storage"
   "appcache" "cache_storage" "cookies" "other" "all" "websql"
   "shader_cache"})

(s/def
 ::usage-for-type
 (s/keys
  :req-un
  [::storage-type
   ::usage]))
(defn
 clear-data-for-origin
 "Clears storage for origin.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :origin        | Security origin.\n  :storage-types | Comma separated list of StorageType to clear."
 ([]
  (clear-data-for-origin
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin storage-types]}]
  (clear-data-for-origin
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin storage-types]}]
  (cmd/command
   connection
   "Storage"
   "clearDataForOrigin"
   params
   {:origin "origin", :storage-types "storageTypes"})))

(s/fdef
 clear-data-for-origin
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin
     ::storage-types]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin
     ::storage-types])))
 :ret
 (s/keys))

(defn
 get-usage-and-quota
 "Returns usage and quota in bytes.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :origin | Security origin.\n\nReturn map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :usage           | Storage usage (bytes).\n  :quota           | Storage quota (bytes).\n  :usage-breakdown | Storage usage per type (bytes)."
 ([]
  (get-usage-and-quota
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (get-usage-and-quota
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (cmd/command
   connection
   "Storage"
   "getUsageAndQuota"
   params
   {:origin "origin"})))

(s/fdef
 get-usage-and-quota
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin])))
 :ret
 (s/keys
  :req-un
  [::usage
   ::quota
   ::usage-breakdown]))

(defn
 track-cache-storage-for-origin
 "Registers origin to be notified when an update occurs to its cache storage list.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :origin | Security origin."
 ([]
  (track-cache-storage-for-origin
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (track-cache-storage-for-origin
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (cmd/command
   connection
   "Storage"
   "trackCacheStorageForOrigin"
   params
   {:origin "origin"})))

(s/fdef
 track-cache-storage-for-origin
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin])))
 :ret
 (s/keys))

(defn
 track-indexed-db-for-origin
 "Registers origin to be notified when an update occurs to its IndexedDB.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :origin | Security origin."
 ([]
  (track-indexed-db-for-origin
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (track-indexed-db-for-origin
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (cmd/command
   connection
   "Storage"
   "trackIndexedDBForOrigin"
   params
   {:origin "origin"})))

(s/fdef
 track-indexed-db-for-origin
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin])))
 :ret
 (s/keys))

(defn
 untrack-cache-storage-for-origin
 "Unregisters origin from receiving notifications for cache storage.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :origin | Security origin."
 ([]
  (untrack-cache-storage-for-origin
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (untrack-cache-storage-for-origin
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (cmd/command
   connection
   "Storage"
   "untrackCacheStorageForOrigin"
   params
   {:origin "origin"})))

(s/fdef
 untrack-cache-storage-for-origin
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin])))
 :ret
 (s/keys))

(defn
 untrack-indexed-db-for-origin
 "Unregisters origin from receiving notifications for IndexedDB.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :origin | Security origin."
 ([]
  (untrack-indexed-db-for-origin
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (untrack-indexed-db-for-origin
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (cmd/command
   connection
   "Storage"
   "untrackIndexedDBForOrigin"
   params
   {:origin "origin"})))

(s/fdef
 untrack-indexed-db-for-origin
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin])))
 :ret
 (s/keys))
