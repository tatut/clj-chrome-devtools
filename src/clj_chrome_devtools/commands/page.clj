(ns clj-chrome-devtools.commands.page
  "Actions and events related to the inspected page belong to the page domain."
  (:require [clojure.spec.alpha :as s]))
(s/def
 ::frame-id
 string?)

(s/def
 ::frame
 (s/keys
  :req-un
  [::id
   ::loader-id
   ::url
   ::security-origin
   ::mime-type]
  :opt-un
  [::parent-id
   ::name
   ::url-fragment
   ::unreachable-url]))

(s/def
 ::frame-resource
 (s/keys
  :req-un
  [::url
   ::type
   ::mime-type]
  :opt-un
  [::last-modified
   ::content-size
   ::failed
   ::canceled]))

(s/def
 ::frame-resource-tree
 (s/keys
  :req-un
  [::frame
   ::resources]
  :opt-un
  [::child-frames]))

(s/def
 ::frame-tree
 (s/keys
  :req-un
  [::frame]
  :opt-un
  [::child-frames]))

(s/def
 ::script-identifier
 string?)

(s/def
 ::transition-type
 #{"typed" "form_submit" "auto_subframe" "address_bar" "auto_toplevel"
   "manual_subframe" "keyword_generated" "keyword" "link" "reload"
   "other" "auto_bookmark" "generated"})

(s/def
 ::navigation-entry
 (s/keys
  :req-un
  [::id
   ::url
   ::user-typed-url
   ::title
   ::transition-type]))

(s/def
 ::screencast-frame-metadata
 (s/keys
  :req-un
  [::offset-top
   ::page-scale-factor
   ::device-width
   ::device-height
   ::scroll-offset-x
   ::scroll-offset-y]
  :opt-un
  [::timestamp]))

(s/def
 ::dialog-type
 #{"alert" "confirm" "prompt" "beforeunload"})

(s/def
 ::app-manifest-error
 (s/keys
  :req-un
  [::message
   ::critical
   ::line
   ::column]))

(s/def
 ::layout-viewport
 (s/keys
  :req-un
  [::page-x
   ::page-y
   ::client-width
   ::client-height]))

(s/def
 ::visual-viewport
 (s/keys
  :req-un
  [::offset-x
   ::offset-y
   ::page-x
   ::page-y
   ::client-width
   ::client-height
   ::scale]
  :opt-un
  [::zoom]))

(s/def
 ::viewport
 (s/keys
  :req-un
  [::x
   ::y
   ::width
   ::height
   ::scale]))

(s/def
 ::font-families
 (s/keys
  :opt-un
  [::standard
   ::fixed
   ::serif
   ::sans-serif
   ::cursive
   ::fantasy
   ::pictograph]))

(s/def
 ::font-sizes
 (s/keys
  :opt-un
  [::standard
   ::fixed]))

(s/def
 ::client-navigation-reason
 #{"scriptInitiated" "metaTagRefresh" "reload" "formSubmissionPost"
   "pageBlockInterstitial" "formSubmissionGet" "httpHeaderRefresh"})
(defn
 add-script-to-evaluate-on-load
 "Deprecated, please use addScriptToEvaluateOnNewDocument instead.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :script-source | null\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | Identifier of the added script."
 ([]
  (add-script-to-evaluate-on-load
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [script-source]}]
  (add-script-to-evaluate-on-load
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [script-source]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.addScriptToEvaluateOnLoad"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:script-source "scriptSource"})]
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
 add-script-to-evaluate-on-load
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::script-source]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::script-source])))
 :ret
 (s/keys
  :req-un
  [::identifier]))

(defn
 add-script-to-evaluate-on-new-document
 "Evaluates given script in every frame upon creation (before loading frame's scripts).\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :source     | null\n  :world-name | If specified, creates an isolated world with the given name and evaluates given script in it.\nThis world name will be used as the ExecutionContextDescription::name when the corresponding\nevent is emitted. (optional)\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | Identifier of the added script."
 ([]
  (add-script-to-evaluate-on-new-document
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [source world-name]}]
  (add-script-to-evaluate-on-new-document
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [source world-name]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.addScriptToEvaluateOnNewDocument"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:source "source", :world-name "worldName"})]
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
 add-script-to-evaluate-on-new-document
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::source]
    :opt-un
    [::world-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::source]
    :opt-un
    [::world-name])))
 :ret
 (s/keys
  :req-un
  [::identifier]))

