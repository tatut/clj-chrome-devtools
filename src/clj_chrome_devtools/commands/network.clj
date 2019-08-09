(ns clj-chrome-devtools.commands.network
  "Network domain allows tracking network activities of the page. It exposes information about http,\nfile, data and other requests and responses, their headers, bodies, timing, etc."
  (:require [clojure.spec.alpha :as s]))
(s/def
 ::resource-type
 #{"Script" "Media" "WebSocket" "Manifest" "SignedExchange" "XHR"
   "Fetch" "Font" "TextTrack" "Ping" "Stylesheet" "Image" "Document"
   "EventSource" "CSPViolationReport" "Other"})

(s/def
 ::loader-id
 string?)

(s/def
 ::request-id
 string?)

(s/def
 ::interception-id
 string?)

(s/def
 ::error-reason
 #{"ConnectionFailed" "Failed" "AccessDenied" "BlockedByClient"
   "ConnectionAborted" "AddressUnreachable" "TimedOut"
   "BlockedByResponse" "NameNotResolved" "ConnectionReset" "Aborted"
   "ConnectionRefused" "ConnectionClosed" "InternetDisconnected"})

(s/def
 ::time-since-epoch
 number?)

(s/def
 ::monotonic-time
 number?)

(s/def
 ::headers
 (s/keys))

(s/def
 ::connection-type
 #{"none" "wimax" "wifi" "cellular3g" "other" "ethernet" "cellular2g"
   "cellular4g" "bluetooth"})

(s/def
 ::cookie-same-site
 #{"None" "Lax" "Extended" "Strict"})

(s/def
 ::resource-timing
 (s/keys
  :req-un
  [::request-time
   ::proxy-start
   ::proxy-end
   ::dns-start
   ::dns-end
   ::connect-start
   ::connect-end
   ::ssl-start
   ::ssl-end
   ::worker-start
   ::worker-ready
   ::send-start
   ::send-end
   ::push-start
   ::push-end
   ::receive-headers-end]))

(s/def
 ::resource-priority
 #{"Medium" "High" "VeryHigh" "Low" "VeryLow"})

(s/def
 ::request
 (s/keys
  :req-un
  [::url
   ::method
   ::headers
   ::initial-priority
   ::referrer-policy]
  :opt-un
  [::url-fragment
   ::post-data
   ::has-post-data
   ::mixed-content-type
   ::is-link-preload]))

(s/def
 ::signed-certificate-timestamp
 (s/keys
  :req-un
  [::status
   ::origin
   ::log-description
   ::log-id
   ::timestamp
   ::hash-algorithm
   ::signature-algorithm
   ::signature-data]))

(s/def
 ::security-details
 (s/keys
  :req-un
  [::protocol
   ::key-exchange
   ::cipher
   ::certificate-id
   ::subject-name
   ::san-list
   ::issuer
   ::valid-from
   ::valid-to
   ::signed-certificate-timestamp-list
   ::certificate-transparency-compliance]
  :opt-un
  [::key-exchange-group
   ::mac]))

(s/def
 ::certificate-transparency-compliance
 #{"not-compliant" "unknown" "compliant"})

(s/def
 ::blocked-reason
 #{"csp" "origin" "content-type" "collapsed-by-client"
   "subresource-filter" "mixed-content" "other" "inspector"})

(s/def
 ::response
 (s/keys
  :req-un
  [::url
   ::status
   ::status-text
   ::headers
   ::mime-type
   ::connection-reused
   ::connection-id
   ::encoded-data-length
   ::security-state]
  :opt-un
  [::headers-text
   ::request-headers
   ::request-headers-text
   ::remote-ip-address
   ::remote-port
   ::from-disk-cache
   ::from-service-worker
   ::from-prefetch-cache
   ::timing
   ::protocol
   ::security-details]))

(s/def
 ::web-socket-request
 (s/keys
  :req-un
  [::headers]))

(s/def
 ::web-socket-response
 (s/keys
  :req-un
  [::status
   ::status-text
   ::headers]
  :opt-un
  [::headers-text
   ::request-headers
   ::request-headers-text]))

(s/def
 ::web-socket-frame
 (s/keys
  :req-un
  [::opcode
   ::mask
   ::payload-data]))

(s/def
 ::cached-resource
 (s/keys
  :req-un
  [::url
   ::type
   ::body-size]
  :opt-un
  [::response]))

(s/def
 ::initiator
 (s/keys
  :req-un
  [::type]
  :opt-un
  [::stack
   ::url
   ::line-number]))

(s/def
 ::cookie
 (s/keys
  :req-un
  [::name
   ::value
   ::domain
   ::path
   ::expires
   ::size
   ::http-only
   ::secure
   ::session]
  :opt-un
  [::same-site]))

(s/def
 ::set-cookie-blocked-reason
 #{"UnknownError" "SchemeNotSupported" "SameSiteNoneInsecure"
   "SecureOnly" "SameSiteExtended" "SyntaxError" "InvalidDomain"
   "OverwriteSecure" "SameSiteUnspecifiedTreatedAsLax"
   "UserPreferences" "InvalidPrefix" "SameSiteLax" "SameSiteStrict"})

(s/def
 ::cookie-blocked-reason
 #{"UnknownError" "SameSiteNoneInsecure" "SecureOnly"
   "SameSiteExtended" "NotOnPath" "SameSiteUnspecifiedTreatedAsLax"
   "DomainMismatch" "UserPreferences" "SameSiteLax" "SameSiteStrict"})

