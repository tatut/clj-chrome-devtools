(ns clj-cdp-test.commands.domsnapshot
  "This domain facilitates obtaining document snapshots with DOM, layout, and style information."
  (:require [clj-cdp-test.define :refer [define-command-functions]]))
(define-command-functions "DOMSnapshot")