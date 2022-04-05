(ns clj-chrome-devtools.commands.layer-tree
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::layer-id
 string?)

(s/def
 ::snapshot-id
 string?)

(s/def
 ::scroll-rect
 (s/keys
  :req-un
  [::rect
   ::type]))

(s/def
 ::sticky-position-constraint
 (s/keys
  :req-un
  [::sticky-box-rect
   ::containing-block-rect]
  :opt-un
  [::nearest-layer-shifting-sticky-box
   ::nearest-layer-shifting-containing-block]))

(s/def
 ::picture-tile
 (s/keys
  :req-un
  [::x
   ::y
   ::picture]))

(s/def
 ::layer
 (s/keys
  :req-un
  [::layer-id
   ::offset-x
   ::offset-y
   ::width
   ::height
   ::paint-count
   ::draws-content]
  :opt-un
  [::parent-layer-id
   ::backend-node-id
   ::transform
   ::anchor-x
   ::anchor-y
   ::anchor-z
   ::invisible
   ::scroll-rects
   ::sticky-position-constraint]))

(s/def
 ::paint-profile
 (s/coll-of number?))
(defn
 compositing-reasons
 "Provides the reasons why the given layer was composited.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :layer-id | The id of the layer for which we want to get the reasons it was composited.\n\nReturn map keys:\n\n\n  Key                     | Description \n  ------------------------|------------ \n  :compositing-reasons    | A list of strings specifying reasons for the given layer to become composited.\n  :compositing-reason-ids | A list of strings specifying reason IDs for the given layer to become composited."
 ([]
  (compositing-reasons
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [layer-id]}]
  (compositing-reasons
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [layer-id]}]
  (cmd/command
   connection
   "LayerTree"
   "compositingReasons"
   params
   {:layer-id "layerId"})))

(s/fdef
 compositing-reasons
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::layer-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::layer-id])))
 :ret
 (s/keys
  :req-un
  [::compositing-reasons
   ::compositing-reason-ids]))

(defn
 disable
 "Disables compositing tree inspection."
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
   "LayerTree"
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
 "Enables compositing tree inspection."
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
   "LayerTree"
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
 load-snapshot
 "Returns the snapshot identifier.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :tiles | An array of tiles composing the snapshot.\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :snapshot-id | The id of the snapshot."
 ([]
  (load-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [tiles]}]
  (load-snapshot
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [tiles]}]
  (cmd/command
   connection
   "LayerTree"
   "loadSnapshot"
   params
   {:tiles "tiles"})))

(s/fdef
 load-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::tiles]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::tiles])))
 :ret
 (s/keys
  :req-un
  [::snapshot-id]))

(defn
 make-snapshot
 "Returns the layer snapshot identifier.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :layer-id | The id of the layer.\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :snapshot-id | The id of the layer snapshot."
 ([]
  (make-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [layer-id]}]
  (make-snapshot
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [layer-id]}]
  (cmd/command
   connection
   "LayerTree"
   "makeSnapshot"
   params
   {:layer-id "layerId"})))

(s/fdef
 make-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::layer-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::layer-id])))
 :ret
 (s/keys
  :req-un
  [::snapshot-id]))

(defn
 profile-snapshot
 "\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :snapshot-id      | The id of the layer snapshot.\n  :min-repeat-count | The maximum number of times to replay the snapshot (1, if not specified). (optional)\n  :min-duration     | The minimum duration (in seconds) to replay the snapshot. (optional)\n  :clip-rect        | The clip rectangle to apply when replaying the snapshot. (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :timings | The array of paint profiles, one per run."
 ([]
  (profile-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [snapshot-id min-repeat-count min-duration clip-rect]}]
  (profile-snapshot
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [snapshot-id min-repeat-count min-duration clip-rect]}]
  (cmd/command
   connection
   "LayerTree"
   "profileSnapshot"
   params
   {:snapshot-id "snapshotId",
    :min-repeat-count "minRepeatCount",
    :min-duration "minDuration",
    :clip-rect "clipRect"})))

(s/fdef
 profile-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::snapshot-id]
    :opt-un
    [::min-repeat-count
     ::min-duration
     ::clip-rect]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::snapshot-id]
    :opt-un
    [::min-repeat-count
     ::min-duration
     ::clip-rect])))
 :ret
 (s/keys
  :req-un
  [::timings]))

(defn
 release-snapshot
 "Releases layer snapshot captured by the back-end.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :snapshot-id | The id of the layer snapshot."
 ([]
  (release-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [snapshot-id]}]
  (release-snapshot
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [snapshot-id]}]
  (cmd/command
   connection
   "LayerTree"
   "releaseSnapshot"
   params
   {:snapshot-id "snapshotId"})))

(s/fdef
 release-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::snapshot-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::snapshot-id])))
 :ret
 (s/keys))

(defn
 replay-snapshot
 "Replays the layer snapshot and returns the resulting bitmap.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :snapshot-id | The id of the layer snapshot.\n  :from-step   | The first step to replay from (replay from the very start if not specified). (optional)\n  :to-step     | The last step to replay to (replay till the end if not specified). (optional)\n  :scale       | The scale to apply while replaying (defaults to 1). (optional)\n\nReturn map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :data-url | A data: URL for resulting image."
 ([]
  (replay-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [snapshot-id from-step to-step scale]}]
  (replay-snapshot
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [snapshot-id from-step to-step scale]}]
  (cmd/command
   connection
   "LayerTree"
   "replaySnapshot"
   params
   {:snapshot-id "snapshotId",
    :from-step "fromStep",
    :to-step "toStep",
    :scale "scale"})))

(s/fdef
 replay-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::snapshot-id]
    :opt-un
    [::from-step
     ::to-step
     ::scale]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::snapshot-id]
    :opt-un
    [::from-step
     ::to-step
     ::scale])))
 :ret
 (s/keys
  :req-un
  [::data-url]))

(defn
 snapshot-command-log
 "Replays the layer snapshot and returns canvas log.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :snapshot-id | The id of the layer snapshot.\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :command-log | The array of canvas function calls."
 ([]
  (snapshot-command-log
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [snapshot-id]}]
  (snapshot-command-log
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [snapshot-id]}]
  (cmd/command
   connection
   "LayerTree"
   "snapshotCommandLog"
   params
   {:snapshot-id "snapshotId"})))

(s/fdef
 snapshot-command-log
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::snapshot-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::snapshot-id])))
 :ret
 (s/keys
  :req-un
  [::command-log]))
