(ns clj-chrome-devtools.commands.network
  "Network domain allows tracking network activities of the page. It exposes information about http,\nfile, data and other requests and responses, their headers, bodies, timing, etc."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::resource-type
 #{"Script" "Media" "WebSocket" "Preflight" "Manifest" "SignedExchange"
   "XHR" "Fetch" "Font" "TextTrack" "Ping" "Stylesheet" "Image"
   "Document" "EventSource" "CSPViolationReport" "Other"})

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
 #{"None" "Lax" "Strict"})

(s/def
 ::cookie-priority
 #{"Medium" "High" "Low"})

(s/def
 ::cookie-source-scheme
 #{"NonSecure" "Secure" "Unset"})

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
   ::worker-fetch-start
   ::worker-respond-with-settled
   ::send-start
   ::send-end
   ::push-start
   ::push-end
   ::receive-headers-end]))

(s/def
 ::resource-priority
 #{"Medium" "High" "VeryHigh" "Low" "VeryLow"})

(s/def
 ::post-data-entry
 (s/keys
  :opt-un
  [::bytes]))

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
   ::post-data-entries
   ::mixed-content-type
   ::is-link-preload
   ::trust-token-params
   ::is-same-site]))

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
 #{"csp" "origin" "content-type" "corp-not-same-site"
   "coop-sandboxed-iframe-cannot-navigate-to-coop-page"
   "corp-not-same-origin" "subresource-filter" "mixed-content"
   "corp-not-same-origin-after-defaulted-to-same-origin-by-coep"
   "other" "inspector" "coep-frame-resource-needs-coep-header"})

(s/def
 ::cors-error
 #{"PreflightInvalidStatus" "PreflightMultipleAllowOriginValues"
   "DisallowedByMode" "PreflightAllowOriginMismatch"
   "InvalidAllowCredentials" "PreflightInvalidAllowCredentials"
   "NoCorsRedirectModeNotFollow" "InvalidAllowOriginValue"
   "InvalidAllowHeadersPreflightResponse" "InvalidResponse"
   "AllowOriginMismatch" "MethodDisallowedByPreflightResponse"
   "PreflightInvalidAllowExternal" "PreflightDisallowedRedirect"
   "UnexpectedPrivateNetworkAccess" "MultipleAllowOriginValues"
   "InvalidPrivateNetworkAccess" "PreflightMissingAllowExternal"
   "PreflightInvalidAllowOriginValue" "MissingAllowOriginHeader"
   "RedirectContainsCredentials" "WildcardOriginNotAllowed"
   "PreflightInvalidAllowPrivateNetwork"
   "HeaderDisallowedByPreflightResponse" "InsecurePrivateNetwork"
   "PreflightMissingAllowOriginHeader"
   "PreflightWildcardOriginNotAllowed" "CorsDisabledScheme"
   "PreflightMissingAllowPrivateNetwork"
   "InvalidAllowMethodsPreflightResponse"})

(s/def
 ::cors-error-status
 (s/keys
  :req-un
  [::cors-error
   ::failed-parameter]))

(s/def
 ::service-worker-response-source
 #{"cache-storage" "fallback-code" "network" "http-cache"})

(s/def
 ::trust-token-params
 (s/keys
  :req-un
  [::type
   ::refresh-policy]
  :opt-un
  [::issuers]))

(s/def
 ::trust-token-operation-type
 #{"Signing" "Redemption" "Issuance"})

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
   ::service-worker-response-source
   ::response-time
   ::cache-storage-cache-name
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
   ::line-number
   ::column-number
   ::request-id]))

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
   ::session
   ::priority
   ::same-party
   ::source-scheme
   ::source-port]
  :opt-un
  [::same-site
   ::partition-key
   ::partition-key-opaque]))

(s/def
 :user/set-cookie-blocked-reason
 #{"UnknownError" "SchemeNotSupported" "SameSiteNoneInsecure"
   "SecureOnly" "NameValuePairExceedsMaxSize" "SyntaxError"
   "InvalidDomain" "SamePartyConflictsWithOtherAttributes"
   "OverwriteSecure" "SamePartyFromCrossPartyContext"
   "SchemefulSameSiteUnspecifiedTreatedAsLax"
   "SameSiteUnspecifiedTreatedAsLax" "UserPreferences" "InvalidPrefix"
   "SameSiteLax" "SchemefulSameSiteLax" "SameSiteStrict"
   "SchemefulSameSiteStrict"})

