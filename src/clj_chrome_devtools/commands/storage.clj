(ns clj-chrome-devtools.commands.storage
  (:require [clojure.spec.alpha :as s]))
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [origin storage-types]}]
  (clear-data-for-origin
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [origin storage-types]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Storage.clearDataForOrigin"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:origin "origin", :storage-types "storageTypes"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (get-usage-and-quota
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Storage.getUsageAndQuota"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:origin "origin"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (track-cache-storage-for-origin
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Storage.trackCacheStorageForOrigin"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:origin "origin"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (track-indexed-db-for-origin
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Storage.trackIndexedDBForOrigin"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:origin "origin"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (untrack-cache-storage-for-origin
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Storage.untrackCacheStorageForOrigin"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:origin "origin"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
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
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (untrack-indexed-db-for-origin
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Storage.untrackIndexedDBForOrigin"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:origin "origin"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::origin])))
 :ret
 (s/keys))
