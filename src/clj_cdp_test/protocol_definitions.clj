(ns clj-cdp-test.protocol-definitions
  "Loads CDP protocol definition JSON files for consumption by def macros."
  (:require [cheshire.core :as cheshire]))

(defn- load-json [json-file]
  (as-> json-file it
       (str "resources/devtools-protocol/json/" it)
       (slurp it)
       (cheshire/parse-string it true)))

(def browser-protocol-json (load-json "browser_protocol.json"))

(defn commands-for-domain [domain]
  (->> browser-protocol-json
       :domains
       (filter #(= (:domain %) domain))
       first
       :commands))

(defn domains []
  (into #{}
        (map :domain)
        (:domains browser-protocol-json)))
