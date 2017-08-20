(ns clj-chrome-devtools.commands.domdebugger
  "DOM debugging allows setting breakpoints on particular DOM operations and events. JavaScript execution will stop on these operations as if there was a regular breakpoint set."
  (:require [clj-chrome-devtools.impl.define :refer [define-command-functions]]))
(define-command-functions "DOMDebugger")
