;; Example of using clj-chrome-devtools with babashka
;;
;; Start Chrome in the background:
;; /some/chrome-binary --remote-debugging-port=9222 --headless &
;;
;; Run with:
;; $ bb bb.clj

(require '[babashka.deps :as deps])
(deps/add-deps
 '{:deps {tatut/devtools {:git/url "https://github.com/tatut/clj-chrome-devtools"
                          :git/sha "cc96255433ca406aaba8bcee17d0d0b3b16dc423"}
          org.babashka/spec.alpha {:git/url "https://github.com/babashka/spec.alpha"
                                   :git/sha "1a841c4cc1d4f6dab7505a98ed2d532dd9d56b78"}}})

(require '[clj-chrome-devtools.core :as core]
         '[clj-chrome-devtools.automation :as auto])

(println "Printing to bb.pdf")
(def a (auto/create-automation (core/connect)))
(auto/to a "https://babashka.org")
(auto/print-pdf a "bb.pdf"
                {:print-background true
                 :paper-width 8.3
                 :paper-height 11.7})