(s/def
 :user/cookie-blocked-reason
 #{"UnknownError" "SameSiteNoneInsecure" "SecureOnly"
   "NameValuePairExceedsMaxSize" "NotOnPath"
   "SamePartyFromCrossPartyContext"
   "SchemefulSameSiteUnspecifiedTreatedAsLax"
   "SameSiteUnspecifiedTreatedAsLax" "DomainMismatch" "UserPreferences"
   "SameSiteLax" "SchemefulSameSiteLax" "SameSiteStrict"
   "SchemefulSameSiteStrict"})

(s/def
 :user/blocked-set-cookie-with-reason
 (s/keys
  :req-un
  [:user/blocked-reasons :user/cookie-line]
  :opt-un
  [:user/cookie]))

(s/def
 :user/blocked-cookie-with-reason
 (s/keys :req-un [:user/blocked-reasons :user/cookie]))

(s/def
 :user/cookie-param
 (s/keys
  :req-un
  [:user/name :user/value]
  :opt-un
  [:user/url
   :user/domain
   :user/path
   :user/secure
   :user/http-only
   :user/same-site
   :user/expires
   :user/priority
   :user/same-party
   :user/source-scheme
   :user/source-port
   :user/partition-key]))

(s/def
 :user/auth-challenge
 (s/keys
  :req-un
  [:user/origin :user/scheme :user/realm]
  :opt-un
  [:user/source]))

(s/def
 :user/auth-challenge-response
 (s/keys
  :req-un
  [:user/response]
  :opt-un
  [:user/username :user/password]))

(s/def
 :user/interception-stage
 #{"Request" "HeadersReceived"})

(s/def
 :user/request-pattern
 (s/keys
  :opt-un
  [:user/url-pattern :user/resource-type :user/interception-stage]))

(s/def
 :user/signed-exchange-signature
 (s/keys
  :req-un
  [:user/label
   :user/signature
   :user/integrity
   :user/validity-url
   :user/date
   :user/expires]
  :opt-un
  [:user/cert-url :user/cert-sha256 :user/certificates]))

(s/def
 :user/signed-exchange-header
 (s/keys
  :req-un
  [:user/request-url
   :user/response-code
   :user/response-headers
   :user/signatures
   :user/header-integrity]))

(s/def
 :user/signed-exchange-error-field
 #{"signatureCertUrl" "signatureSig" "signatureCertSha256"
   "signatureValidityUrl" "signatureTimestamps" "signatureIntegrity"})

(s/def
 :user/signed-exchange-error
 (s/keys
  :req-un
  [:user/message]
  :opt-un
  [:user/signature-index :user/error-field]))

(s/def
 :user/signed-exchange-info
 (s/keys
  :req-un
  [:user/outer-response]
  :opt-un
  [:user/header :user/security-details :user/errors]))

(s/def :user/content-encoding #{"br" "gzip" "deflate"})

(s/def
 :user/private-network-request-policy
 #{"PreflightBlock" "Allow" "WarnFromInsecureToMorePrivate"
   "BlockFromInsecureToMorePrivate" "PreflightWarn"})

(s/def
 :user/ip-address-space
 #{"Private" "Public" "Unknown" "Local"})

(s/def
 :user/connect-timing
 (s/keys :req-un [:user/request-time]))

(s/def
 :user/client-security-state
 (s/keys
  :req-un
  [:user/initiator-is-secure-context
   :user/initiator-ip-address-space
   :user/private-network-request-policy]))

(s/def
 :user/cross-origin-opener-policy-value
 #{"SameOriginPlusCoep" "SameOriginAllowPopups"
   "SameOriginAllowPopupsPlusCoep" "SameOrigin" "UnsafeNone"})

