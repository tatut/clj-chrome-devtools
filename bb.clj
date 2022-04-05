;; Example of using clj-chrome-devtools with babashka
;;
;; Make a .classpath
;; $ clj -A:bb -Spath > .classpath
;;
;; Start Chrome in the background:
;; /some/chrome-binary --remote-debugging-port=9222 --headless &
;;
;; Run with:
;; $ bb --classpath $(cat .classpath) bb.clj

(require '[clj-chrome-devtools.core :as core]
         '[clj-chrome-devtools.automation :as auto])

(println "Printing to bb.pdf")
(def a (auto/create-automation (core/connect)))
(auto/to a "https://babashka.org")
(auto/print-pdf a "bb.pdf"
                {:print-background true
                 :paper-width 8.3
                 :paper-height 11.7})
