(ns clj-chrome-devtools.commands.audits
  "Audits domain allows investigation of page violations and possible improvements."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::affected-cookie
 (s/keys
  :req-un
  [::name
   ::path
   ::domain]))

(s/def
 ::affected-request
 (s/keys
  :req-un
  [::request-id]
  :opt-un
  [::url]))

(s/def
 ::affected-frame
 (s/keys
  :req-un
  [::frame-id]))

(s/def
 ::cookie-exclusion-reason
 #{"ExcludeSameSiteLax" "ExcludeSameSiteStrict"
   "ExcludeSameSiteNoneInsecure"
   "ExcludeSameSiteUnspecifiedTreatedAsLax"
   "ExcludeSamePartyCrossPartyContext" "ExcludeInvalidSameParty"})

(s/def
 ::cookie-warning-reason
 #{"WarnAttributeValueExceedsMaxSize"
   "WarnSameSiteUnspecifiedCrossSiteContext"
   "WarnSameSiteStrictCrossDowngradeLax"
   "WarnSameSiteLaxCrossDowngradeLax"
   "WarnSameSiteStrictLaxDowngradeStrict"
   "WarnSameSiteStrictCrossDowngradeStrict"
   "WarnSameSiteLaxCrossDowngradeStrict" "WarnSameSiteNoneInsecure"
   "WarnSameSiteUnspecifiedLaxAllowUnsafe"})

(s/def
 ::cookie-operation
 #{"ReadCookie" "SetCookie"})

(s/def
 ::cookie-issue-details
 (s/keys
  :req-un
  [::cookie-warning-reasons
   ::cookie-exclusion-reasons
   ::operation]
  :opt-un
  [::cookie
   ::raw-cookie-line
   ::site-for-cookies
   ::cookie-url
   ::request]))

(s/def
 ::mixed-content-resolution-status
 #{"MixedContentBlocked" "MixedContentAutomaticallyUpgraded"
   "MixedContentWarning"})

(s/def
 ::mixed-content-resource-type
 #{"XSLT" "Script" "XMLHttpRequest" "Video" "Audio" "Prefetch"
   "AttributionSrc" "Manifest" "ServiceWorker" "PluginResource" "Form"
   "SharedWorker" "Download" "Font" "Import" "Favicon" "Worker" "Track"
   "Beacon" "Frame" "CSPReport" "Ping" "Resource" "Stylesheet" "Image"
   "EventSource" "PluginData"})

(s/def
 ::mixed-content-issue-details
 (s/keys
  :req-un
  [::resolution-status
   ::insecure-url
   ::main-resource-url]
  :opt-un
  [::resource-type
   ::request
   ::frame]))

(s/def
 ::blocked-by-response-reason
 #{"CoepFrameResourceNeedsCoepHeader" "CorpNotSameSite"
   "CorpNotSameOriginAfterDefaultedToSameOriginByCoep"
   "CoopSandboxedIFrameCannotNavigateToCoopPage" "CorpNotSameOrigin"})

(s/def
 ::blocked-by-response-issue-details
 (s/keys
  :req-un
  [::request
   ::reason]
  :opt-un
  [::parent-frame
   ::blocked-frame]))