(s/def
 ::blocked-set-cookie-with-reason
 (s/keys
  :req-un
  [::blocked-reason
   ::cookie-line]
  :opt-un
  [::cookie]))

(s/def
 ::blocked-cookie-with-reason
 (s/keys
  :req-un
  [::blocked-reason
   ::cookie]))

(s/def
 ::cookie-param
 (s/keys
  :req-un
  [::name
   ::value]
  :opt-un
  [::url
   ::domain
   ::path
   ::secure
   ::http-only
   ::same-site
   ::expires]))

(s/def
 ::auth-challenge
 (s/keys
  :req-un
  [::origin
   ::scheme
   ::realm]
  :opt-un
  [::source]))

(s/def
 ::auth-challenge-response
 (s/keys
  :req-un
  [::response]
  :opt-un
  [::username
   ::password]))

(s/def
 ::interception-stage
 #{"Request" "HeadersReceived"})

(s/def
 :clj-chrome-devtools.impl.define/request-pattern
 (s/keys
  :opt-un
  [:clj-chrome-devtools.impl.define/url-pattern
   :clj-chrome-devtools.impl.define/resource-type
   :clj-chrome-devtools.impl.define/interception-stage]))

(s/def
 :clj-chrome-devtools.impl.define/signed-exchange-signature
 (s/keys
  :req-un
  [:clj-chrome-devtools.impl.define/label
   :clj-chrome-devtools.impl.define/signature
   :clj-chrome-devtools.impl.define/integrity
   :clj-chrome-devtools.impl.define/validity-url
   :clj-chrome-devtools.impl.define/date
   :clj-chrome-devtools.impl.define/expires]
  :opt-un
  [:clj-chrome-devtools.impl.define/cert-url
   :clj-chrome-devtools.impl.define/cert-sha256
   :clj-chrome-devtools.impl.define/certificates]))

(s/def
 :clj-chrome-devtools.impl.define/signed-exchange-header
 (s/keys
  :req-un
  [:clj-chrome-devtools.impl.define/request-url
   :clj-chrome-devtools.impl.define/response-code
   :clj-chrome-devtools.impl.define/response-headers
   :clj-chrome-devtools.impl.define/signatures
   :clj-chrome-devtools.impl.define/header-integrity]))

(s/def
 :clj-chrome-devtools.impl.define/signed-exchange-error-field
 #{"signatureCertUrl" "signatureSig" "signatureCertSha256"
   "signatureValidityUrl" "signatureTimestamps" "signatureIntegrity"})

(s/def
 :clj-chrome-devtools.impl.define/signed-exchange-error
 (s/keys
  :req-un
  [:clj-chrome-devtools.impl.define/message]
  :opt-un
  [:clj-chrome-devtools.impl.define/signature-index
   :clj-chrome-devtools.impl.define/error-field]))

(s/def
 :clj-chrome-devtools.impl.define/signed-exchange-info
 (s/keys
  :req-un
  [:clj-chrome-devtools.impl.define/outer-response]
  :opt-un
  [:clj-chrome-devtools.impl.define/header
   :clj-chrome-devtools.impl.define/security-details
   :clj-chrome-devtools.impl.define/errors]))
