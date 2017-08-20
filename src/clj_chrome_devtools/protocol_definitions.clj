(ns clj-chrome-devtools.protocol-definitions
  "Loads CDP protocol definition JSON files for consumption by def macros."
  (:require [cheshire.core :as cheshire]
            [clojure.java.io :as io]))

(defn- load-json [json-file]
  (as-> json-file it
    (str "devtools-protocol/json/" it)
    (io/resource it)
    (slurp it)
    (cheshire/parse-string it true)))

(def protocol-files ["browser_protocol.json" "js_protocol.json"])

(defn all-domains []
  (mapcat (comp :domains load-json) protocol-files))

(defn commands-for-domain [domain]
  (->> (all-domains)
       (filter #(= (:domain %) domain))
       first
       :commands))

(defn domains []
  (into #{}
        (map :domain)
        (all-domains)))
