(ns clj-chrome-devtools.commands.dom-storage
  "Query and modify DOM storage."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::storage-id
 (s/keys
  :req-un
  [::security-origin
   ::is-local-storage]))

(s/def
 ::item
 (s/coll-of string?))
(defn
 clear
 "\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :storage-id | null"
 ([]
  (clear
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [storage-id]}]
  (clear
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [storage-id]}]
  (cmd/command
   connection
   "DOMStorage"
   "clear"
   params
   {:storage-id "storageId"})))

(s/fdef
 clear
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::storage-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::storage-id])))
 :ret
 (s/keys))

(defn
 disable
 "Disables storage tracking, prevents storage events from being sent to the client."
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
   "DOMStorage"
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
 "Enables storage tracking, storage events will now be delivered to the client."
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
   "DOMStorage"
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
 get-dom-storage-items
 "\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :storage-id | null\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :entries | null"
 ([]
  (get-dom-storage-items
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [storage-id]}]
  (get-dom-storage-items
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [storage-id]}]
  (cmd/command
   connection
   "DOMStorage"
   "getDOMStorageItems"
   params
   {:storage-id "storageId"})))

(s/fdef
 get-dom-storage-items
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::storage-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::storage-id])))
 :ret
 (s/keys
  :req-un
  [::entries]))

(defn
 remove-dom-storage-item
 "\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :storage-id | null\n  :key        | null"
 ([]
  (remove-dom-storage-item
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [storage-id key]}]
  (remove-dom-storage-item
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [storage-id key]}]
  (cmd/command
   connection
   "DOMStorage"
   "removeDOMStorageItem"
   params
   {:storage-id "storageId", :key "key"})))

(s/fdef
 remove-dom-storage-item
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::storage-id
     ::key]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::storage-id
     ::key])))
 :ret
 (s/keys))

(defn
 set-dom-storage-item
 "\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :storage-id | null\n  :key        | null\n  :value      | null"
 ([]
  (set-dom-storage-item
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [storage-id key value]}]
  (set-dom-storage-item
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [storage-id key value]}]
  (cmd/command
   connection
   "DOMStorage"
   "setDOMStorageItem"
   params
   {:storage-id "storageId", :key "key", :value "value"})))

(s/fdef
 set-dom-storage-item
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::storage-id
     ::key
     ::value]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::storage-id
     ::key
     ::value])))
 :ret
 (s/keys))
