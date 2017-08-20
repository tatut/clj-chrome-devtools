(ns clj-chrome-devtools.automation
  "Higher level automation convenience API"
  (:require [clj-chrome-devtools.commands.dom :as dom]
            [clj-chrome-devtools.commands.page :as page]
            [clj-chrome-devtools.events :as events]
            [clj-chrome-devtools.core]
            [clojure.core.async :as async :refer [go <!!]]))

;;-DEbug
;(clj-chrome-devtools.core/set-current-connection! (clj-chrome-devtools.core/connect "localhost" 9222))

(defn root
  "Returns the root node for id reference."
  []
  {:node-id (-> (dom/get-document {}) :root :node-id)})

(defn to
  "Navigate to the given URL. Waits for browser load to be finished before returning.
  Returns root node id on success."
  [url]
  (page/enable)
  (events/with-event-wait :page :frame-stopped-loading
    (page/navigate {:url url}))
  (root))



(defn sel
  "Select elements by path. Path is a string."
  [path]
  (map (fn [id]
         {:node-id id})
       (:node-ids (dom/query-selector-all {:node-id 1 :selector path}))))

(defn sel1
  "Select a single element by path."
  [path]
  (dom/query-selector {:node-id 1 :selector path}))