(s/def
 :user/cross-origin-opener-policy-status
 (s/keys
  :req-un
  [:user/value :user/report-only-value]
  :opt-un
  [:user/reporting-endpoint :user/report-only-reporting-endpoint]))

(s/def
 :user/cross-origin-embedder-policy-value
 #{"None" "Credentialless" "RequireCorp"})

(s/def
 :user/cross-origin-embedder-policy-status
 (s/keys
  :req-un
  [:user/value :user/report-only-value]
  :opt-un
  [:user/reporting-endpoint :user/report-only-reporting-endpoint]))

(s/def
 :user/security-isolation-status
 (s/keys :opt-un [:user/coop :user/coep]))

(s/def
 :user/report-status
 #{"Success" "MarkedForRemoval" "Pending" "Queued"})

(s/def :user/report-id string?)

(s/def
 :user/reporting-api-report
 (s/keys
  :req-un
  [:user/id
   :user/initiator-url
   :user/destination
   :user/type
   :user/timestamp
   :user/depth
   :user/completed-attempts
   :user/body
   :user/status]))

(s/def
 :user/reporting-api-endpoint
 (s/keys :req-un [:user/url :user/group-name]))

(s/def
 :user/load-network-resource-page-result
 (s/keys
  :req-un
  [:user/success]
  :opt-un
  [:user/net-error
   :user/net-error-name
   :user/http-status-code
   :user/stream
   :user/headers]))

(s/def
 :user/load-network-resource-options
 (s/keys
  :req-un
  [:user/disable-cache :user/include-credentials]))
(defn
 set-accepted-encodings
 "Sets a list of content encodings that will be accepted. Empty list means no encoding is accepted.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :encodings | List of accepted content encodings."
 ([]
  (set-accepted-encodings
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [encodings]}]
  (set-accepted-encodings
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [encodings]}]
  (cmd/command
   connection
   "Network"
   "setAcceptedEncodings"
   params
   {:encodings "encodings"})))

(s/fdef
 set-accepted-encodings
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::encodings]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::encodings])))
 :ret
 (s/keys))

(defn
 clear-accepted-encodings-override
 "Clears accepted encodings set by setAcceptedEncodings"
 ([]
  (clear-accepted-encodings-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-accepted-encodings-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
   "clearAcceptedEncodingsOverride"
   params
   {})))

(s/fdef
 clear-accepted-encodings-override
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
 can-clear-browser-cache
 "Tells whether clearing browser cache is supported.\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | True if browser cache can be cleared."
 ([]
  (can-clear-browser-cache
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (can-clear-browser-cache
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
   "canClearBrowserCache"
   params
   {})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (can-clear-browser-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
   "canClearBrowserCookies"
   params
   {})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (can-emulate-network-conditions
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
   "canEmulateNetworkConditions"
   params
   {})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-browser-cache
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
   "clearBrowserCache"
   params
   {})))

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
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 clear-browser-cookies
 "Clears browser cookies."
 ([]
  (clear-browser-cookies
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-browser-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
   "clearBrowserCookies"
   params
   {})))

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
    c/connection?)
   :params
   (s/keys)))
 :ret
 (s/keys))

