(ns clj-chrome-devtools.commands.web-authn
  "This domain allows configuring virtual authenticators to test the WebAuthn\nAPI."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::authenticator-id
 string?)

(s/def
 ::authenticator-protocol
 #{"u2f" "ctap2"})

(s/def
 ::ctap2-version
 #{"ctap2_1" "ctap2_0"})

(s/def
 ::authenticator-transport
 #{"internal" "cable" "ble" "nfc" "usb"})

(s/def
 ::virtual-authenticator-options
 (s/keys
  :req-un
  [::protocol
   ::transport]
  :opt-un
  [::ctap2-version
   ::has-resident-key
   ::has-user-verification
   ::has-large-blob
   ::has-cred-blob
   ::has-min-pin-length
   ::automatic-presence-simulation
   ::is-user-verified]))

(s/def
 ::credential
 (s/keys
  :req-un
  [::credential-id
   ::is-resident-credential
   ::private-key
   ::sign-count]
  :opt-un
  [::rp-id
   ::user-handle
   ::large-blob]))
(defn
 enable
 "Enable the WebAuthn domain and start intercepting credential storage and\nretrieval with a virtual authenticator."
 ([]
  (enable
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (enable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "WebAuthn"
   "enable"
   params
   {})))

(s/fdef
 enable
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
 disable
 "Disable the WebAuthn domain."
 ([]
  (disable
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (disable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "WebAuthn"
   "disable"
   params
   {})))

(s/fdef
 disable
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
 add-virtual-authenticator
 "Creates and adds a virtual authenticator.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :options | null\n\nReturn map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null"
 ([]
  (add-virtual-authenticator
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [options]}]
  (add-virtual-authenticator
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [options]}]
  (cmd/command
   connection
   "WebAuthn"
   "addVirtualAuthenticator"
   params
   {:options "options"})))

(s/fdef
 add-virtual-authenticator
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::options]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::options])))
 :ret
 (s/keys
  :req-un
  [::authenticator-id]))

(defn
 remove-virtual-authenticator
 "Removes the given authenticator.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null"
 ([]
  (remove-virtual-authenticator
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id]}]
  (remove-virtual-authenticator
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id]}]
  (cmd/command
   connection
   "WebAuthn"
   "removeVirtualAuthenticator"
   params
   {:authenticator-id "authenticatorId"})))

(s/fdef
 remove-virtual-authenticator
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id])))
 :ret
 (s/keys))

(defn
 add-credential
 "Adds the credential to the specified authenticator.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null\n  :credential       | null"
 ([]
  (add-credential
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id credential]}]
  (add-credential
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id credential]}]
  (cmd/command
   connection
   "WebAuthn"
   "addCredential"
   params
   {:authenticator-id "authenticatorId", :credential "credential"})))

(s/fdef
 add-credential
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::credential]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::credential])))
 :ret
 (s/keys))

(defn
 get-credential
 "Returns a single credential stored in the given virtual authenticator that\nmatches the credential ID.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null\n  :credential-id    | null\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :credential | null"
 ([]
  (get-credential
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id credential-id]}]
  (get-credential
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id credential-id]}]
  (cmd/command
   connection
   "WebAuthn"
   "getCredential"
   params
   {:authenticator-id "authenticatorId",
    :credential-id "credentialId"})))

(s/fdef
 get-credential
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::credential-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::credential-id])))
 :ret
 (s/keys
  :req-un
  [::credential]))

(defn
 get-credentials
 "Returns all the credentials stored in the given virtual authenticator.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :credentials | null"
 ([]
  (get-credentials
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id]}]
  (get-credentials
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id]}]
  (cmd/command
   connection
   "WebAuthn"
   "getCredentials"
   params
   {:authenticator-id "authenticatorId"})))

(s/fdef
 get-credentials
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id])))
 :ret
 (s/keys
  :req-un
  [::credentials]))

(defn
 remove-credential
 "Removes a credential from the authenticator.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null\n  :credential-id    | null"
 ([]
  (remove-credential
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id credential-id]}]
  (remove-credential
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id credential-id]}]
  (cmd/command
   connection
   "WebAuthn"
   "removeCredential"
   params
   {:authenticator-id "authenticatorId",
    :credential-id "credentialId"})))

(s/fdef
 remove-credential
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::credential-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::credential-id])))
 :ret
 (s/keys))

(defn
 clear-credentials
 "Clears all the credentials from the specified device.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null"
 ([]
  (clear-credentials
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id]}]
  (clear-credentials
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id]}]
  (cmd/command
   connection
   "WebAuthn"
   "clearCredentials"
   params
   {:authenticator-id "authenticatorId"})))

(s/fdef
 clear-credentials
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id])))
 :ret
 (s/keys))

(defn
 set-user-verified
 "Sets whether User Verification succeeds or fails for an authenticator.\nThe default is true.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null\n  :is-user-verified | null"
 ([]
  (set-user-verified
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id is-user-verified]}]
  (set-user-verified
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id is-user-verified]}]
  (cmd/command
   connection
   "WebAuthn"
   "setUserVerified"
   params
   {:authenticator-id "authenticatorId",
    :is-user-verified "isUserVerified"})))

(s/fdef
 set-user-verified
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::is-user-verified]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::is-user-verified])))
 :ret
 (s/keys))

(defn
 set-automatic-presence-simulation
 "Sets whether tests of user presence will succeed immediately (if true) or fail to resolve (if false) for an authenticator.\nThe default is true.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :authenticator-id | null\n  :enabled          | null"
 ([]
  (set-automatic-presence-simulation
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [authenticator-id enabled]}]
  (set-automatic-presence-simulation
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [authenticator-id enabled]}]
  (cmd/command
   connection
   "WebAuthn"
   "setAutomaticPresenceSimulation"
   params
   {:authenticator-id "authenticatorId", :enabled "enabled"})))

(s/fdef
 set-automatic-presence-simulation
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::authenticator-id
     ::enabled])))
 :ret
 (s/keys))
