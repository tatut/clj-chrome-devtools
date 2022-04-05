(ns clj-chrome-devtools.commands.storage
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::storage-type
 #{"indexeddb" "file_systems" "service_workers" "local_storage"
   "appcache" "cache_storage" "cookies" "interest_groups" "other" "all"
   "websql" "shader_cache"})

(s/def
 ::usage-for-type
 (s/keys
  :req-un
  [::storage-type
   ::usage]))

(s/def
 ::trust-tokens
 (s/keys
  :req-un
  [::issuer-origin
   ::count]))

(s/def
 ::interest-group-access-type
 #{"update" "win" "leave" "join" "bid"})

(s/def
 ::interest-group-ad
 (s/keys
  :req-un
  [::render-url]
  :opt-un
  [::metadata]))

(s/def
 ::interest-group-details
 (s/keys
  :req-un
  [::owner-origin
   ::name
   ::expiration-time
   ::joining-origin
   ::trusted-bidding-signals-keys
   ::ads
   ::ad-components]
  :opt-un
  [::bidding-url
   ::bidding-wasm-helper-url
   ::update-url
   ::trusted-bidding-signals-url
   ::user-bidding-signals]))
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
 get-cookies
 "Returns all browser cookies.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :browser-context-id | Browser context to use when called on the browser endpoint. (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Array of cookie objects."
 ([]
  (get-cookies
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [browser-context-id]}]
  (get-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [browser-context-id]}]
  (cmd/command
   connection
   "Storage"
   "getCookies"
   params
   {:browser-context-id "browserContextId"})))

(s/fdef
 get-cookies
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::browser-context-id])))
 :ret
 (s/keys
  :req-un
  [::cookies]))

(defn
 set-cookies
 "Sets given cookies.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :cookies            | Cookies to be set.\n  :browser-context-id | Browser context to use when called on the browser endpoint. (optional)"
 ([]
  (set-cookies
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cookies browser-context-id]}]
  (set-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [cookies browser-context-id]}]
  (cmd/command
   connection
   "Storage"
   "setCookies"
   params
   {:cookies "cookies", :browser-context-id "browserContextId"})))

(s/fdef
 set-cookies
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cookies]
    :opt-un
    [::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cookies]
    :opt-un
    [::browser-context-id])))
 :ret
 (s/keys))

(defn
 clear-cookies
 "Clears cookies.\n\nParameters map keys:\n\n\n  Key                 | Description \n  --------------------|------------ \n  :browser-context-id | Browser context to use when called on the browser endpoint. (optional)"
 ([]
  (clear-cookies
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [browser-context-id]}]
  (clear-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [browser-context-id]}]
  (cmd/command
   connection
   "Storage"
   "clearCookies"
   params
   {:browser-context-id "browserContextId"})))

(s/fdef
 clear-cookies
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::browser-context-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::browser-context-id])))
 :ret
 (s/keys))

(defn
 get-usage-and-quota
 "Returns usage and quota in bytes.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :origin | Security origin.\n\nReturn map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :usage           | Storage usage (bytes).\n  :quota           | Storage quota (bytes).\n  :override-active | Whether or not the origin has an active storage quota override\n  :usage-breakdown | Storage usage per type (bytes)."
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
   ::override-active
   ::usage-breakdown]))

(defn
 override-quota-for-origin
 "Override quota for the specified origin\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :origin     | Security origin.\n  :quota-size | The quota size (in bytes) to override the original quota with.\nIf this is called multiple times, the overridden quota will be equal to\nthe quotaSize provided in the final call. If this is called without\nspecifying a quotaSize, the quota will be reset to the default value for\nthe specified origin. If this is called multiple times with different\norigins, the override will be maintained for each origin until it is\ndisabled (called without a quotaSize). (optional)"
 ([]
  (override-quota-for-origin
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin quota-size]}]
  (override-quota-for-origin
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin quota-size]}]
  (cmd/command
   connection
   "Storage"
   "overrideQuotaForOrigin"
   params
   {:origin "origin", :quota-size "quotaSize"})))

(s/fdef
 override-quota-for-origin
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin]
    :opt-un
    [::quota-size]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin]
    :opt-un
    [::quota-size])))
 :ret
 (s/keys))

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

(defn
 get-trust-tokens
 "Returns the number of stored Trust Tokens per issuer for the\ncurrent browsing context.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :tokens | null"
 ([]
  (get-trust-tokens
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-trust-tokens
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Storage"
   "getTrustTokens"
   params
   {})))

(s/fdef
 get-trust-tokens
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
  [::tokens]))

(defn
 clear-trust-tokens
 "Removes all Trust Tokens issued by the provided issuerOrigin.\nLeaves other stored data, including the issuer's Redemption Records, intact.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :issuer-origin | null\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :did-delete-tokens | True if any tokens were deleted, false otherwise."
 ([]
  (clear-trust-tokens
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [issuer-origin]}]
  (clear-trust-tokens
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [issuer-origin]}]
  (cmd/command
   connection
   "Storage"
   "clearTrustTokens"
   params
   {:issuer-origin "issuerOrigin"})))

(s/fdef
 clear-trust-tokens
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::issuer-origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::issuer-origin])))
 :ret
 (s/keys
  :req-un
  [::did-delete-tokens]))

(defn
 get-interest-group-details
 "Gets details for a named interest group.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :owner-origin | null\n  :name         | null\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :details | null"
 ([]
  (get-interest-group-details
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [owner-origin name]}]
  (get-interest-group-details
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [owner-origin name]}]
  (cmd/command
   connection
   "Storage"
   "getInterestGroupDetails"
   params
   {:owner-origin "ownerOrigin", :name "name"})))

(s/fdef
 get-interest-group-details
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::owner-origin
     ::name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::owner-origin
     ::name])))
 :ret
 (s/keys
  :req-un
  [::details]))

(defn
 set-interest-group-tracking
 "Enables/Disables issuing of interestGroupAccessed events.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :enable | null"
 ([]
  (set-interest-group-tracking
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enable]}]
  (set-interest-group-tracking
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enable]}]
  (cmd/command
   connection
   "Storage"
   "setInterestGroupTracking"
   params
   {:enable "enable"})))

(s/fdef
 set-interest-group-tracking
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enable]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::enable])))
 :ret
 (s/keys))
