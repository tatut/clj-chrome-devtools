(ns clj-chrome-devtools.impl.util
  (:require [clojure.string :as str]))

(defn camel->clojure [string]
  (-> string
      ;; borrowed from r0man/inflections
      ;; -------------------------------
      ;; consoleAPICalled -> console-api-called
      (str/replace #"([A-Z]+)([A-Z][a-z])" "$1-$2")
      ;; console1APICalled -> console1-api-called
      (str/replace #"([a-z\d])([A-Z])" "$1-$2")
      ;; console api called -> console-api-called
      (str/replace #"\s+" "-")
      ;; ;; console_api_called -> console-api-called
      (str/replace #"_" "-")
      ;; Lower case everything
      (str/lower-case)))