(defn
 bring-to-front
 "Brings page to front (activates tab)."
 ([]
  (bring-to-front
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (bring-to-front
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.bringToFront"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 bring-to-front
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 capture-screenshot
 "Capture page screenshot.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :format       | Image compression format (defaults to png). (optional)\n  :quality      | Compression quality from range [0..100] (jpeg only). (optional)\n  :clip         | Capture the screenshot of a given region only. (optional)\n  :from-surface | Capture the screenshot from the surface, rather than the view. Defaults to true. (optional)\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :data | Base64-encoded image data."
 ([]
  (capture-screenshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [format quality clip from-surface]}]
  (capture-screenshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [format quality clip from-surface]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.captureScreenshot"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:format "format",
      :quality "quality",
      :clip "clip",
      :from-surface "fromSurface"})]
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
 capture-screenshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::format
     ::quality
     ::clip
     ::from-surface]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [::format
     ::quality
     ::clip
     ::from-surface])))
 :ret
 (s/keys
  :req-un
  [::data]))

(defn
 capture-snapshot
 "Returns a snapshot of the page as a string. For MHTML format, the serialization includes\niframes, shadow DOM, external resources, and element-inline styles.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :format | Format (defaults to mhtml). (optional)\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :data | Serialized page data."
 ([]
  (capture-snapshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [format]}]
  (capture-snapshot
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [format]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.captureSnapshot"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:format "format"})]
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
 capture-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::format]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [::format])))
 :ret
 (s/keys
  :req-un
  [::data]))

(defn
 clear-device-metrics-override
 "Clears the overriden device metrics."
 ([]
  (clear-device-metrics-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-device-metrics-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.clearDeviceMetricsOverride"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 clear-device-metrics-override
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 clear-device-orientation-override
 "Clears the overridden Device Orientation."
 ([]
  (clear-device-orientation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-device-orientation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.clearDeviceOrientationOverride"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 clear-device-orientation-override
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 clear-geolocation-override
 "Clears the overriden Geolocation Position and Error."
 ([]
  (clear-geolocation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-geolocation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.clearGeolocationOverride"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 clear-geolocation-override
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 create-isolated-world
 "Creates an isolated world for the given frame.\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :frame-id              | Id of the frame in which the isolated world should be created.\n  :world-name            | An optional name which is reported in the Execution Context. (optional)\n  :grant-univeral-access | Whether or not universal access should be granted to the isolated world. This is a powerful\noption, use with caution. (optional)\n\nReturn map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :execution-context-id | Execution context of the isolated world."
 ([]
  (create-isolated-world
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id world-name grant-univeral-access]}]
  (create-isolated-world
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [frame-id world-name grant-univeral-access]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.createIsolatedWorld"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:frame-id "frameId",
      :world-name "worldName",
      :grant-univeral-access "grantUniveralAccess"})]
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
 create-isolated-world
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id]
    :opt-un
    [::world-name
     ::grant-univeral-access]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id]
    :opt-un
    [::world-name
     ::grant-univeral-access])))
 :ret
 (s/keys
  :req-un
  [::execution-context-id]))

(defn
 delete-cookie
 "Deletes browser cookie with given name, domain and path.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :cookie-name | Name of the cookie to remove.\n  :url         | URL to match cooke domain and path."
 ([]
  (delete-cookie
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [cookie-name url]}]
  (delete-cookie
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [cookie-name url]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.deleteCookie"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:cookie-name "cookieName", :url "url"})]
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
 delete-cookie
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cookie-name
     ::url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::cookie-name
     ::url])))
 :ret
 (s/keys))

