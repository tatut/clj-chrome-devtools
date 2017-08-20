(ns clj-cdp-test.commands.network
  "Network domain allows tracking network activities of the page. It exposes information about http, file, data and other requests and responses, their headers, bodies, timing, etc."
  (:require [clj-cdp-test.define :refer [define-command-functions]]))
(define-command-functions "Network")