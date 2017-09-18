(defproject clj-chrome-devtools "0.2.1"
  :description "Clojure API for Chrome DevTools remote"
  :license {:name "MIT License"}
  :url "https://github.com/tatut/clj-chrome-devtools"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [http-kit "2.2.0"]
                 [cheshire "5.8.0"]
                 [stylefruits/gniazdo "1.0.1"]
                 [org.clojure/core.async "0.3.443"]
                 [com.taoensso/timbre "4.10.0"]]
  :plugins [[lein-codox "0.10.3"]]
  :codox {:output-path "docs/api"
          :metadata {:doc/format :markdown}})
