(ns clj-chrome-devtools.commands.application-cache
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::application-cache-resource
 (s/keys
  :req-un
  [::url
   ::size
   ::type]))

(s/def
 ::application-cache
 (s/keys
  :req-un
  [::manifest-url
   ::size
   ::creation-time
   ::update-time
   ::resources]))

(s/def
 ::frame-with-manifest
 (s/keys
  :req-un
  [::frame-id
   ::manifest-url
   ::status]))
(defn
 enable
 "Enables application cache domain notifications."
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
   "ApplicationCache"
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
 get-application-cache-for-frame
 "Returns relevant application cache data for the document in given frame.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | Identifier of the frame containing document whose application cache is retrieved.\n\nReturn map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :application-cache | Relevant application cache data for the document in given frame."
 ([]
  (get-application-cache-for-frame
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id]}]
  (get-application-cache-for-frame
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id]}]
  (cmd/command
   connection
   "ApplicationCache"
   "getApplicationCacheForFrame"
   params
   {:frame-id "frameId"})))

(s/fdef
 get-application-cache-for-frame
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id])))
 :ret
 (s/keys
  :req-un
  [::application-cache]))

(defn
 get-frames-with-manifests
 "Returns array of frame identifiers with manifest urls for each frame containing a document\nassociated with some application cache.\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :frame-ids | Array of frame identifiers with manifest urls for each frame containing a document\nassociated with some application cache."
 ([]
  (get-frames-with-manifests
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-frames-with-manifests
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "ApplicationCache"
   "getFramesWithManifests"
   params
   {})))

(s/fdef
 get-frames-with-manifests
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
  [::frame-ids]))

(defn
 get-manifest-for-frame
 "Returns manifest URL for document in the given frame.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | Identifier of the frame containing document whose manifest is retrieved.\n\nReturn map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :manifest-url | Manifest URL for document in the given frame."
 ([]
  (get-manifest-for-frame
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id]}]
  (get-manifest-for-frame
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id]}]
  (cmd/command
   connection
   "ApplicationCache"
   "getManifestForFrame"
   params
   {:frame-id "frameId"})))

(s/fdef
 get-manifest-for-frame
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id])))
 :ret
 (s/keys
  :req-un
  [::manifest-url]))