(defn
 continue-intercepted-request
 "Response to Network.requestIntercepted which either modifies the request to continue with any\nmodifications, or blocks it, or completes it with the provided response bytes. If a network\nfetch occurs as a result which encounters a redirect an additional Network.requestIntercepted\nevent will be sent with the same InterceptionId.\nDeprecated, use Fetch.continueRequest, Fetch.fulfillRequest and Fetch.failRequest instead.\n\nParameters map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :interception-id         | null\n  :error-reason            | If set this causes the request to fail with the given reason. Passing `Aborted` for requests\nmarked with `isNavigationRequest` also cancels the navigation. Must not be set in response\nto an authChallenge. (optional)\n  :raw-response            | If set the requests completes using with the provided base64 encoded raw response, including\nHTTP status line and headers etc... Must not be set in response to an authChallenge. (Encoded as a base64 string when passed over JSON) (optional)\n  :url                     | If set the request url will be modified in a way that's not observable by page. Must not be\nset in response to an authChallenge. (optional)\n  :method                  | If set this allows the request method to be overridden. Must not be set in response to an\nauthChallenge. (optional)\n  :post-data               | If set this allows postData to be set. Must not be set in response to an authChallenge. (optional)\n  :headers                 | If set this allows the request headers to be changed. Must not be set in response to an\nauthChallenge. (optional)\n  :auth-challenge-response | Response to a requestIntercepted with an authChallenge. Must not be set otherwise. (optional)"
 ([]
  (continue-intercepted-request
   (c/get-current-connection)
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
   (c/get-current-connection)
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
  (cmd/command
   connection
   "Network"
   "continueInterceptedRequest"
   params
   {:interception-id "interceptionId",
    :error-reason "errorReason",
    :raw-response "rawResponse",
    :url "url",
    :method "method",
    :post-data "postData",
    :headers "headers",
    :auth-challenge-response "authChallengeResponse"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [name url domain path]}]
  (delete-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [name url domain path]}]
  (cmd/command
   connection
   "Network"
   "deleteCookies"
   params
   {:name "name", :url "url", :domain "domain", :path "path"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (disable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
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
 emulate-network-conditions
 "Activates emulation of network conditions.\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :offline             | True to emulate internet disconnection.\n  :latency             | Minimum latency from request sent to response headers received (ms).\n  :download-throughput | Maximal aggregated download throughput (bytes/sec). -1 disables download throttling.\n  :upload-throughput   | Maximal aggregated upload throughput (bytes/sec).  -1 disables upload throttling.\n  :connection-type     | Connection type if known. (optional)"
 ([]
  (emulate-network-conditions
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [offline
     latency
     download-throughput
     upload-throughput
     connection-type]}]
  (emulate-network-conditions
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [offline
     latency
     download-throughput
     upload-throughput
     connection-type]}]
  (cmd/command
   connection
   "Network"
   "emulateNetworkConditions"
   params
   {:offline "offline",
    :latency "latency",
    :download-throughput "downloadThroughput",
    :upload-throughput "uploadThroughput",
    :connection-type "connectionType"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [max-total-buffer-size
     max-resource-buffer-size
     max-post-data-size]}]
  (enable
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [max-total-buffer-size
     max-resource-buffer-size
     max-post-data-size]}]
  (cmd/command
   connection
   "Network"
   "enable"
   params
   {:max-total-buffer-size "maxTotalBufferSize",
    :max-resource-buffer-size "maxResourceBufferSize",
    :max-post-data-size "maxPostDataSize"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-all-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Network"
   "getAllCookies"
   params
   {})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [origin]}]
  (get-certificate
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [origin]}]
  (cmd/command
   connection
   "Network"
   "getCertificate"
   params
   {:origin "origin"})))

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
    c/connection?)
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
 "Returns all browser cookies for the current URL. Depending on the backend support, will return\ndetailed cookie information in the `cookies` field.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :urls | The list of URLs for which applicable cookies will be fetched.\nIf not specified, it's assumed to be set to the list containing\nthe URLs of the page and all of its subframes. (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Array of cookie objects."
 ([]
  (get-cookies
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [urls]}]
  (get-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [urls]}]
  (cmd/command
   connection
   "Network"
   "getCookies"
   params
   {:urls "urls"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id]}]
  (get-response-body
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id]}]
  (cmd/command
   connection
   "Network"
   "getResponseBody"
   params
   {:request-id "requestId"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id]}]
  (get-request-post-data
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id]}]
  (cmd/command
   connection
   "Network"
   "getRequestPostData"
   params
   {:request-id "requestId"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [interception-id]}]
  (get-response-body-for-interception
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [interception-id]}]
  (cmd/command
   connection
   "Network"
   "getResponseBodyForInterception"
   params
   {:interception-id "interceptionId"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [interception-id]}]
  (take-response-body-for-interception-as-stream
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [interception-id]}]
  (cmd/command
   connection
   "Network"
   "takeResponseBodyForInterceptionAsStream"
   params
   {:interception-id "interceptionId"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id]}]
  (replay-xhr
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id]}]
  (cmd/command
   connection
   "Network"
   "replayXHR"
   params
   {:request-id "requestId"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id query case-sensitive is-regex]}]
  (search-in-response-body
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [request-id query case-sensitive is-regex]}]
  (cmd/command
   connection
   "Network"
   "searchInResponseBody"
   params
   {:request-id "requestId",
    :query "query",
    :case-sensitive "caseSensitive",
    :is-regex "isRegex"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [urls]}]
  (set-blocked-ur-ls
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [urls]}]
  (cmd/command
   connection
   "Network"
   "setBlockedURLs"
   params
   {:urls "urls"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [bypass]}]
  (set-bypass-service-worker
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [bypass]}]
  (cmd/command
   connection
   "Network"
   "setBypassServiceWorker"
   params
   {:bypass "bypass"})))

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
    c/connection?)
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
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cache-disabled]}]
  (set-cache-disabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [cache-disabled]}]
  (cmd/command
   connection
   "Network"
   "setCacheDisabled"
   params
   {:cache-disabled "cacheDisabled"})))

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
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cache-disabled])))
 :ret
 (s/keys))