(defn
 disable
 "Disables page domain notifications."
 ([]
  (disable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (disable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.disable"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 enable
 "Enables page domain notifications."
 ([]
  (enable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (enable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.enable"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 get-app-manifest
 "\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :url    | Manifest location.\n  :errors | null\n  :data   | Manifest content. (optional)"
 ([]
  (get-app-manifest
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-app-manifest
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getAppManifest"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 get-app-manifest
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::url
   ::errors]
  :opt-un
  [::data]))

(defn
 get-installability-errors
 "\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :errors | null"
 ([]
  (get-installability-errors
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-installability-errors
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getInstallabilityErrors"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 get-installability-errors
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::errors]))

(defn
 get-cookies
 "Returns all browser cookies. Depending on the backend support, will return detailed cookie\ninformation in the `cookies` field.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Array of cookie objects."
 ([]
  (get-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getCookies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 get-cookies
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::cookies]))

(defn
 get-frame-tree
 "Returns present frame tree structure.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :frame-tree | Present frame tree structure."
 ([]
  (get-frame-tree
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-frame-tree
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getFrameTree"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 get-frame-tree
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::frame-tree]))

(defn
 get-layout-metrics
 "Returns metrics relating to the layouting of the page, such as viewport bounds/scale.\n\nReturn map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :layout-viewport | Metrics relating to the layout viewport.\n  :visual-viewport | Metrics relating to the visual viewport.\n  :content-size    | Size of scrollable area."
 ([]
  (get-layout-metrics
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-layout-metrics
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getLayoutMetrics"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 get-layout-metrics
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::layout-viewport
   ::visual-viewport
   ::content-size]))

(defn
 get-navigation-history
 "Returns navigation history for the current page.\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :current-index | Index of the current navigation history entry.\n  :entries       | Array of navigation history entries."
 ([]
  (get-navigation-history
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-navigation-history
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getNavigationHistory"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 get-navigation-history
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::current-index
   ::entries]))

(defn
 reset-navigation-history
 "Resets navigation history for the current page."
 ([]
  (reset-navigation-history
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (reset-navigation-history
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.resetNavigationHistory"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 reset-navigation-history
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 get-resource-content
 "Returns content of the given resource.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | Frame id to get resource for.\n  :url      | URL of the resource to get content for.\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :content        | Resource content.\n  :base64-encoded | True, if content was served as base64."
 ([]
  (get-resource-content
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id url]}]
  (get-resource-content
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id url]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getResourceContent"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:frame-id "frameId", :url "url"})]
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
 get-resource-content
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id
     ::url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id
     ::url])))
 :ret
 (s/keys
  :req-un
  [::content
   ::base64-encoded]))

(defn
 get-resource-tree
 "Returns present frame / resource tree structure.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :frame-tree | Present frame / resource tree structure."
 ([]
  (get-resource-tree
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-resource-tree
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.getResourceTree"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 get-resource-tree
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::frame-tree]))

(defn
 handle-java-script-dialog
 "Accepts or dismisses a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload).\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :accept      | Whether to accept or dismiss the dialog.\n  :prompt-text | The text to enter into the dialog prompt before accepting. Used only if this is a prompt\ndialog. (optional)"
 ([]
  (handle-java-script-dialog
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [accept prompt-text]}]
  (handle-java-script-dialog
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [accept prompt-text]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.handleJavaScriptDialog"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:accept "accept", :prompt-text "promptText"})]
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
 handle-java-script-dialog
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::accept]
    :opt-un
    [::prompt-text]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::accept]
    :opt-un
    [::prompt-text])))
 :ret
 (s/keys))

(defn
 navigate
 "Navigates current page to the given URL.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :url             | URL to navigate the page to.\n  :referrer        | Referrer URL. (optional)\n  :transition-type | Intended transition type. (optional)\n  :frame-id        | Frame id to navigate, if not specified navigates the top frame. (optional)\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :frame-id   | Frame id that has navigated (or failed to navigate)\n  :loader-id  | Loader identifier. (optional)\n  :error-text | User friendly error message, present if and only if navigation has failed. (optional)"
 ([]
  (navigate
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [url referrer transition-type frame-id]}]
  (navigate
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [url referrer transition-type frame-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.navigate"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:url "url",
      :referrer "referrer",
      :transition-type "transitionType",
      :frame-id "frameId"})]
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
 navigate
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::url]
    :opt-un
    [::referrer
     ::transition-type
     ::frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::url]
    :opt-un
    [::referrer
     ::transition-type
     ::frame-id])))
 :ret
 (s/keys
  :req-un
  [::frame-id]
  :opt-un
  [::loader-id
   ::error-text]))

(defn
 navigate-to-history-entry
 "Navigates current page to the given history entry.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :entry-id | Unique id of the entry to navigate to."
 ([]
  (navigate-to-history-entry
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [entry-id]}]
  (navigate-to-history-entry
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [entry-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.navigateToHistoryEntry"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:entry-id "entryId"})]
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
 navigate-to-history-entry
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::entry-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::entry-id])))
 :ret
 (s/keys))

