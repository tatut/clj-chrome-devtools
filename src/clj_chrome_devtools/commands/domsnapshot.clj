(ns clj-chrome-devtools.commands.domsnapshot
  "This domain facilitates obtaining document snapshots with DOM, layout, and style information."
  (:require [clj-chrome-devtools.impl.define :refer [define-domain]]))
(define-domain "DOMSnapshot")