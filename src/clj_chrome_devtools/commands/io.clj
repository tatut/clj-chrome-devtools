(ns clj-chrome-devtools.commands.io
  "Input/Output operations for streams produced by DevTools."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::stream-handle
 string?)
(defn
 close
 "Close the stream, discard any temporary backing storage.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :handle | Handle of the stream to close."
 ([]
  (close
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [handle]}]
  (close
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [handle]}]
  (cmd/command
   connection
   "IO"
   "close"
   params
   {:handle "handle"})))

(s/fdef
 close
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::handle]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::handle])))
 :ret
 (s/keys))

(defn
 read
 "Read a chunk of the stream\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :handle | Handle of the stream to read.\n  :offset | Seek to the specified offset before reading (if not specificed, proceed with offset\nfollowing the last read). Some types of streams may only support sequential reads. (optional)\n  :size   | Maximum number of bytes to read (left upon the agent discretion if not specified). (optional)\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :base64-encoded | Set if the data is base64-encoded (optional)\n  :data           | Data that were read.\n  :eof            | Set if the end-of-file condition occurred while reading."
 ([]
  (read
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [handle offset size]}]
  (read
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [handle offset size]}]
  (cmd/command
   connection
   "IO"
   "read"
   params
   {:handle "handle", :offset "offset", :size "size"})))

(s/fdef
 read
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::handle]
    :opt-un
    [::offset
     ::size]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::handle]
    :opt-un
    [::offset
     ::size])))
 :ret
 (s/keys
  :req-un
  [::data
   ::eof]
  :opt-un
  [::base64-encoded]))

(defn
 resolve-blob
 "Return UUID of Blob object specified by a remote object id.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :object-id | Object id of a Blob object wrapper.\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :uuid | UUID of the specified Blob."
 ([]
  (resolve-blob
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [object-id]}]
  (resolve-blob
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [object-id]}]
  (cmd/command
   connection
   "IO"
   "resolveBlob"
   params
   {:object-id "objectId"})))

(s/fdef
 resolve-blob
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::object-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::object-id])))
 :ret
 (s/keys
  :req-un
  [::uuid]))