(defn
 print-to-pdf
 "Print page as PDF.\n\nParameters map keys:\n\n\n  Key                         | Description \n  ----------------------------|------------ \n  :landscape                  | Paper orientation. Defaults to false. (optional)\n  :display-header-footer      | Display header and footer. Defaults to false. (optional)\n  :print-background           | Print background graphics. Defaults to false. (optional)\n  :scale                      | Scale of the webpage rendering. Defaults to 1. (optional)\n  :paper-width                | Paper width in inches. Defaults to 8.5 inches. (optional)\n  :paper-height               | Paper height in inches. Defaults to 11 inches. (optional)\n  :margin-top                 | Top margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :margin-bottom              | Bottom margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :margin-left                | Left margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :margin-right               | Right margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :page-ranges                | Paper ranges to print, e.g., '1-5, 8, 11-13'. Defaults to the empty string, which means\nprint all pages. (optional)\n  :ignore-invalid-page-ranges | Whether to silently ignore invalid but successfully parsed page ranges, such as '3-2'.\nDefaults to false. (optional)\n  :header-template            | HTML template for the print header. Should be valid HTML markup with following\nclasses used to inject printing values into them:\n- `date`: formatted print date\n- `title`: document title\n- `url`: document location\n- `pageNumber`: current page number\n- `totalPages`: total pages in the document\n\nFor example, `<span class=title></span>` would generate span containing the title. (optional)\n  :footer-template            | HTML template for the print footer. Should use the same format as the `headerTemplate`. (optional)\n  :prefer-css-page-size       | Whether or not to prefer page size as defined by css. Defaults to false,\nin which case the content will be scaled to fit the paper size. (optional)\n  :transfer-mode              | return as stream (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :data   | Base64-encoded pdf data. Empty if |returnAsStream| is specified.\n  :stream | A handle of the stream that holds resulting PDF data. (optional)"
 ([]
  (print-to-pdf
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [landscape
     display-header-footer
     print-background
     scale
     paper-width
     paper-height
     margin-top
     margin-bottom
     margin-left
     margin-right
     page-ranges
     ignore-invalid-page-ranges
     header-template
     footer-template
     prefer-css-page-size
     transfer-mode]}]
  (print-to-pdf
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [landscape
     display-header-footer
     print-background
     scale
     paper-width
     paper-height
     margin-top
     margin-bottom
     margin-left
     margin-right
     page-ranges
     ignore-invalid-page-ranges
     header-template
     footer-template
     prefer-css-page-size
     transfer-mode]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.printToPDF"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:page-ranges "pageRanges",
      :ignore-invalid-page-ranges "ignoreInvalidPageRanges",
      :scale "scale",
      :header-template "headerTemplate",
      :display-header-footer "displayHeaderFooter",
      :margin-left "marginLeft",
      :margin-top "marginTop",
      :transfer-mode "transferMode",
      :prefer-css-page-size "preferCSSPageSize",
      :landscape "landscape",
      :footer-template "footerTemplate",
      :print-background "printBackground",
      :paper-width "paperWidth",
      :margin-right "marginRight",
      :margin-bottom "marginBottom",
      :paper-height "paperHeight"})]
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
 print-to-pdf
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::landscape
     ::display-header-footer
     ::print-background
     ::scale
     ::paper-width
     ::paper-height
     ::margin-top
     ::margin-bottom
     ::margin-left
     ::margin-right
     ::page-ranges
     ::ignore-invalid-page-ranges
     ::header-template
     ::footer-template
     ::prefer-css-page-size
     ::transfer-mode]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [::landscape
     ::display-header-footer
     ::print-background
     ::scale
     ::paper-width
     ::paper-height
     ::margin-top
     ::margin-bottom
     ::margin-left
     ::margin-right
     ::page-ranges
     ::ignore-invalid-page-ranges
     ::header-template
     ::footer-template
     ::prefer-css-page-size
     ::transfer-mode])))
 :ret
 (s/keys
  :req-un
  [::data]
  :opt-un
  [::stream]))

