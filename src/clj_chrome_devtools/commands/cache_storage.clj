(ns clj-chrome-devtools.commands.cache-storage
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(s/def
 ::cache-id
 string?)

(s/def
 ::cached-response-type
 #{"opaqueResponse" "error" "cors" "default" "opaqueRedirect" "basic"})

(s/def
 ::data-entry
 (s/keys
  :req-un
  [::request-url
   ::request-method
   ::request-headers
   ::response-time
   ::response-status
   ::response-status-text
   ::response-type
   ::response-headers]))

(s/def
 ::cache
 (s/keys
  :req-un
  [::cache-id
   ::security-origin
   ::cache-name]))

(s/def
 ::header
 (s/keys
  :req-un
  [::name
   ::value]))

(s/def
 ::cached-response
 (s/keys
  :req-un
  [::body]))
(defn
 delete-cache
 "Deletes a cache.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :cache-id | Id of cache for deletion."
 ([]
  (delete-cache
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cache-id]}]
  (delete-cache
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [cache-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "CacheStorage.deleteCache"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:cache-id "cacheId"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 delete-cache
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cache-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cache-id])))
 :ret
 (s/keys))

(defn
 delete-entry
 "Deletes a cache entry.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :cache-id | Id of cache where the entry will be deleted.\n  :request  | URL spec of the request."
 ([]
  (delete-entry
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cache-id request]}]
  (delete-entry
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [cache-id request]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "CacheStorage.deleteEntry"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:cache-id "cacheId", :request "request"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 delete-entry
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cache-id
     ::request]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cache-id
     ::request])))
 :ret
 (s/keys))

(defn
 request-cache-names
 "Requests cache names.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :security-origin | Security origin.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :caches | Caches for the security origin."
 ([]
  (request-cache-names
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [security-origin]}]
  (request-cache-names
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [security-origin]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "CacheStorage.requestCacheNames"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:security-origin "securityOrigin"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 request-cache-names
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
  [::caches]))

(defn
 request-cached-response
 "Fetches cache entry.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :cache-id        | Id of cache that contains the entry.\n  :request-url     | URL spec of the request.\n  :request-headers | headers of the request.\n\nReturn map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :response | Response read from the cache."
 ([]
  (request-cached-response
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cache-id request-url request-headers]}]
  (request-cached-response
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [cache-id request-url request-headers]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "CacheStorage.requestCachedResponse"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:cache-id "cacheId",
      :request-url "requestURL",
      :request-headers "requestHeaders"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 request-cached-response
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cache-id
     ::request-url
     ::request-headers]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cache-id
     ::request-url
     ::request-headers])))
 :ret
 (s/keys
  :req-un
  [::response]))

(defn
 request-entries
 "Requests data from cache.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :cache-id    | ID of cache to get entries from.\n  :skip-count  | Number of records to skip.\n  :page-size   | Number of records to fetch.\n  :path-filter | If present, only return the entries containing this substring in the path (optional)\n\nReturn map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :cache-data-entries | Array of object store data entries.\n  :return-count       | Count of returned entries from this storage. If pathFilter is empty, it\nis the count of all entries from this storage."
 ([]
  (request-entries
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cache-id skip-count page-size path-filter]}]
  (request-entries
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [cache-id skip-count page-size path-filter]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "CacheStorage.requestEntries"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:cache-id "cacheId",
      :skip-count "skipCount",
      :page-size "pageSize",
      :path-filter "pathFilter"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

(s/fdef
 request-entries
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cache-id
     ::skip-count
     ::page-size]
    :opt-un
    [::path-filter]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cache-id
     ::skip-count
     ::page-size]
    :opt-un
    [::path-filter])))
 :ret
 (s/keys
  :req-un
  [::cache-data-entries
   ::return-count]))
