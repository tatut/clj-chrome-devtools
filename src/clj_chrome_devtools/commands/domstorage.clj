(ns clj-chrome-devtools.commands.domstorage
  "Query and modify DOM storage."
  (:require [clj-chrome-devtools.impl.define :refer [define-domain]]))
(define-domain "DOMStorage")