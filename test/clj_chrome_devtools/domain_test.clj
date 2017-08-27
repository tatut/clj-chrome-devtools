(ns clj-chrome-devtools.domain-test
  "Test that all namespaces can be required and have public vars."
  (:require  [clojure.test :as t :refer [deftest is]]))

(def domains '[clj-chrome-devtools.commands.accessibility
               clj-chrome-devtools.commands.animation
               clj-chrome-devtools.commands.application-cache
               clj-chrome-devtools.commands.audits
               clj-chrome-devtools.commands.browser
               clj-chrome-devtools.commands.cache-storage
               clj-chrome-devtools.commands.console
               clj-chrome-devtools.commands.css
               clj-chrome-devtools.commands.database
               clj-chrome-devtools.commands.debugger
               clj-chrome-devtools.commands.device-orientation
               clj-chrome-devtools.commands.dom
               clj-chrome-devtools.commands.domdebugger
               clj-chrome-devtools.commands.domsnapshot
               clj-chrome-devtools.commands.domstorage
               clj-chrome-devtools.commands.emulation
               clj-chrome-devtools.commands.heap-profiler
               clj-chrome-devtools.commands.indexed-db
               clj-chrome-devtools.commands.input
               clj-chrome-devtools.commands.inspector
               clj-chrome-devtools.commands.io
               clj-chrome-devtools.commands.layer-tree
               clj-chrome-devtools.commands.log
               clj-chrome-devtools.commands.memory
               clj-chrome-devtools.commands.network
               clj-chrome-devtools.commands.overlay
               clj-chrome-devtools.commands.page
               clj-chrome-devtools.commands.performance
               clj-chrome-devtools.commands.profiler
               clj-chrome-devtools.commands.runtime
               clj-chrome-devtools.commands.schema
               clj-chrome-devtools.commands.security
               clj-chrome-devtools.commands.service-worker
               clj-chrome-devtools.commands.storage
               clj-chrome-devtools.commands.system-info
               clj-chrome-devtools.commands.target
               clj-chrome-devtools.commands.tethering
               clj-chrome-devtools.commands.tracing])

(deftest require-domain-namespaces
  (doseq [ns domains]
    (require ns)
    (is (>= (count (ns-publics ns)) 1))))
