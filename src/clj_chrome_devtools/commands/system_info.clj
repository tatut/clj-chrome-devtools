(ns clj-chrome-devtools.commands.system-info
  "The SystemInfo domain defines methods and events for querying low-level system information."
  (:require [clojure.spec.alpha :as s]))
(s/def
 ::gpu-device
 (s/keys
  :req-un
  [::vendor-id
   ::device-id
   ::vendor-string
   ::device-string
   ::driver-vendor
   ::driver-version]))

(s/def
 ::size
 (s/keys
  :req-un
  [::width
   ::height]))

(s/def
 ::video-decode-accelerator-capability
 (s/keys
  :req-un
  [::profile
   ::max-resolution
   ::min-resolution]))

(s/def
 ::video-encode-accelerator-capability
 (s/keys
  :req-un
  [::profile
   ::max-resolution
   ::max-framerate-numerator
   ::max-framerate-denominator]))

(s/def
 ::subsampling-format
 #{"yuv422" "yuv444" "yuv420"})

(s/def
 ::image-type
 #{"webp" "jpeg" "unknown"})

(s/def
 ::image-decode-accelerator-capability
 (s/keys
  :req-un
  [::image-type
   ::max-dimensions
   ::min-dimensions
   ::subsamplings]))

(s/def
 ::gpu-info
 (s/keys
  :req-un
  [::devices
   ::driver-bug-workarounds
   ::video-decoding
   ::video-encoding
   ::image-decoding]
  :opt-un
  [::aux-attributes
   ::feature-status]))

(s/def
 ::process-info
 (s/keys
  :req-un
  [::type
   ::id
   ::cpu-time]))
(defn
 get-info
 "Returns information about the system.\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :gpu           | Information about the GPUs on the system.\n  :model-name    | A platform-dependent description of the model of the machine. On Mac OS, this is, for\nexample, 'MacBookPro'. Will be the empty string if not supported.\n  :model-version | A platform-dependent description of the version of the machine. On Mac OS, this is, for\nexample, '10.1'. Will be the empty string if not supported.\n  :command-line  | The command line string used to launch the browser. Will be the empty string if not\nsupported."
 ([]
  (get-info
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-info
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "SystemInfo.getInfo"
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
 get-info
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
  [::gpu
   ::model-name
   ::model-version
   ::command-line]))

(defn
 get-process-info
 "Returns information about all running processes.\n\nReturn map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :process-info | An array of process info blocks."
 ([]
  (get-process-info
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-process-info
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "SystemInfo.getProcessInfo"
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
 get-process-info
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
  [::process-info]))