(defn
 can-clear-browser-cache
 "Tells whether clearing browser cache is supported.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | True if browser cache can be cleared."
 ([]
  (can-clear-browser-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (can-clear-browser-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.canClearBrowserCache"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 can-clear-browser-cache
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 can-clear-browser-cookies
 "Tells whether clearing browser cookies is supported.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | True if browser cookies can be cleared."
 ([]
  (can-clear-browser-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (can-clear-browser-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.canClearBrowserCookies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 can-clear-browser-cookies
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 can-emulate-network-conditions
 "Tells whether emulation of network conditions is supported.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | True if emulation of network conditions is supported."
 ([]
  (can-emulate-network-conditions
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (can-emulate-network-conditions
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.canEmulateNetworkConditions"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 can-emulate-network-conditions
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 clear-browser-cache
 "Clears browser cache."
 ([]
  (clear-browser-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-browser-cache
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.clearBrowserCache"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 clear-browser-cache
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 clear-browser-cookies
 "Clears browser cookies."
 ([]
  (clear-browser-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-browser-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.clearBrowserCookies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 clear-browser-cookies
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 continue-intercepted-request
 "Response to Network.requestIntercepted which either modifies the request to continue with any\nmodifications, or blocks it, or completes it with the provided response bytes. If a network\nfetch occurs as a result which encounters a redirect an additional Network.requestIntercepted\nevent will be sent with the same InterceptionId.\nDeprecated, use Fetch.continueRequest, Fetch.fulfillRequest and Fetch.failRequest instead.\n\nParameters map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :interception-id         | null\n  :error-reason            | If set this causes the request to fail with the given reason. Passing `Aborted` for requests\nmarked with `isNavigationRequest` also cancels the navigation. Must not be set in response\nto an authChallenge. (optional)\n  :raw-response            | If set the requests completes using with the provided base64 encoded raw response, including\nHTTP status line and headers etc... Must not be set in response to an authChallenge. (optional)\n  :url                     | If set the request url will be modified in a way that's not observable by page. Must not be\nset in response to an authChallenge. (optional)\n  :method                  | If set this allows the request method to be overridden. Must not be set in response to an\nauthChallenge. (optional)\n  :post-data               | If set this allows postData to be set. Must not be set in response to an authChallenge. (optional)\n  :headers                 | If set this allows the request headers to be changed. Must not be set in response to an\nauthChallenge. (optional)\n  :auth-challenge-response | Response to a requestIntercepted with an authChallenge. Must not be set otherwise. (optional)"
 ([]
  (continue-intercepted-request
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [interception-id
     error-reason
     raw-response
     url
     method
     post-data
     headers
     auth-challenge-response]}]
  (continue-intercepted-request
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [interception-id
     error-reason
     raw-response
     url
     method
     post-data
     headers
     auth-challenge-response]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.continueInterceptedRequest"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:interception-id "interceptionId",
      :error-reason "errorReason",
      :raw-response "rawResponse",
      :url "url",
      :method "method",
      :post-data "postData",
      :headers "headers",
      :auth-challenge-response "authChallengeResponse"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 continue-intercepted-request
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::interception-id]
    :opt-un
    [::error-reason
     ::raw-response
     ::url
     ::method
     ::post-data
     ::headers
     ::auth-challenge-response]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::interception-id]
    :opt-un
    [::error-reason
     ::raw-response
     ::url
     ::method
     ::post-data
     ::headers
     ::auth-challenge-response])))
 :ret
 (s/keys))

(defn
 delete-cookies
 "Deletes browser cookies with matching name and url or domain/path pair.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :name   | Name of the cookies to remove.\n  :url    | If specified, deletes all the cookies with the given name where domain and path match\nprovided URL. (optional)\n  :domain | If specified, deletes only cookies with the exact domain. (optional)\n  :path   | If specified, deletes only cookies with the exact path. (optional)"
 ([]
  (delete-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [name url domain path]}]
  (delete-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [name url domain path]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.deleteCookies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:name "name", :url "url", :domain "domain", :path "path"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 delete-cookies
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::name]
    :opt-un
    [::url
     ::domain
     ::path]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::name]
    :opt-un
    [::url
     ::domain
     ::path])))
 :ret
 (s/keys))

(defn
 disable
 "Disables network tracking, prevents network events from being sent to the client."
 ([]
  (disable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (disable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.disable"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 emulate-network-conditions
 "Activates emulation of network conditions.\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :offline             | True to emulate internet disconnection.\n  :latency             | Minimum latency from request sent to response headers received (ms).\n  :download-throughput | Maximal aggregated download throughput (bytes/sec). -1 disables download throttling.\n  :upload-throughput   | Maximal aggregated upload throughput (bytes/sec).  -1 disables upload throttling.\n  :connection-type     | Connection type if known. (optional)"
 ([]
  (emulate-network-conditions
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [offline
     latency
     download-throughput
     upload-throughput
     connection-type]}]
  (emulate-network-conditions
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [offline
     latency
     download-throughput
     upload-throughput
     connection-type]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.emulateNetworkConditions"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:offline "offline",
      :latency "latency",
      :download-throughput "downloadThroughput",
      :upload-throughput "uploadThroughput",
      :connection-type "connectionType"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 emulate-network-conditions
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::offline
     ::latency
     ::download-throughput
     ::upload-throughput]
    :opt-un
    [::connection-type]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::offline
     ::latency
     ::download-throughput
     ::upload-throughput]
    :opt-un
    [::connection-type])))
 :ret
 (s/keys))

(defn
 enable
 "Enables network tracking, network events will now be delivered to the client.\n\nParameters map keys:\n\n\n  Key                       | Description \n  --------------------------|------------ \n  :max-total-buffer-size    | Buffer size in bytes to use when preserving network payloads (XHRs, etc). (optional)\n  :max-resource-buffer-size | Per-resource buffer size in bytes to use when preserving network payloads (XHRs, etc). (optional)\n  :max-post-data-size       | Longest post body size (in bytes) that would be included in requestWillBeSent notification (optional)"
 ([]
  (enable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [max-total-buffer-size
     max-resource-buffer-size
     max-post-data-size]}]
  (enable
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [max-total-buffer-size
     max-resource-buffer-size
     max-post-data-size]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.enable"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:max-total-buffer-size "maxTotalBufferSize",
      :max-resource-buffer-size "maxResourceBufferSize",
      :max-post-data-size "maxPostDataSize"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 enable
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::max-total-buffer-size
     ::max-resource-buffer-size
     ::max-post-data-size]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [::max-total-buffer-size
     ::max-resource-buffer-size
     ::max-post-data-size])))
 :ret
 (s/keys))

(defn
 get-all-cookies
 "Returns all browser cookies. Depending on the backend support, will return detailed cookie\ninformation in the `cookies` field.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Array of cookie objects."
 ([]
  (get-all-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-all-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.getAllCookies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 get-all-cookies
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
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys
  :req-un
  [::cookies]))

(defn
 get-certificate
 "Returns the DER-encoded certificate.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :origin | Origin to get certificate for.\n\nReturn map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :table-names | null"
 ([]
  (get-certificate
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (get-certificate
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.getCertificate"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:origin "origin"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 get-certificate
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::origin]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::origin])))
 :ret
 (s/keys
  :req-un
  [::table-names]))

