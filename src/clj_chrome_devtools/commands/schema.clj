(ns clj-chrome-devtools.commands.schema
  "This domain is deprecated."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::domain
 (s/keys
  :req-un
  [::name
   ::version]))
(defn
 get-domains
 "Returns supported domains.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :domains | List of supported domains."
 ([]
  (get-domains
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-domains
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Schema"
   "getDomains"
   params
   {})))

(s/fdef
 get-domains
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
 (s/keys
  :req-un
  [::domains]))
