(ns clj-chrome-devtools.commands.cast
  "A domain for interacting with Cast, Presentation API, and Remote Playback API\nfunctionalities."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::sink
 (s/keys
  :req-un
  [::name
   ::id]
  :opt-un
  [::session]))
(defn
 enable
 "Starts observing for sinks that can be used for tab mirroring, and if set,\nsinks compatible with |presentationUrl| as well. When sinks are found, a\n|sinksUpdated| event is fired.\nAlso starts observing for issue messages. When an issue is added or removed,\nan |issueUpdated| event is fired.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :presentation-url | null (optional)"
 ([]
  (enable
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [presentation-url]}]
  (enable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [presentation-url]}]
  (cmd/command
   connection
   "Cast"
   "enable"
   params
   {:presentation-url "presentationUrl"})))

(s/fdef
 enable
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::presentation-url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::presentation-url])))
 :ret
 (s/keys))

(defn
 disable
 "Stops observing for sinks and issues."
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
   "Cast"
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
 set-sink-to-use
 "Sets a sink to be used when the web page requests the browser to choose a\nsink via Presentation API, Remote Playback API, or Cast SDK.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :sink-name | null"
 ([]
  (set-sink-to-use
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [sink-name]}]
  (set-sink-to-use
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [sink-name]}]
  (cmd/command
   connection
   "Cast"
   "setSinkToUse"
   params
   {:sink-name "sinkName"})))

(s/fdef
 set-sink-to-use
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::sink-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::sink-name])))
 :ret
 (s/keys))

(defn
 start-tab-mirroring
 "Starts mirroring the tab to the sink.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :sink-name | null"
 ([]
  (start-tab-mirroring
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [sink-name]}]
  (start-tab-mirroring
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [sink-name]}]
  (cmd/command
   connection
   "Cast"
   "startTabMirroring"
   params
   {:sink-name "sinkName"})))

(s/fdef
 start-tab-mirroring
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::sink-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::sink-name])))
 :ret
 (s/keys))

(defn
 stop-casting
 "Stops the active Cast session on the sink.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :sink-name | null"
 ([]
  (stop-casting
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [sink-name]}]
  (stop-casting
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [sink-name]}]
  (cmd/command
   connection
   "Cast"
   "stopCasting"
   params
   {:sink-name "sinkName"})))

(s/fdef
 stop-casting
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::sink-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::sink-name])))
 :ret
 (s/keys))
