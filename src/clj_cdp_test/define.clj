(ns clj-cdp-test.define
  "Macro for defining a domain of commands."
  (:require [clj-cdp-test.protocol-definitions :as proto]
            [clj-cdp-test.connection :as connection]
            [clojure.string :as str]
            [clojure.core.async :as async :refer [go <!! >!]]))

(defn camel->clojure [string]
  (str/replace string #"([a-z])([A-Z])"
               (fn [[_ prev capital]]
                 (str prev "-" (str/lower-case capital)))))

(def to-symbol (comp symbol camel->clojure))

(defonce command-id (atom 0))

(defn next-command-id! []
  (swap! command-id inc))

(defn command-payload [id name parameter-names parameter-values]
  {:id id
   :method name
   :params (zipmap parameter-names parameter-values)})

(defmacro define-command-functions [domain]
  `(do
     ~@(for [{:keys [name description parameters]} (proto/commands-for-domain domain)
             :let [fn-name (to-symbol name)
                   params (mapv (comp to-symbol :name) parameters)]]
         `(defn ~fn-name
            ~(str description
                  (when-not (empty? parameters)
                    (str "\nParameters:\n"
                         (str/join "\n"
                                   (map #(format "%-20s %s" (camel->clojure (:name %)) (:description %))
                                        parameters)))))
            ([~@params]
             (~(to-symbol name) (connection/get-current-connection)
              ~@params))
            ([connection# ~@params]
             (let [id# (next-command-id!)
                   method# ~(str domain "." name)
                   ch# (async/chan)
                   payload# (command-payload id# method#
                                             ~(mapv :name parameters)
                                             [~@params])]
               (connection/send-command connection# payload# id# #(go (>! ch# %)))
               (let [result# (<!! ch#)]
                 (if-let [error# (:error result#)]
                   (throw (ex-info (str "Error in command " method# ": " (:message error#))
                                   {:request payload#
                                    :error error#}))
                   (:result result#)))))))))

(comment
  ;; To regenerate the command namespaces, run this
  (doseq [domain (proto/domains)]
    (let [clj-name (camel->clojure domain)
          file-name (str "src/clj_cdp_test/commands/" (str/lower-case (str/replace clj-name "-" "_")) ".clj")]
      (spit file-name
            (str "(ns clj-cdp-test.commands." (str/lower-case clj-name) "\n"
                 "  (:require [clj-cdp-test.define :refer [define-command-functions]]))\n"
                 "(define-command-functions \"" domain "\")")))))
