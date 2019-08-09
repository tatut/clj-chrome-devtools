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
 ::authenticator-transport
 #{"internal" "cable" "ble" "nfc" "usb"})

(s/def
 ::virtual-authenticator-options
 (s/keys
  :req-un
  [::protocol
   ::transport
   ::has-resident-key
   ::has-user-verification]
  :opt-un
  [::automatic-presence-simulation]))

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
   ::user-handle]))
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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.enable"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.disable"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.addVirtualAuthenticator"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:options "options"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.removeVirtualAuthenticator"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:authenticator-id "authenticatorId"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.addCredential"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:authenticator-id "authenticatorId", :credential "credential"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.getCredential"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:authenticator-id "authenticatorId",
      :credential-id "credentialId"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.getCredentials"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:authenticator-id "authenticatorId"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.clearCredentials"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:authenticator-id "authenticatorId"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
  (let
   [id__69750__auto__
    (cmd/next-command-id!)
    method__69751__auto__
    "WebAuthn.setUserVerified"
    ch__69752__auto__
    (clojure.core.async/chan)
    payload__69753__auto__
    (cmd/command-payload
     id__69750__auto__
     method__69751__auto__
     params
     {:authenticator-id "authenticatorId",
      :is-user-verified "isUserVerified"})]
   (c/send-command
    connection
    payload__69753__auto__
    id__69750__auto__
    (fn*
     [p1__69749__69754__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__69752__auto__
       p1__69749__69754__auto__))))
   (let
    [result__69755__auto__ (clojure.core.async/<!! ch__69752__auto__)]
    (if-let
     [error__69756__auto__ (:error result__69755__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__69751__auto__
        ": "
        (:message error__69756__auto__))
       {:request payload__69753__auto__, :error error__69756__auto__}))
     (:result result__69755__auto__))))))

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
