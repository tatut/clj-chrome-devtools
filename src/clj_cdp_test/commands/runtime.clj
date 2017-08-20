(ns clj-cdp-test.commands.runtime
  "Runtime domain exposes JavaScript runtime by means of remote evaluation and mirror objects. Evaluation results are returned as mirror object that expose object type, string representation and unique identifier that can be used for further object reference. Original objects are maintained in memory unless they are either explicitly released or are released along with the other objects in their object group."
  (:require [clj-cdp-test.define :refer [define-command-functions]]))
(define-command-functions "Runtime")