(s/def
 ::heavy-ad-resolution-status
 #{"HeavyAdWarning" "HeavyAdBlocked"})

(s/def
 ::heavy-ad-reason
 #{"CpuTotalLimit" "CpuPeakLimit" "NetworkTotalLimit"})

(s/def
 ::heavy-ad-issue-details
 (s/keys
  :req-un
  [::resolution
   ::reason
   ::frame]))

(s/def
 ::content-security-policy-violation-type
 #{"kURLViolation" "kWasmEvalViolation" "kTrustedTypesPolicyViolation"
   "kTrustedTypesSinkViolation" "kEvalViolation" "kInlineViolation"})

(s/def
 ::source-code-location
 (s/keys
  :req-un
  [::url
   ::line-number
   ::column-number]
  :opt-un
  [::script-id]))

(s/def
 ::content-security-policy-issue-details
 (s/keys
  :req-un
  [::violated-directive
   ::is-report-only
   ::content-security-policy-violation-type]
  :opt-un
  [::blocked-url
   ::frame-ancestor
   ::source-code-location
   ::violating-node-id]))

(s/def
 ::shared-array-buffer-issue-type
 #{"CreationIssue" "TransferIssue"})

(s/def
 ::shared-array-buffer-issue-details
 (s/keys
  :req-un
  [::source-code-location
   ::is-warning
   ::type]))

(s/def
 ::twa-quality-enforcement-violation-type
 #{"kHttpError" "kDigitalAssetLinks" "kUnavailableOffline"})

(s/def
 ::trusted-web-activity-issue-details
 (s/keys
  :req-un
  [::url
   ::violation-type]
  :opt-un
  [::http-status-code
   ::package-name
   ::signature]))

(s/def
 ::low-text-contrast-issue-details
 (s/keys
  :req-un
  [::violating-node-id
   ::violating-node-selector
   ::contrast-ratio
   ::threshold-aa
   ::threshold-aaa
   ::font-size
   ::font-weight]))

(s/def
 ::cors-issue-details
 (s/keys
  :req-un
  [::cors-error-status
   ::is-warning
   ::request]
  :opt-un
  [::location
   ::initiator-origin
   ::resource-ip-address-space
   ::client-security-state]))

(s/def
 ::attribution-reporting-issue-type
 #{"InvalidAttributionSourceExpiry"
   "AttributionEventSourceTriggerDataTooLarge"
   "InvalidAttributionSourcePriority" "InvalidEventSourceTriggerData"
   "AttributionTriggerDataTooLarge" "PermissionPolicyDisabled"
   "AttributionUntrustworthyOrigin"
   "AttributionSourceUntrustworthyOrigin" "InvalidAttributionData"
   "InvalidTriggerDedupKey" "InvalidAttributionSourceEventId"
   "InvalidTriggerPriority"})

(s/def
 ::attribution-reporting-issue-details
 (s/keys
  :req-un
  [::violation-type]
  :opt-un
  [::frame
   ::request
   ::violating-node-id
   ::invalid-parameter]))

(s/def
 ::quirks-mode-issue-details
 (s/keys
  :req-un
  [::is-limited-quirks-mode
   ::document-node-id
   ::url
   ::frame-id
   ::loader-id]))

(s/def
 ::navigator-user-agent-issue-details
 (s/keys
  :req-un
  [::url]
  :opt-un
  [::location]))

(s/def
 ::generic-issue-error-type
 #{"CrossOriginPortalPostMessageError"})

(s/def
 ::generic-issue-details
 (s/keys
  :req-un
  [::error-type]
  :opt-un
  [::frame-id]))

(s/def
 ::deprecation-issue-details
 (s/keys
  :req-un
  [::source-code-location
   ::deprecation-type]
  :opt-un
  [::affected-frame
   ::message]))

(s/def
 ::client-hint-issue-reason
 #{"MetaTagModifiedHTML" "MetaTagAllowListInvalidOrigin"})

(s/def
 :user/federated-auth-request-issue-details
 (s/keys
  :req-un
  [:user/federated-auth-request-issue-reason]))

(s/def
 :user/federated-auth-request-issue-reason
 #{"ApprovalDeclined" "IdTokenNoResponse" "IdTokenInvalidRequest"
   "DisabledInSettings" "InvalidSigninResponse" "ErrorFetchingSignin"
   "ManifestInvalidResponse" "ManifestHttpNotFound"
   "ClientMetadataMissingPrivacyPolicyUrl" "ClientMetadataNoResponse"
   "Canceled" "ManifestNoResponse" "AccountsHttpNotFound"
   "TooManyRequests" "IdTokenHttpNotFound" "AccountsNoResponse"
   "ClientMetadataInvalidResponse" "AccountsInvalidResponse"
   "ErrorIdToken" "ClientMetadataHttpNotFound"
   "IdTokenInvalidResponse"})

(s/def
 :user/client-hint-issue-details
 (s/keys
  :req-un
  [:user/source-code-location :user/client-hint-issue-reason]))

(s/def
 :user/inspector-issue-code
 #{"AttributionReportingIssue" "BlockedByResponseIssue"
   "MixedContentIssue" "ClientHintIssue" "SharedArrayBufferIssue"
   "TrustedWebActivityIssue" "FederatedAuthRequestIssue"
   "ContentSecurityPolicyIssue" "DeprecationIssue" "GenericIssue"
   "LowTextContrastIssue" "CorsIssue" "NavigatorUserAgentIssue"
   "CookieIssue" "QuirksModeIssue" "HeavyAdIssue"})

(s/def
 :user/inspector-issue-details
 (s/keys
  :opt-un
  [:user/cookie-issue-details
   :user/mixed-content-issue-details
   :user/blocked-by-response-issue-details
   :user/heavy-ad-issue-details
   :user/content-security-policy-issue-details
   :user/shared-array-buffer-issue-details
   :user/twa-quality-enforcement-details
   :user/low-text-contrast-issue-details
   :user/cors-issue-details
   :user/attribution-reporting-issue-details
   :user/quirks-mode-issue-details
   :user/navigator-user-agent-issue-details
   :user/generic-issue-details
   :user/deprecation-issue-details
   :user/client-hint-issue-details
   :user/federated-auth-request-issue-details]))

(s/def :user/issue-id string?)

(s/def
 :user/inspector-issue
 (s/keys
  :req-un
  [:user/code :user/details]
  :opt-un
  [:user/issue-id]))
(defn
 get-encoded-response
 "Returns the response body and size if it were re-encoded with the specified settings. Only\napplies to images.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | Identifier of the network request to get content for.\n  :encoding   | The encoding to use.\n  :quality    | The quality of the encoding (0-1). (defaults to 1) (optional)\n  :size-only  | Whether to only return the size information (defaults to false). (optional)\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :body          | The encoded body as a base64 string. Omitted if sizeOnly is true. (Encoded as a base64 string when passed over JSON) (optional)\n  :original-size | Size before re-encoding.\n  :encoded-size  | Size after re-encoding."
 ([]
  (get-encoded-response
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id encoding quality size-only]}]
  (get-encoded-response
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [request-id encoding quality size-only]}]
  (cmd/command
   connection
   "Audits"
   "getEncodedResponse"
   params
   {:request-id "requestId",
    :encoding "encoding",
    :quality "quality",
    :size-only "sizeOnly"})))

(s/fdef
 get-encoded-response
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
     ::encoding]
    :opt-un
    [::quality
     ::size-only]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::request-id
     ::encoding]
    :opt-un
    [::quality
     ::size-only])))
 :ret
 (s/keys
  :req-un
  [::original-size
   ::encoded-size]
  :opt-un
  [::body]))

(defn
 disable
 "Disables issues domain, prevents further issues from being reported to the client."
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
   "Audits"
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
 "Enables issues domain, sends the issues collected so far to the client by means of the\n`issueAdded` event."
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
   "Audits"
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
 check-contrast
 "Runs the contrast check for the target page. Found issues are reported\nusing Audits.issueAdded event.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :report-aaa | Whether to report WCAG AAA level issues. Default is false. (optional)"
 ([]
  (check-contrast
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [report-aaa]}]
  (check-contrast
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [report-aaa]}]
  (cmd/command
   connection
   "Audits"
   "checkContrast"
   params
   {:report-aaa "reportAAA"})))

(s/fdef
 check-contrast
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::report-aaa]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::report-aaa])))
 :ret
 (s/keys))
