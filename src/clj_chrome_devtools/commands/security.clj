(ns clj-chrome-devtools.commands.security
  "Security"
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::certificate-id
 integer?)

(s/def
 ::mixed-content-type
 #{"none" "blockable" "optionally-blockable"})

(s/def
 ::security-state
 #{"neutral" "info" "insecure-broken" "secure" "unknown" "insecure"})

(s/def
 ::certificate-security-state
 (s/keys
  :req-un
  [::protocol
   ::key-exchange
   ::cipher
   ::certificate
   ::subject-name
   ::issuer
   ::valid-from
   ::valid-to
   ::certificate-has-weak-signature
   ::certificate-has-sha1-signature
   ::modern-ssl
   ::obsolete-ssl-protocol
   ::obsolete-ssl-key-exchange
   ::obsolete-ssl-cipher
   ::obsolete-ssl-signature]
  :opt-un
  [::key-exchange-group
   ::mac
   ::certificate-network-error]))

(s/def
 ::safety-tip-status
 #{"badReputation" "lookalike"})

(s/def
 ::safety-tip-info
 (s/keys
  :req-un
  [::safety-tip-status]
  :opt-un
  [::safe-url]))

(s/def
 ::visible-security-state
 (s/keys
  :req-un
  [::security-state
   ::security-state-issue-ids]
  :opt-un
  [::certificate-security-state
   ::safety-tip-info]))

(s/def
 ::security-state-explanation
 (s/keys
  :req-un
  [::security-state
   ::title
   ::summary
   ::description
   ::mixed-content-type
   ::certificate]
  :opt-un
  [::recommendations]))

(s/def
 ::insecure-content-status
 (s/keys
  :req-un
  [::ran-mixed-content
   ::displayed-mixed-content
   ::contained-mixed-form
   ::ran-content-with-cert-errors
   ::displayed-content-with-cert-errors
   ::ran-insecure-content-style
   ::displayed-insecure-content-style]))

(s/def
 ::certificate-error-action
 #{"cancel" "continue"})
(defn
 disable
 "Disables tracking security state changes."
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
   "Security"
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
 enable
 "Enables tracking security state changes."
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
   "Security"
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
 set-ignore-certificate-errors
 "Enable/disable whether all certificate errors should be ignored.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :ignore | If true, all certificate errors will be ignored."
 ([]
  (set-ignore-certificate-errors
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [ignore]}]
  (set-ignore-certificate-errors
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [ignore]}]
  (cmd/command
   connection
   "Security"
   "setIgnoreCertificateErrors"
   params
   {:ignore "ignore"})))

(s/fdef
 set-ignore-certificate-errors
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::ignore]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::ignore])))
 :ret
 (s/keys))

(defn
 handle-certificate-error
 "Handles a certificate error that fired a certificateError event.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :event-id | The ID of the event.\n  :action   | The action to take on the certificate error."
 ([]
  (handle-certificate-error
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [event-id action]}]
  (handle-certificate-error
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [event-id action]}]
  (cmd/command
   connection
   "Security"
   "handleCertificateError"
   params
   {:event-id "eventId", :action "action"})))

(s/fdef
 handle-certificate-error
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::event-id
     ::action]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::event-id
     ::action])))
 :ret
 (s/keys))

(defn
 set-override-certificate-errors
 "Enable/disable overriding certificate errors. If enabled, all certificate error events need to\nbe handled by the DevTools client and should be answered with `handleCertificateError` commands.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :override | If true, certificate errors will be overridden."
 ([]
  (set-override-certificate-errors
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [override]}]
  (set-override-certificate-errors
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [override]}]
  (cmd/command
   connection
   "Security"
   "setOverrideCertificateErrors"
   params
   {:override "override"})))

(s/fdef
 set-override-certificate-errors
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::override]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::override])))
 :ret
 (s/keys))
