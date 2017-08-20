(ns clj-chrome-devtools.protocol-definitions
  "Loads CDP protocol definition JSON files for consumption by def macros."
  (:require [cheshire.core :as cheshire]))

(defn- load-json [json-file]
  (as-> json-file it
       (str "resources/devtools-protocol/json/" it)
       (slurp it)
       (cheshire/parse-string it true)))

(def browser-protocol-json (load-json "browser_protocol.json"))
(def js-protocol-json (load-json "js_protocol.json"))

(defn all-domains []
  (concat (:domains browser-protocol-json)
          (:domains js-protocol-json)))

(defn commands-for-domain [domain]
  (->> (all-domains)
       (filter #(= (:domain %) domain))
       first
       :commands))

(defn domains []
  (into #{}
        (map :domain)
        (all-domains)))
