(ns clj-chrome-devtools.define
  "Macro for defining a domain of commands."
  (:require [clj-chrome-devtools.protocol-definitions :as proto]
            [clj-chrome-devtools.connection :as connection]
            [clojure.string :as str]
            [clojure.core.async :as async :refer [go <!! >!]]
            [clojure.set :as set]))

(defn camel->clojure [string]
  (-> string
      ;; Add dashes before uppercase letters that come after a lowercase letter
      (str/replace #"([a-z])([A-Z])"
                   (fn [[_ lower upper]]
                     (str lower "-" upper)))
      ;; Lower case everything
      (str/lower-case)))

(def to-symbol (comp symbol camel->clojure))

(defonce command-id (atom 0))

(defn next-command-id! []
  (swap! command-id inc))

(defn command-payload [id name params parameter-names]
  {:id id
   :method name
   :params (set/rename-keys params parameter-names)})

(defn- describe-map-keys [ks]
  (str/join "\n"
            (map #(str (format "  :%-20s %s"
                               (camel->clojure (:name %))
                               (:description %))
                       (when (:optional %)
                         " (optional)"))
                 ks)))

(defmacro define-command-functions [domain]
  `(do
     ~@(for [{:keys [name description parameters returns]} (proto/commands-for-domain domain)
             :let [fn-name (to-symbol name)
                   params (mapv (comp to-symbol :name) parameters)
                   param-names (zipmap (map (comp keyword :name) parameters)
                                       (map :name parameters))
                   [required-params optional-params] (split-with (comp not :optional) params)]]
         `(defn ~fn-name
            ~(str description
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
                   (:result result#)))))))))

(define-command-functions "Page")

;; To regenerate the command namespaces, run this
(doseq [{:keys [domain description]} (proto/all-domains)]
  (let [clj-name (camel->clojure domain)
        file-name (str "src/clj_cdp_test/commands/" (str/lower-case (str/replace clj-name "-" "_")) ".clj")]
    (spit file-name
          (str "(ns clj-chrome-devtools.commands." (str/lower-case clj-name) "\n"
               (when description
                 (str "  " (pr-str description) "\n"))
               "  (:require [clj-chrome-devtools.define :refer [define-command-functions]]))\n"
               "(define-command-functions \"" domain "\")"))))
