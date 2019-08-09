(ns clj-chrome-devtools.impl.define
  "Macro for defining a domain of commands."
  (:require [clj-chrome-devtools.protocol-definitions :as proto]
            [clj-chrome-devtools.impl.connection :as connection]
            [clojure.string :as str]
            [clojure.core.async :as async :refer [go <!! >!]]
            [clojure.set :as set]
            [clj-chrome-devtools.impl.util :refer [camel->clojure]]
            [clojure.spec.alpha :as s]
            [clojure.pprint :as pprint]))


(def to-symbol (comp symbol camel->clojure))

(defonce command-id (atom 0))

(defn next-command-id! []
  (swap! command-id inc))

(defn command-payload [id name params parameter-names]
  {:id id
   :method name
   :params (set/rename-keys params parameter-names)})

(defn process-doc [doc]
  (some-> doc
          (str/replace #"<code>" "`")
          (str/replace #"</code>" "`")))

(defn- describe-map-keys [ks]
  (let [max-key-length (reduce max 0 (map (comp count camel->clojure :name) ks))]
    (str "\n\n  Key " (str/join (repeat (- max-key-length 2) " ")) "| Description "
         "\n  " (str/join (repeat (+ 2 max-key-length) "-")) "|------------ \n"
         (str/join "\n"
                   (map #(str (format (str "  :%-" max-key-length "s | %s")
                                      (camel->clojure (:name %))
                                      (process-doc (:description %)))
                              (when (:optional %)
                                " (optional)"))
                        ks)))))

(def kw (comp keyword camel->clojure))

(defn ns-kw [string]
  (keyword (str *ns*) (camel->clojure string)))

(defn keys-spec [properties]
  (let [{opt-un true req-un false} (group-by (comp boolean :optional) properties)]
    `(s/keys ~@(when-not (empty? req-un)
                 `(:req-un [~@(map (comp ns-kw :name) req-un)]))
             ~@(when-not (empty? opt-un)
                 `(:opt-un [~@(map (comp ns-kw :name) opt-un)])))))

(defn spec-for-type [{type :type :as t}]
  (if (:enum t)
    ;; Enumerated type, just output the set of valid values
    (into #{} (:enum t))

    (case type
      "object" (keys-spec (:properties t))
      "string" `string?
      "integer" `integer?
      "number" `number?
      "boolean" `boolean?
      "array" `(s/coll-of ~(spec-for-type (:items t)))
      `any?)))

(defmacro define-type-specs [domain]
  `(do
     ~@(for [{id :id type :type :as t} (proto/types-for-domain domain)
             :let [name-kw (ns-kw id)]]

         `(do
            ;; If this is an object, create specs for basic type keys
            ~@(for [{name :name :as property-type} (:properties t)
                    :when (#{"string" "number" "integer" "boolean" "array"} type)]
                `(s/def ~(ns-kw name)
                   ~(spec-for-type property-type)))
            (s/def ~name-kw ~(spec-for-type t))))))

(defn generate-type-specs [domain]
  (mapcat
   (fn [{id :id type :type :as t}]
     (let [name-kw (ns-kw id)]
       (concat
        ;; If this is an object, create specs for basic type keys
        (for [{name :name :as property-type} (:properties t)
              :when (#{"string" "number" "integer" "boolean" "array"} type)]
          `(s/def ~(ns-kw name)
             ~(spec-for-type property-type)))
        [`(s/def ~name-kw ~(spec-for-type t))])))
   (proto/types-for-domain domain)))

(defmacro define-command-functions [domain]
  `(do
     ~@(for [{:keys [name description parameters returns]} (proto/commands-for-domain domain)
             :let [fn-name (to-symbol name)
                   params (mapv (comp to-symbol :name) parameters)
                   param-names (zipmap (map (comp keyword camel->clojure :name) parameters)
                                       (map :name parameters))
                   [required-params optional-params] (split-with (comp not :optional) params)]]
         `(do
            (defn ~fn-name
              ~(str (process-doc description)
                    (when-not (empty? parameters)
                      (str "\n\nParameters map keys:\n"
                           (describe-map-keys parameters)))
                    (when-not (empty? returns)
                      (str "\n\nReturn map keys:\n"
                           (describe-map-keys returns))))
              ([] (~(to-symbol name) (connection/get-current-connection) {}))
              ([{:keys [~@params] :as ~'params}]
               (~(to-symbol name) (connection/get-current-connection) ~'params))
              ([~'connection {:keys [~@params] :as ~'params}]
               (let [id# (next-command-id!)
                     method# ~(str domain "." name)
                     ch# (async/chan)
                     payload# (command-payload id# method# ~'params
                                               ~param-names)]
                 (connection/send-command ~'connection payload# id# #(go (>! ch# %)))
                 (let [result# (<!! ch#)]
                   (if-let [error# (:error result#)]
                     (throw (ex-info (str "Error in command " method# ": " (:message error#))
                                     {:request payload#
                                      :error error#}))
                     (:result result#))))))
            (s/fdef ~fn-name
                    :args (s/or :no-args
                                (s/cat)

                                :just-params
                                (s/cat :params ~(keys-spec parameters))

                                :connection-and-params
                                (s/cat
                                 :connection (s/? connection/connection?)
                                 :params ~(keys-spec parameters)))
                    :ret ~(keys-spec returns))))))

(defn generate-command-functions [domain]
  (mapcat
   (fn [{:keys [name description parameters returns]}]
     (let [fn-name (to-symbol name)
           params (mapv (comp to-symbol :name) parameters)
           param-names (zipmap (map (comp keyword camel->clojure :name) parameters)
                               (map :name parameters))
           [required-params optional-params] (split-with (comp not :optional) params)]
       [`(defn ~fn-name
           ~(str (process-doc description)
                 (when-not (empty? parameters)
                   (str "\n\nParameters map keys:\n"
                        (describe-map-keys parameters)))
                 (when-not (empty? returns)
                   (str "\n\nReturn map keys:\n"
                        (describe-map-keys returns))))
           ([] (~(to-symbol name) (connection/get-current-connection) {}))
           ([{:keys [~@params] :as ~'params}]
            (~(to-symbol name) (connection/get-current-connection) ~'params))
           ([~'connection {:keys [~@params] :as ~'params}]
            (let [id# (next-command-id!)
                  method# ~(str domain "." name)
                  ch# (async/chan)
                  payload# (command-payload id# method# ~'params
                                            ~param-names)]
              (connection/send-command ~'connection payload# id# #(go (>! ch# %)))
              (let [result# (<!! ch#)]
                (if-let [error# (:error result#)]
                  (throw (ex-info (str "Error in command " method# ": " (:message error#))
                                  {:request payload#
                                   :error error#}))
                  (:result result#))))))
        `(s/fdef ~fn-name
           :args (s/or :no-args
                       (s/cat)

                       :just-params
                       (s/cat :params ~(keys-spec parameters))

                       :connection-and-params
                       (s/cat
                        :connection (s/? connection/connection?)
                        :params ~(keys-spec parameters)))
           :ret ~(keys-spec returns))]))
   (proto/commands-for-domain domain)))

(defn call-in-ns [ns fun]
  (let [old-ns (ns-name *ns*)]
    (in-ns ns)
    (let [ret (fun)]
      (in-ns old-ns)
      ret)))

(defn pretty-print-code [the-ns code]
  (str/join "\n"
            (map #(-> (with-out-str
                        (pprint/pprint %))

                      ;; Fix spec and clojure.core refs
                      (str/replace #"clojure.spec.alpha/" "s/")
                      (str/replace #"clojure.core/" "")

                      ;; Use same ns keywords
                      (str/replace (str ":" the-ns "/")
                                   (str "::"))) code)))

#_(defmacro define-event-handlers [domain]
  `(do
     ~@(for [{:keys [name parameters description] :as event} (proto/events-for-domain domain)
             :let [fn-name (symbol (str "on-" (camel->clojure name)))]]
         `(defn ~fn-name
            ~(str "Register handler for " name " event.\n" description "\n\n"
                  (when-not (empty? parameters)
                    (str "The handler will be called with a map with the following keys: "
                         (describe-map-keys parameters)))
                  "\n\nReturns a 0 arity function that will remove this handler when called.")
            [connection# handler#]
            ))))


(defmacro define-domain [domain]
  `(do
     (define-type-specs ~domain)
     (define-command-functions ~domain)
     #_(define-event-handlers domain)))

(defn -main [& args]
  (doseq [{:keys [domain description]} (proto/all-domains)]
    (let [clj-name (camel->clojure domain)
          file-name (str "src/clj_chrome_devtools/commands/" (str/lower-case (str/replace clj-name "-" "_")) ".clj")
          ns-name (str "clj-chrome-devtools.commands." (str/lower-case clj-name))
          ns-sym (symbol ns-name)]
      (println "Generate command namespace: " file-name)
      (spit file-name
            (str "(ns clj-chrome-devtools.commands." (str/lower-case clj-name) "\n"
                 (when description
                   (str "  " (pr-str (process-doc description)) "\n"))
                 "  (:require [clojure.spec.alpha :as s]))\n"
                 (pretty-print-code ns-name (call-in-ns ns-sym #(generate-type-specs domain)))
                 (pretty-print-code ns-name (call-in-ns ns-sym #(generate-command-functions domain))))))))