(defn
 set-cookie
 "Sets a cookie with the given cookie data; may overwrite equivalent cookies if they exist.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :name          | Cookie name.\n  :value         | Cookie value.\n  :url           | The request-URI to associate with the setting of the cookie. This value can affect the\ndefault domain, path, source port, and source scheme values of the created cookie. (optional)\n  :domain        | Cookie domain. (optional)\n  :path          | Cookie path. (optional)\n  :secure        | True if cookie is secure. (optional)\n  :http-only     | True if cookie is http-only. (optional)\n  :same-site     | Cookie SameSite type. (optional)\n  :expires       | Cookie expiration date, session cookie if not set (optional)\n  :priority      | Cookie Priority type. (optional)\n  :same-party    | True if cookie is SameParty. (optional)\n  :source-scheme | Cookie source scheme type. (optional)\n  :source-port   | Cookie source port. Valid values are {-1, [1, 65535]}, -1 indicates an unspecified port.\nAn unspecified port value allows protocol clients to emulate legacy cookie scope for the port.\nThis is a temporary ability and it will be removed in the future. (optional)\n  :partition-key | Cookie partition key. The site of the top-level URL the browser was visiting at the start\nof the request to the endpoint that set the cookie.\nIf not set, the cookie will be set as not partitioned. (optional)\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :success | Always set to true. If an error occurs, the response indicates protocol error."
 ([]
  (set-cookie
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [name
     value
     url
     domain
     path
     secure
     http-only
     same-site
     expires
     priority
     same-party
     source-scheme
     source-port
     partition-key]}]
  (set-cookie
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [name
     value
     url
     domain
     path
     secure
     http-only
     same-site
     expires
     priority
     same-party
     source-scheme
     source-port
     partition-key]}]
  (cmd/command
   connection
   "Network"
   "setCookie"
   params
   {:path "path",
    :same-site "sameSite",
    :partition-key "partitionKey",
    :same-party "sameParty",
    :name "name",
    :value "value",
    :source-scheme "sourceScheme",
    :expires "expires",
    :priority "priority",
    :source-port "sourcePort",
    :url "url",
    :domain "domain",
    :secure "secure",
    :http-only "httpOnly"})))

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
     ::expires
     ::priority
     ::same-party
     ::source-scheme
     ::source-port
     ::partition-key]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
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
     ::expires
     ::priority
     ::same-party
     ::source-scheme
     ::source-port
     ::partition-key])))
 :ret
 (s/keys
  :req-un
  [::success]))

(defn
 set-cookies
 "Sets given cookies.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Cookies to be set."
 ([]
  (set-cookies
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cookies]}]
  (set-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [cookies]}]
  (cmd/command
   connection
   "Network"
   "setCookies"
   params
   {:cookies "cookies"})))

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
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cookies])))
 :ret
 (s/keys))

(defn
 set-extra-http-headers
 "Specifies whether to always send extra HTTP headers with the requests from this page.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :headers | Map with extra HTTP headers."
 ([]
  (set-extra-http-headers
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [headers]}]
  (set-extra-http-headers
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [headers]}]
  (cmd/command
   connection
   "Network"
   "setExtraHTTPHeaders"
   params
   {:headers "headers"})))

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
    c/connection?)
   :params
   (s/keys
    :req-un
    [::headers])))
 :ret
 (s/keys))

