(ns clj-chrome-devtools.commands.device-orientation
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(defn
 clear-device-orientation-override
 "Clears the overridden Device Orientation."
 ([]
  (clear-device-orientation-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-device-orientation-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "DeviceOrientation"
   "clearDeviceOrientationOverride"
   params
   {})))

(s/fdef
 clear-device-orientation-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat :params (s/keys))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 set-device-orientation-override
 "Overrides the Device Orientation.\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :alpha | Mock alpha\n  :beta  | Mock beta\n  :gamma | Mock gamma"
 ([]
  (set-device-orientation-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [alpha beta gamma]}]
  (set-device-orientation-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [alpha beta gamma]}]
  (cmd/command
   connection
   "DeviceOrientation"
   "setDeviceOrientationOverride"
   params
   {:alpha "alpha", :beta "beta", :gamma "gamma"})))

(s/fdef
 set-device-orientation-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::alpha
     ::beta
     ::gamma]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::alpha
     ::beta
     ::gamma])))
 :ret
 (s/keys))
