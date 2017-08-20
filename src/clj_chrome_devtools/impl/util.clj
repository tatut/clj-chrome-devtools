(ns clj-chrome-devtools.impl.util
  (:require [clojure.string :as str]))

(defn camel->clojure [string]
  (-> string
      ;; Add dashes before uppercase letters that come after a lowercase letter
      (str/replace #"([a-z])([A-Z])"
                   (fn [[_ lower upper]]
                     (str lower "-" upper)))
      ;; Lower case everything
      (str/lower-case)))
