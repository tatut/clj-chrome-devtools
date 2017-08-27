(ns clj-chrome-devtools.commands.page
  "Actions and events related to the inspected page belong to the page domain."
  (:require [clj-chrome-devtools.impl.define :refer [define-domain]]))
(define-domain "Page")