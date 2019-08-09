(ns clj-chrome-devtools.commands.service-worker
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(s/def
 ::registration-id
 string?)

(s/def
 ::service-worker-registration
 (s/keys
  :req-un
  [::registration-id
   ::scope-url
   ::is-deleted]))

(s/def
 ::service-worker-version-running-status
 #{"stopping" "running" "starting" "stopped"})

(s/def
 ::service-worker-version-status
 #{"new" "activating" "installing" "redundant" "activated" "installed"})

(s/def
 ::service-worker-version
 (s/keys
  :req-un
  [::version-id
   ::registration-id
   ::script-url
   ::running-status
   ::status]
  :opt-un
  [::script-last-modified
   ::script-response-time
   ::controlled-clients
   ::target-id]))

(s/def
 ::service-worker-error-message
 (s/keys
  :req-un
  [::error-message
   ::registration-id
   ::version-id
   ::source-url
   ::line-number
   ::column-number]))
(defn
 deliver-push-message
 "\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :origin          | null\n  :registration-id | null\n  :data            | null"
 ([]
  (deliver-push-message
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin registration-id data]}]
  (deliver-push-message
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin registration-id data]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.deliverPushMessage"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:origin "origin",
      :registration-id "registrationId",
      :data "data"})]
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
 deliver-push-message
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
     ::registration-id
     ::data]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin
     ::registration-id
     ::data])))
 :ret
 (s/keys))

(defn
 disable
 ""
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
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.disable"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {})]
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
 dispatch-sync-event
 "\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :origin          | null\n  :registration-id | null\n  :tag             | null\n  :last-chance     | null"
 ([]
  (dispatch-sync-event
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin registration-id tag last-chance]}]
  (dispatch-sync-event
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [origin registration-id tag last-chance]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.dispatchSyncEvent"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:origin "origin",
      :registration-id "registrationId",
      :tag "tag",
      :last-chance "lastChance"})]
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
 dispatch-sync-event
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
     ::registration-id
     ::tag
     ::last-chance]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin
     ::registration-id
     ::tag
     ::last-chance])))
 :ret
 (s/keys))

(defn
 dispatch-periodic-sync-event
 "\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :origin          | null\n  :registration-id | null\n  :tag             | null"
 ([]
  (dispatch-periodic-sync-event
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin registration-id tag]}]
  (dispatch-periodic-sync-event
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin registration-id tag]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.dispatchPeriodicSyncEvent"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:origin "origin",
      :registration-id "registrationId",
      :tag "tag"})]
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
 dispatch-periodic-sync-event
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
     ::registration-id
     ::tag]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::origin
     ::registration-id
     ::tag])))
 :ret
 (s/keys))

(defn
 enable
 ""
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
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.enable"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {})]
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
 inspect-worker
 "\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :version-id | null"
 ([]
  (inspect-worker
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [version-id]}]
  (inspect-worker
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [version-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.inspectWorker"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:version-id "versionId"})]
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
 inspect-worker
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::version-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::version-id])))
 :ret
 (s/keys))

(defn
 set-force-update-on-page-load
 "\n\nParameters map keys:\n\n\n  Key                        | Description \n  ---------------------------|------------ \n  :force-update-on-page-load | null"
 ([]
  (set-force-update-on-page-load
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [force-update-on-page-load]}]
  (set-force-update-on-page-load
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [force-update-on-page-load]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.setForceUpdateOnPageLoad"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:force-update-on-page-load "forceUpdateOnPageLoad"})]
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
 set-force-update-on-page-load
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::force-update-on-page-load]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::force-update-on-page-load])))
 :ret
 (s/keys))

(defn
 skip-waiting
 "\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :scope-url | null"
 ([]
  (skip-waiting
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [scope-url]}]
  (skip-waiting
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [scope-url]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.skipWaiting"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:scope-url "scopeURL"})]
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
 skip-waiting
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::scope-url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::scope-url])))
 :ret
 (s/keys))

(defn
 start-worker
 "\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :scope-url | null"
 ([]
  (start-worker
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [scope-url]}]
  (start-worker
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [scope-url]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.startWorker"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:scope-url "scopeURL"})]
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
 start-worker
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::scope-url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::scope-url])))
 :ret
 (s/keys))

(defn
 stop-all-workers
 ""
 ([]
  (stop-all-workers
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-all-workers
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.stopAllWorkers"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {})]
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
 stop-all-workers
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
 stop-worker
 "\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :version-id | null"
 ([]
  (stop-worker
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [version-id]}]
  (stop-worker
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [version-id]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.stopWorker"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:version-id "versionId"})]
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
 stop-worker
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::version-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::version-id])))
 :ret
 (s/keys))

(defn
 unregister
 "\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :scope-url | null"
 ([]
  (unregister
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [scope-url]}]
  (unregister
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [scope-url]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.unregister"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:scope-url "scopeURL"})]
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
 unregister
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::scope-url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::scope-url])))
 :ret
 (s/keys))

(defn
 update-registration
 "\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :scope-url | null"
 ([]
  (update-registration
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [scope-url]}]
  (update-registration
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [scope-url]}]
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "ServiceWorker.updateRegistration"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:scope-url "scopeURL"})]
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
 update-registration
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::scope-url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::scope-url])))
 :ret
 (s/keys))
