(ns clj-chrome-devtools.commands.tethering
  "The Tethering domain defines methods and events for browser port binding."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(defn
 bind
 "Request browser port binding.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :port | Port number to bind."
 ([]
  (bind
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [port]}]
  (bind
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [port]}]
  (cmd/command
   connection
   "Tethering"
   "bind"
   params
   {:port "port"})))

(s/fdef
 bind
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::port]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::port])))
 :ret
 (s/keys))

(defn
 unbind
 "Request browser port unbinding.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :port | Port number to unbind."
 ([]
  (unbind
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [port]}]
  (unbind
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [port]}]
  (cmd/command
   connection
   "Tethering"
   "unbind"
   params
   {:port "port"})))

(s/fdef
 unbind
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::port]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::port])))
 :ret
 (s/keys))
