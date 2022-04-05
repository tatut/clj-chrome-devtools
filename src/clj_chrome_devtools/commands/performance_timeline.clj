(ns clj-chrome-devtools.commands.performance-timeline
  "Reporting of performance timeline events, as specified in\nhttps://w3c.github.io/performance-timeline/#dom-performanceobserver."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::largest-contentful-paint
 (s/keys
  :req-un
  [::render-time
   ::load-time
   ::size]
  :opt-un
  [::element-id
   ::url
   ::node-id]))

(s/def
 ::layout-shift-attribution
 (s/keys
  :req-un
  [::previous-rect
   ::current-rect]
  :opt-un
  [::node-id]))

(s/def
 ::layout-shift
 (s/keys
  :req-un
  [::value
   ::had-recent-input
   ::last-input-time
   ::sources]))

(s/def
 ::timeline-event
 (s/keys
  :req-un
  [::frame-id
   ::type
   ::name
   ::time]
  :opt-un
  [::duration
   ::lcp-details
   ::layout-shift-details]))
(defn
 enable
 "Previously buffered events would be reported before method returns.\nSee also: timelineEventAdded\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :event-types | The types of event to report, as specified in\nhttps://w3c.github.io/performance-timeline/#dom-performanceentry-entrytype\nThe specified filter overrides any previous filters, passing empty\nfilter disables recording.\nNote that not all types exposed to the web platform are currently supported."
 ([]
  (enable
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [event-types]}]
  (enable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [event-types]}]
  (cmd/command
   connection
   "PerformanceTimeline"
   "enable"
   params
   {:event-types "eventTypes"})))

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
    :req-un
    [::event-types]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::event-types])))
 :ret
 (s/keys))