(defn
 get-cookies
 "Returns all browser cookies for the current URL. Depending on the backend support, will return\ndetailed cookie information in the `cookies` field.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :urls | The list of URLs for which applicable cookies will be fetched (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Array of cookie objects."
 ([]
  (get-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [urls]}]
  (get-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [urls]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.getCookies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:urls "urls"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 get-cookies
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::urls]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :opt-un
    [::urls])))
 :ret
 (s/keys
  :req-un
  [::cookies]))

(defn
 get-response-body
 "Returns content served for the given request.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | Identifier of the network request to get content for.\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :body           | Response body.\n  :base64-encoded | True, if content was sent as base64."
 ([]
  (get-response-body
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [request-id]}]
  (get-response-body
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.getResponseBody"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:request-id "requestId"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 get-response-body
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::request-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::request-id])))
 :ret
 (s/keys
  :req-un
  [::body
   ::base64-encoded]))

(defn
 get-request-post-data
 "Returns post data sent with the request. Returns an error when no data was sent with the request.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | Identifier of the network request to get content for.\n\nReturn map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :post-data | Request body string, omitting files from multipart requests"
 ([]
  (get-request-post-data
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [request-id]}]
  (get-request-post-data
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.getRequestPostData"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:request-id "requestId"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 get-request-post-data
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::request-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::request-id])))
 :ret
 (s/keys
  :req-un
  [::post-data]))

