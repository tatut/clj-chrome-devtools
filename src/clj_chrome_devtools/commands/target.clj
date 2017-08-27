(ns clj-chrome-devtools.commands.target
  "Supports additional targets discovery and allows to attach to them."
  (:require [clj-chrome-devtools.impl.define :refer [define-domain]]))
(define-domain "Target")