(defn
 reload
 "Reloads given page optionally ignoring the cache.\n\nParameters map keys:\n\n\n  Key                         | Description \n  ----------------------------|------------ \n  :ignore-cache               | If true, browser cache is ignored (as if the user pressed Shift+refresh). (optional)\n  :script-to-evaluate-on-load | If set, the script will be injected into all frames of the inspected page after reload.\nArgument will be ignored if reloading dataURL origin. (optional)"
 ([]
  (reload
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [ignore-cache script-to-evaluate-on-load]}]
  (reload
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [ignore-cache script-to-evaluate-on-load]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.reload"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:ignore-cache "ignoreCache",
      :script-to-evaluate-on-load "scriptToEvaluateOnLoad"})]
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
 reload
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::ignore-cache
     ::script-to-evaluate-on-load]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [::ignore-cache
     ::script-to-evaluate-on-load])))
 :ret
 (s/keys))

(defn
 remove-script-to-evaluate-on-load
 "Deprecated, please use removeScriptToEvaluateOnNewDocument instead.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | null"
 ([]
  (remove-script-to-evaluate-on-load
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [identifier]}]
  (remove-script-to-evaluate-on-load
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [identifier]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.removeScriptToEvaluateOnLoad"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:identifier "identifier"})]
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
 remove-script-to-evaluate-on-load
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::identifier]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::identifier])))
 :ret
 (s/keys))

(defn
 remove-script-to-evaluate-on-new-document
 "Removes given script from the list.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | null"
 ([]
  (remove-script-to-evaluate-on-new-document
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [identifier]}]
  (remove-script-to-evaluate-on-new-document
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [identifier]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.removeScriptToEvaluateOnNewDocument"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:identifier "identifier"})]
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
 remove-script-to-evaluate-on-new-document
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::identifier]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::identifier])))
 :ret
 (s/keys))

(defn
 screencast-frame-ack
 "Acknowledges that a screencast frame has been received by the frontend.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :session-id | Frame number."
 ([]
  (screencast-frame-ack
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [session-id]}]
  (screencast-frame-ack
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [session-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.screencastFrameAck"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:session-id "sessionId"})]
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
 screencast-frame-ack
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::session-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::session-id])))
 :ret
 (s/keys))

(defn
 search-in-resource
 "Searches for given string in resource content.\n\nParameters map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :frame-id       | Frame id for resource to search in.\n  :url            | URL of the resource to search in.\n  :query          | String to search for.\n  :case-sensitive | If true, search is case sensitive. (optional)\n  :is-regex       | If true, treats string parameter as regex. (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | List of search matches."
 ([]
  (search-in-resource
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id url query case-sensitive is-regex]}]
  (search-in-resource
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [frame-id url query case-sensitive is-regex]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.searchInResource"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:frame-id "frameId",
      :url "url",
      :query "query",
      :case-sensitive "caseSensitive",
      :is-regex "isRegex"})]
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
 search-in-resource
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id
     ::url
     ::query]
    :opt-un
    [::case-sensitive
     ::is-regex]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id
     ::url
     ::query]
    :opt-un
    [::case-sensitive
     ::is-regex])))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 set-ad-blocking-enabled
 "Enable Chrome's experimental ad filter on all sites.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | Whether to block ads."
 ([]
  (set-ad-blocking-enabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-ad-blocking-enabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setAdBlockingEnabled"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:enabled "enabled"})]
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
 set-ad-blocking-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::enabled])))
 :ret
 (s/keys))

(defn
 set-bypass-csp
 "Enable page Content Security Policy by-passing.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | Whether to bypass page CSP."
 ([]
  (set-bypass-csp
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-bypass-csp
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setBypassCSP"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:enabled "enabled"})]
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
 set-bypass-csp
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::enabled])))
 :ret
 (s/keys))