(defn
 get-response-body-for-interception
 "Returns content served for the given currently intercepted request.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :interception-id | Identifier for the intercepted request to get body for.\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :body           | Response body.\n  :base64-encoded | True, if content was sent as base64."
 ([]
  (get-response-body-for-interception
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [interception-id]}]
  (get-response-body-for-interception
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [interception-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.getResponseBodyForInterception"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:interception-id "interceptionId"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 get-response-body-for-interception
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::interception-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::interception-id])))
 :ret
 (s/keys
  :req-un
  [::body
   ::base64-encoded]))

(defn
 take-response-body-for-interception-as-stream
 "Returns a handle to the stream representing the response body. Note that after this command,\nthe intercepted request can't be continued as is -- you either need to cancel it or to provide\nthe response body. The stream only supports sequential read, IO.read will fail if the position\nis specified.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :interception-id | null\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :stream | null"
 ([]
  (take-response-body-for-interception-as-stream
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [interception-id]}]
  (take-response-body-for-interception-as-stream
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [interception-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.takeResponseBodyForInterceptionAsStream"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:interception-id "interceptionId"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 take-response-body-for-interception-as-stream
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::interception-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::interception-id])))
 :ret
 (s/keys
  :req-un
  [::stream]))

(defn
 replay-xhr
 "This method sends a new XMLHttpRequest which is identical to the original one. The following\nparameters should be identical: method, url, async, request body, extra headers, withCredentials\nattribute, user, password.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | Identifier of XHR to replay."
 ([]
  (replay-xhr
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [request-id]}]
  (replay-xhr
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.replayXHR"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:request-id "requestId"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 replay-xhr
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::request-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::request-id])))
 :ret
 (s/keys))

(defn
 search-in-response-body
 "Searches for given string in response content.\n\nParameters map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :request-id     | Identifier of the network response to search.\n  :query          | String to search for.\n  :case-sensitive | If true, search is case sensitive. (optional)\n  :is-regex       | If true, treats string parameter as regex. (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | List of search matches."
 ([]
  (search-in-response-body
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [request-id query case-sensitive is-regex]}]
  (search-in-response-body
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [request-id query case-sensitive is-regex]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.searchInResponseBody"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:request-id "requestId",
      :query "query",
      :case-sensitive "caseSensitive",
      :is-regex "isRegex"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 search-in-response-body
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::request-id
     ::query]
    :opt-un
    [::case-sensitive
     ::is-regex]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::request-id
     ::query]
    :opt-un
    [::case-sensitive
     ::is-regex])))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 set-blocked-ur-ls
 "Blocks URLs from loading.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :urls | URL patterns to block. Wildcards ('*') are allowed."
 ([]
  (set-blocked-ur-ls
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [urls]}]
  (set-blocked-ur-ls
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [urls]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setBlockedURLs"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:urls "urls"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-blocked-ur-ls
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::urls]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::urls])))
 :ret
 (s/keys))

(defn
 set-bypass-service-worker
 "Toggles ignoring of service worker for each request.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :bypass | Bypass service worker and load from network."
 ([]
  (set-bypass-service-worker
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [bypass]}]
  (set-bypass-service-worker
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [bypass]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setBypassServiceWorker"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:bypass "bypass"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-bypass-service-worker
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::bypass]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::bypass])))
 :ret
 (s/keys))

