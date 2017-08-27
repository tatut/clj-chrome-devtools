(ns clj-chrome-devtools.commands.network
  "Network domain allows tracking network activities of the page. It exposes information about http, file, data and other requests and responses, their headers, bodies, timing, etc."
  (:require [clj-chrome-devtools.impl.define :refer [define-domain]]))
(define-domain "Network")