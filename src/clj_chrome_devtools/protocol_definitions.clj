(ns clj-chrome-devtools.protocol-definitions
  "Loads CDP protocol definition JSON files for consumption by def macros."
  (:require [cheshire.core :as cheshire]))

(defn- load-json [json-file]
  (as-> json-file it
    (str "devtools-protocol/json/" it)
    (slurp it)
    (cheshire/parse-string it true)))

(def protocol-files ["browser_protocol.json" "js_protocol.json"])

(defn all-domains []
  (mapcat (comp :domains load-json) protocol-files))

(defn domain-by-name [domain]
  (some #(when (= (:domain %) domain) %) (all-domains)))

(defn commands-for-domain [domain]
  (:commands (domain-by-name domain)))

(defn types-for-domain [domain]
  (:types (domain-by-name domain)))

(defn events-for-domain [domain]
  (:events (domain-by-name domain)))

(defn domains []
  (into #{}
        (map :domain)
        (all-domains)))