(defn
 set-device-metrics-override
 "Overrides the values of device screen dimensions (window.screen.width, window.screen.height,\nwindow.innerWidth, window.innerHeight, and \"device-width\"/\"device-height\"-related CSS media\nquery results).\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :width                 | Overriding width value in pixels (minimum 0, maximum 10000000). 0 disables the override.\n  :height                | Overriding height value in pixels (minimum 0, maximum 10000000). 0 disables the override.\n  :device-scale-factor   | Overriding device scale factor value. 0 disables the override.\n  :mobile                | Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text\nautosizing and more.\n  :scale                 | Scale to apply to resulting view image. (optional)\n  :screen-width          | Overriding screen width value in pixels (minimum 0, maximum 10000000). (optional)\n  :screen-height         | Overriding screen height value in pixels (minimum 0, maximum 10000000). (optional)\n  :position-x            | Overriding view X position on screen in pixels (minimum 0, maximum 10000000). (optional)\n  :position-y            | Overriding view Y position on screen in pixels (minimum 0, maximum 10000000). (optional)\n  :dont-set-visible-size | Do not set visible view size, rely upon explicit setVisibleSize call. (optional)\n  :screen-orientation    | Screen orientation override. (optional)\n  :viewport              | The viewport dimensions and scale. If not set, the override is cleared. (optional)"
 ([]
  (set-device-metrics-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [width
     height
     device-scale-factor
     mobile
     scale
     screen-width
     screen-height
     position-x
     position-y
     dont-set-visible-size
     screen-orientation
     viewport]}]
  (set-device-metrics-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [width
     height
     device-scale-factor
     mobile
     scale
     screen-width
     screen-height
     position-x
     position-y
     dont-set-visible-size
     screen-orientation
     viewport]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setDeviceMetricsOverride"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:dont-set-visible-size "dontSetVisibleSize",
      :device-scale-factor "deviceScaleFactor",
      :screen-orientation "screenOrientation",
      :scale "scale",
      :width "width",
      :position-y "positionY",
      :position-x "positionX",
      :screen-height "screenHeight",
      :mobile "mobile",
      :viewport "viewport",
      :height "height",
      :screen-width "screenWidth"})]
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
 set-device-metrics-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/width
     :clj-chrome-devtools.impl.define/height
     :clj-chrome-devtools.impl.define/device-scale-factor
     :clj-chrome-devtools.impl.define/mobile]
    :opt-un
    [:clj-chrome-devtools.impl.define/scale
     :clj-chrome-devtools.impl.define/screen-width
     :clj-chrome-devtools.impl.define/screen-height
     :clj-chrome-devtools.impl.define/position-x
     :clj-chrome-devtools.impl.define/position-y
     :clj-chrome-devtools.impl.define/dont-set-visible-size
     :clj-chrome-devtools.impl.define/screen-orientation
     :clj-chrome-devtools.impl.define/viewport]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/width
     :clj-chrome-devtools.impl.define/height
     :clj-chrome-devtools.impl.define/device-scale-factor
     :clj-chrome-devtools.impl.define/mobile]
    :opt-un
    [:clj-chrome-devtools.impl.define/scale
     :clj-chrome-devtools.impl.define/screen-width
     :clj-chrome-devtools.impl.define/screen-height
     :clj-chrome-devtools.impl.define/position-x
     :clj-chrome-devtools.impl.define/position-y
     :clj-chrome-devtools.impl.define/dont-set-visible-size
     :clj-chrome-devtools.impl.define/screen-orientation
     :clj-chrome-devtools.impl.define/viewport])))
 :ret
 (s/keys))

(defn
 set-device-orientation-override
 "Overrides the Device Orientation.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :alpha | Mock alpha\n  :beta  | Mock beta\n  :gamma | Mock gamma"
 ([]
  (set-device-orientation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [alpha beta gamma]}]
  (set-device-orientation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [alpha beta gamma]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setDeviceOrientationOverride"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:alpha "alpha", :beta "beta", :gamma "gamma"})]
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
 set-device-orientation-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/alpha
     :clj-chrome-devtools.impl.define/beta
     :clj-chrome-devtools.impl.define/gamma]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/alpha
     :clj-chrome-devtools.impl.define/beta
     :clj-chrome-devtools.impl.define/gamma])))
 :ret
 (s/keys))

(defn
 set-font-families
 "Set generic font families.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :font-families | Specifies font families to set. If a font family is not specified, it won't be changed."
 ([]
  (set-font-families
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [font-families]}]
  (set-font-families
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [font-families]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setFontFamilies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:font-families "fontFamilies"})]
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
 set-font-families
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/font-families]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/font-families])))
 :ret
 (s/keys))

(defn
 set-font-sizes
 "Set default font sizes.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :font-sizes | Specifies font sizes to set. If a font size is not specified, it won't be changed."
 ([]
  (set-font-sizes
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [font-sizes]}]
  (set-font-sizes
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [font-sizes]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setFontSizes"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:font-sizes "fontSizes"})]
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
 set-font-sizes
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/font-sizes]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/font-sizes])))
 :ret
 (s/keys))