(defn
 set-cache-disabled
 "Toggles ignoring cache for each request. If `true`, cache will not be used.\n\nParameters map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :cache-disabled | Cache disabled state."
 ([]
  (set-cache-disabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [cache-disabled]}]
  (set-cache-disabled
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [cache-disabled]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setCacheDisabled"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:cache-disabled "cacheDisabled"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-cache-disabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cache-disabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::cache-disabled])))
 :ret
 (s/keys))

(defn
 set-cookie
 "Sets a cookie with the given cookie data; may overwrite equivalent cookies if they exist.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :name      | Cookie name.\n  :value     | Cookie value.\n  :url       | The request-URI to associate with the setting of the cookie. This value can affect the\ndefault domain and path values of the created cookie. (optional)\n  :domain    | Cookie domain. (optional)\n  :path      | Cookie path. (optional)\n  :secure    | True if cookie is secure. (optional)\n  :http-only | True if cookie is http-only. (optional)\n  :same-site | Cookie SameSite type. (optional)\n  :expires   | Cookie expiration date, session cookie if not set (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :success | True if successfully set cookie."
 ([]
  (set-cookie
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [name value url domain path secure http-only same-site expires]}]
  (set-cookie
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [name value url domain path secure http-only same-site expires]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setCookie"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:path "path",
      :same-site "sameSite",
      :name "name",
      :value "value",
      :expires "expires",
      :url "url",
      :domain "domain",
      :secure "secure",
      :http-only "httpOnly"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-cookie
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::name
     ::value]
    :opt-un
    [::url
     ::domain
     ::path
     ::secure
     ::http-only
     ::same-site
     ::expires]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::name
     ::value]
    :opt-un
    [::url
     ::domain
     ::path
     ::secure
     ::http-only
     ::same-site
     ::expires])))
 :ret
 (s/keys
  :req-un
  [::success]))

(defn
 set-cookies
 "Sets given cookies.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Cookies to be set."
 ([]
  (set-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [cookies]}]
  (set-cookies
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [cookies]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setCookies"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:cookies "cookies"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-cookies
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cookies]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::cookies])))
 :ret
 (s/keys))

(defn
 set-data-size-limits-for-test
 "For testing.\n\nParameters map keys:\n\n\n  Key                | Description \n  -------------------|------------ \n  :max-total-size    | Maximum total buffer size.\n  :max-resource-size | Maximum per-resource size."
 ([]
  (set-data-size-limits-for-test
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [max-total-size max-resource-size]}]
  (set-data-size-limits-for-test
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [max-total-size max-resource-size]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setDataSizeLimitsForTest"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:max-total-size "maxTotalSize",
      :max-resource-size "maxResourceSize"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-data-size-limits-for-test
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::max-total-size
     ::max-resource-size]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::max-total-size
     ::max-resource-size])))
 :ret
 (s/keys))

(defn
 set-extra-http-headers
 "Specifies whether to always send extra HTTP headers with the requests from this page.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :headers | Map with extra HTTP headers."
 ([]
  (set-extra-http-headers
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [headers]}]
  (set-extra-http-headers
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [headers]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setExtraHTTPHeaders"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:headers "headers"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-extra-http-headers
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::headers]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::headers])))
 :ret
 (s/keys))

(defn
 set-request-interception
 "Sets the requests to intercept that match the provided patterns and optionally resource types.\nDeprecated, please use Fetch.enable instead.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :patterns | Requests matching any of these patterns will be forwarded and wait for the corresponding\ncontinueInterceptedRequest call."
 ([]
  (set-request-interception
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [patterns]}]
  (set-request-interception
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection {:as params, :keys [patterns]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setRequestInterception"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:patterns "patterns"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-request-interception
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::patterns]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::patterns])))
 :ret
 (s/keys))

(defn
 set-user-agent-override
 "Allows overriding user agent with the given string.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :user-agent      | User agent to use.\n  :accept-language | Browser langugage to emulate. (optional)\n  :platform        | The platform navigator.platform should return. (optional)"
 ([]
  (set-user-agent-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [user-agent accept-language platform]}]
  (set-user-agent-override
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [user-agent accept-language platform]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Network.setUserAgentOverride"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:user-agent "userAgent",
      :accept-language "acceptLanguage",
      :platform "platform"})]
   (clj-chrome-devtools.impl.connection/send-command
    connection
    payload__36881__auto__
    id__36878__auto__
    (fn*
     [p1__36877__36882__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__36880__auto__
       p1__36877__36882__auto__))))
   (let
    [result__36883__auto__ (clojure.core.async/<!! ch__36880__auto__)]
    (if-let
     [error__36884__auto__ (:error result__36883__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__36879__auto__
        ": "
        (:message error__36884__auto__))
       {:request payload__36881__auto__, :error error__36884__auto__}))
     (:result result__36883__auto__))))))

(s/fdef
 set-user-agent-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::user-agent]
    :opt-un
    [::accept-language
     ::platform]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    clj-chrome-devtools.impl.connection/connection?)
   :params
   (s/keys
    :req-un
    [::user-agent]
    :opt-un
    [::accept-language
     ::platform])))
 :ret
 (s/keys))