(defn
 set-attach-debug-stack
 "Specifies whether to attach a page script stack id in requests\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | Whether to attach a page script stack for debugging purpose."
 ([]
  (set-attach-debug-stack
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-attach-debug-stack
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (cmd/command
   connection
   "Network"
   "setAttachDebugStack"
   params
   {:enabled "enabled"})))

(s/fdef
 set-attach-debug-stack
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::enabled])))
 :ret
 (s/keys))

(defn
 set-request-interception
 "Sets the requests to intercept that match the provided patterns and optionally resource types.\nDeprecated, please use Fetch.enable instead.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :patterns | Requests matching any of these patterns will be forwarded and wait for the corresponding\ncontinueInterceptedRequest call."
 ([]
  (set-request-interception
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [patterns]}]
  (set-request-interception
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [patterns]}]
  (cmd/command
   connection
   "Network"
   "setRequestInterception"
   params
   {:patterns "patterns"})))

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
    c/connection?)
   :params
   (s/keys
    :req-un
    [::patterns])))
 :ret
 (s/keys))

(defn
 set-user-agent-override
 "Allows overriding user agent with the given string.\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :user-agent          | User agent to use.\n  :accept-language     | Browser langugage to emulate. (optional)\n  :platform            | The platform navigator.platform should return. (optional)\n  :user-agent-metadata | To be sent in Sec-CH-UA-* headers and returned in navigator.userAgentData (optional)"
 ([]
  (set-user-agent-override
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [user-agent accept-language platform user-agent-metadata]}]
  (set-user-agent-override
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [user-agent accept-language platform user-agent-metadata]}]
  (cmd/command
   connection
   "Network"
   "setUserAgentOverride"
   params
   {:user-agent "userAgent",
    :accept-language "acceptLanguage",
    :platform "platform",
    :user-agent-metadata "userAgentMetadata"})))

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
     ::platform
     ::user-agent-metadata]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::user-agent]
    :opt-un
    [::accept-language
     ::platform
     ::user-agent-metadata])))
 :ret
 (s/keys))

(defn
 get-security-isolation-status
 "Returns information about the COEP/COOP isolation status.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | If no frameId is provided, the status of the target is provided. (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :status | null"
 ([]
  (get-security-isolation-status
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id]}]
  (get-security-isolation-status
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id]}]
  (cmd/command
   connection
   "Network"
   "getSecurityIsolationStatus"
   params
   {:frame-id "frameId"})))

(s/fdef
 get-security-isolation-status
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::frame-id])))
 :ret
 (s/keys
  :req-un
  [::status]))

(defn
 enable-reporting-api
 "Enables tracking for the Reporting API, events generated by the Reporting API will now be delivered to the client.\nEnabling triggers 'reportingApiReportAdded' for all existing reports.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :enable | Whether to enable or disable events for the Reporting API"
 ([]
  (enable-reporting-api
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enable]}]
  (enable-reporting-api
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enable]}]
  (cmd/command
   connection
   "Network"
   "enableReportingApi"
   params
   {:enable "enable"})))

(s/fdef
 enable-reporting-api
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::enable]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::enable])))
 :ret
 (s/keys))

(defn
 load-network-resource
 "Fetches the resource and returns the content.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | Frame id to get the resource for. Mandatory for frame targets, and\nshould be omitted for worker targets. (optional)\n  :url      | URL of the resource to get content for.\n  :options  | Options for the request.\n\nReturn map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :resource | null"
 ([]
  (load-network-resource
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id url options]}]
  (load-network-resource
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id url options]}]
  (cmd/command
   connection
   "Network"
   "loadNetworkResource"
   params
   {:frame-id "frameId", :url "url", :options "options"})))

(s/fdef
 load-network-resource
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:user/url :user/options]
    :opt-un
    [:user/frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:user/url :user/options]
    :opt-un
    [:user/frame-id])))
 :ret
 (s/keys :req-un [:user/resource]))
