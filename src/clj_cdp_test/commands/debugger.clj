(ns clj-cdp-test.commands.debugger
  "Debugger domain exposes JavaScript debugging capabilities. It allows setting and removing breakpoints, stepping through execution, exploring stack traces, etc."
  (:require [clj-cdp-test.define :refer [define-command-functions]]))
(define-command-functions "Debugger")