(defn
 set-document-content
 "Sets given markup as the document's HTML.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | Frame id to set HTML for.\n  :html     | HTML content to set."
 ([]
  (set-document-content
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id html]}]
  (set-document-content
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id html]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setDocumentContent"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:frame-id "frameId", :html "html"})]
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
 set-document-content
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/frame-id
     :clj-chrome-devtools.impl.define/html]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/frame-id
     :clj-chrome-devtools.impl.define/html])))
 :ret
 (s/keys))

(defn
 set-download-behavior
 "Set the behavior when downloading a file.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :behavior      | Whether to allow all or deny all download requests, or use default Chrome behavior if\navailable (otherwise deny).\n  :download-path | The default path to save downloaded files to. This is requred if behavior is set to 'allow' (optional)"
 ([]
  (set-download-behavior
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [behavior download-path]}]
  (set-download-behavior
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [behavior download-path]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setDownloadBehavior"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:behavior "behavior", :download-path "downloadPath"})]
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
 set-download-behavior
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/behavior]
    :opt-un
    [:clj-chrome-devtools.impl.define/download-path]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/behavior]
    :opt-un
    [:clj-chrome-devtools.impl.define/download-path])))
 :ret
 (s/keys))

(defn
 set-geolocation-override
 "Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position\nunavailable.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :latitude  | Mock latitude (optional)\n  :longitude | Mock longitude (optional)\n  :accuracy  | Mock accuracy (optional)"
 ([]
  (set-geolocation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [latitude longitude accuracy]}]
  (set-geolocation-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [latitude longitude accuracy]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setGeolocationOverride"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:latitude "latitude",
      :longitude "longitude",
      :accuracy "accuracy"})]
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
 set-geolocation-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [:clj-chrome-devtools.impl.define/latitude
     :clj-chrome-devtools.impl.define/longitude
     :clj-chrome-devtools.impl.define/accuracy]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [:clj-chrome-devtools.impl.define/latitude
     :clj-chrome-devtools.impl.define/longitude
     :clj-chrome-devtools.impl.define/accuracy])))
 :ret
 (s/keys))

(defn
 set-lifecycle-events-enabled
 "Controls whether page will emit lifecycle events.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | If true, starts emitting lifecycle events."
 ([]
  (set-lifecycle-events-enabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-lifecycle-events-enabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setLifecycleEventsEnabled"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:enabled "enabled"})]
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
 set-lifecycle-events-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled])))
 :ret
 (s/keys))

(defn
 set-touch-emulation-enabled
 "Toggles mouse event-based touch event emulation.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :enabled       | Whether the touch event emulation should be enabled.\n  :configuration | Touch/gesture events configuration. Default: current platform. (optional)"
 ([]
  (set-touch-emulation-enabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [enabled configuration]}]
  (set-touch-emulation-enabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled configuration]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setTouchEmulationEnabled"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:enabled "enabled", :configuration "configuration"})]
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
 set-touch-emulation-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled]
    :opt-un
    [:clj-chrome-devtools.impl.define/configuration]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled]
    :opt-un
    [:clj-chrome-devtools.impl.define/configuration])))
 :ret
 (s/keys))

(defn
 start-screencast
 "Starts sending each frame using the `screencastFrame` event.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :format          | Image compression format. (optional)\n  :quality         | Compression quality from range [0..100]. (optional)\n  :max-width       | Maximum screenshot width. (optional)\n  :max-height      | Maximum screenshot height. (optional)\n  :every-nth-frame | Send every n-th frame. (optional)"
 ([]
  (start-screencast
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys [format quality max-width max-height every-nth-frame]}]
  (start-screencast
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [format quality max-width max-height every-nth-frame]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.startScreencast"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:format "format",
      :quality "quality",
      :max-width "maxWidth",
      :max-height "maxHeight",
      :every-nth-frame "everyNthFrame"})]
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
 start-screencast
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [:clj-chrome-devtools.impl.define/format
     :clj-chrome-devtools.impl.define/quality
     :clj-chrome-devtools.impl.define/max-width
     :clj-chrome-devtools.impl.define/max-height
     :clj-chrome-devtools.impl.define/every-nth-frame]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [:clj-chrome-devtools.impl.define/format
     :clj-chrome-devtools.impl.define/quality
     :clj-chrome-devtools.impl.define/max-width
     :clj-chrome-devtools.impl.define/max-height
     :clj-chrome-devtools.impl.define/every-nth-frame])))
 :ret
 (s/keys))

