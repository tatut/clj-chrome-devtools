(ns clj-chrome-devtools.commands.debugger
  "Debugger domain exposes JavaScript debugging capabilities. It allows setting and removing breakpoints, stepping through execution, exploring stack traces, etc."
  (:require [clj-chrome-devtools.impl.define :refer [define-command-functions]]))
(define-command-functions "Debugger")