(defn
 stop-loading
 "Force the page stop all navigations and pending resource fetches."
 ([]
  (stop-loading
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-loading
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.stopLoading"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 stop-loading
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 crash
 "Crashes renderer on the IO thread, generates minidumps."
 ([]
  (crash
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (crash
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.crash"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 crash
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 close
 "Tries to close page, running its beforeunload hooks, if any."
 ([]
  (close
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (close
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.close"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 close
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 set-web-lifecycle-state
 "Tries to update the web lifecycle state of the page.\nIt will transition the page to the given state according to:\nhttps://github.com/WICG/web-lifecycle/\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :state | Target lifecycle state"
 ([]
  (set-web-lifecycle-state
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [state]}]
  (set-web-lifecycle-state
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [state]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setWebLifecycleState"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:state "state"})]
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
 set-web-lifecycle-state
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/state]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/state])))
 :ret
 (s/keys))

(defn
 stop-screencast
 "Stops sending each frame in the `screencastFrame`."
 ([]
  (stop-screencast
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-screencast
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.stopScreencast"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 stop-screencast
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 set-produce-compilation-cache
 "Forces compilation cache to be generated for every subresource script.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | null"
 ([]
  (set-produce-compilation-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-produce-compilation-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setProduceCompilationCache"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:enabled "enabled"})]
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
 set-produce-compilation-cache
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled])))
 :ret
 (s/keys))

(defn
 add-compilation-cache
 "Seeds compilation cache for given url. Compilation cache does not survive\ncross-process navigation.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :url  | null\n  :data | Base64-encoded data"
 ([]
  (add-compilation-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [url data]}]
  (add-compilation-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [url data]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.addCompilationCache"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:url "url", :data "data"})]
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
 add-compilation-cache
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/url
     :clj-chrome-devtools.impl.define/data]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/url
     :clj-chrome-devtools.impl.define/data])))
 :ret
 (s/keys))

(defn
 clear-compilation-cache
 "Clears seeded compilation cache."
 ([]
  (clear-compilation-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-compilation-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.clearCompilationCache"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 clear-compilation-cache
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 generate-test-report
 "Generates a report for testing.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :message | Message to be displayed in the report.\n  :group   | Specifies the endpoint group to deliver the report to. (optional)"
 ([]
  (generate-test-report
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [message group]}]
  (generate-test-report
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [message group]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.generateTestReport"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:message "message", :group "group"})]
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
 generate-test-report
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/message]
    :opt-un
    [:clj-chrome-devtools.impl.define/group]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/message]
    :opt-un
    [:clj-chrome-devtools.impl.define/group])))
 :ret
 (s/keys))

(defn
 wait-for-debugger
 "Pauses page execution. Can be resumed using generic Runtime.runIfWaitingForDebugger."
 ([]
  (wait-for-debugger
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (wait-for-debugger
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.waitForDebugger"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
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
 wait-for-debugger
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 set-intercept-file-chooser-dialog
 "Intercept file chooser requests and transfer control to protocol clients.\nWhen file chooser interception is enabled, native file chooser dialog is not shown.\nInstead, a protocol event `Page.fileChooserOpened` is emitted.\nFile chooser can be handled with `page.handleFileChooser` command.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | null"
 ([]
  (set-intercept-file-chooser-dialog
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-intercept-file-chooser-dialog
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.setInterceptFileChooserDialog"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:enabled "enabled"})]
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
 set-intercept-file-chooser-dialog
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/enabled])))
 :ret
 (s/keys))

(defn
 handle-file-chooser
 "Accepts or cancels an intercepted file chooser dialog.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :action | null\n  :files  | Array of absolute file paths to set, only respected with `accept` action. (optional)"
 ([]
  (handle-file-chooser
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [action files]}]
  (handle-file-chooser
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [action files]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Page.handleFileChooser"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:action "action", :files "files"})]
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
 handle-file-chooser
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/action]
    :opt-un
    [:clj-chrome-devtools.impl.define/files]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [:clj-chrome-devtools.impl.define/action]
    :opt-un
    [:clj-chrome-devtools.impl.define/files])))
 :ret
 (s/keys))
