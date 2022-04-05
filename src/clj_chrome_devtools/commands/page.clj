(ns clj-chrome-devtools.commands.page
  "Actions and events related to the inspected page belong to the page domain."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::frame-id
 string?)

(s/def
 ::ad-frame-type
 #{"none" "child" "root"})

(s/def
 ::ad-frame-explanation
 #{"MatchedBlockingRule" "CreatedByAdScript" "ParentIsAd"})

(s/def
 ::ad-frame-status
 (s/keys
  :req-un
  [::ad-frame-type]
  :opt-un
  [::explanations]))

(s/def
 ::secure-context-type
 #{"Secure" "SecureLocalhost" "InsecureScheme" "InsecureAncestor"})

(s/def
 ::cross-origin-isolated-context-type
 #{"Isolated" "NotIsolatedFeatureDisabled" "NotIsolated"})

(s/def
 ::gated-api-features
 #{"SharedArrayBuffersTransferAllowed" "PerformanceProfile"
   "PerformanceMeasureMemory" "SharedArrayBuffers"})

(s/def
 ::permissions-policy-feature
 #{"ch-ua-full-version-list" "ch-ua-wow64" "ch-viewport-height"
   "gamepad" "sync-xhr" "ch-ua-full" "run-ad-auction"
   "screen-wake-lock" "ch-prefers-color-scheme" "xr-spatial-tracking"
   "serial" "trust-token-redemption" "cross-origin-isolated"
   "frobulate" "shared-autofill" "display-capture" "autoplay"
   "document-domain" "direct-sockets" "clipboard-read"
   "execution-while-not-rendered" "vertical-scroll" "camera"
   "magnetometer" "web-share" "ch-ua-model" "geolocation" "gyroscope"
   "keyboard-map" "clipboard-write" "ch-ua-reduced"
   "ch-ua-full-version" "ch-partitioned-cookies" "ch-downlink" "usb"
   "ch-ua-platform-version" "ch-ua-mobile" "attribution-reporting"
   "ch-ua-platform" "interest-cohort" "ch-viewport-width" "hid"
   "ambient-light-sensor" "ch-ua-bitness" "window-placement"
   "join-ad-interest-group" "microphone" "fullscreen" "browsing-topics"
   "midi" "ch-ect" "execution-while-out-of-viewport" "otp-credentials"
   "storage-access-api" "ch-device-memory" "ch-ua-arch" "ch-rtt"
   "picture-in-picture" "idle-detection" "publickey-credentials-get"
   "ch-ua" "encrypted-media" "focus-without-user-activation" "ch-dpr"
   "accelerometer" "payment" "ch-width"})

(s/def
 ::permissions-policy-block-reason
 #{"Header" "InFencedFrameTree" "IframeAttribute"})

(s/def
 ::permissions-policy-block-locator
 (s/keys
  :req-un
  [::frame-id
   ::block-reason]))

(s/def
 ::permissions-policy-feature-state
 (s/keys
  :req-un
  [::feature
   ::allowed]
  :opt-un
  [::locator]))

(s/def
 ::origin-trial-token-status
 #{"WrongOrigin" "NotSupported" "FeatureDisabled" "WrongVersion"
   "FeatureDisabledForUser" "Expired" "Success" "Malformed"
   "UnknownTrial" "InvalidSignature" "Insecure" "TokenDisabled"})

(s/def
 ::origin-trial-status
 #{"ValidTokenNotProvided" "Enabled" "TrialNotAllowed"
   "OSNotSupported"})

(s/def
 ::origin-trial-usage-restriction
 #{"None" "Subset"})

(s/def
 ::origin-trial-token
 (s/keys
  :req-un
  [::origin
   ::match-sub-domains
   ::trial-name
   ::expiry-time
   ::is-third-party
   ::usage-restriction]))

(s/def
 ::origin-trial-token-with-status
 (s/keys
  :req-un
  [::raw-token-text
   ::status]
  :opt-un
  [::parsed-token]))

(s/def
 ::origin-trial
 (s/keys
  :req-un
  [::trial-name
   ::status
   ::tokens-with-status]))

(s/def
 ::frame
 (s/keys
  :req-un
  [::id
   ::loader-id
   ::url
   ::domain-and-registry
   ::security-origin
   ::mime-type
   ::secure-context-type
   ::cross-origin-isolated-context-type
   ::gated-api-features]
  :opt-un
  [::parent-id
   ::name
   ::url-fragment
   ::unreachable-url
   ::ad-frame-status]))

(s/def
 ::frame-resource
 (s/keys
  :req-un
  [::url
   ::type
   ::mime-type]
  :opt-un
  [::last-modified
   ::content-size
   ::failed
   ::canceled]))

(s/def
 ::frame-resource-tree
 (s/keys
  :req-un
  [::frame
   ::resources]
  :opt-un
  [::child-frames]))

(s/def
 ::frame-tree
 (s/keys
  :req-un
  [::frame]
  :opt-un
  [::child-frames]))

(s/def
 ::script-identifier
 string?)

(s/def
 ::transition-type
 #{"typed" "form_submit" "auto_subframe" "address_bar" "auto_toplevel"
   "manual_subframe" "keyword_generated" "keyword" "link" "reload"
   "other" "auto_bookmark" "generated"})

(s/def
 ::navigation-entry
 (s/keys
  :req-un
  [::id
   ::url
   ::user-typed-url
   ::title
   ::transition-type]))

(s/def
 ::screencast-frame-metadata
 (s/keys
  :req-un
  [::offset-top
   ::page-scale-factor
   ::device-width
   ::device-height
   ::scroll-offset-x
   ::scroll-offset-y]
  :opt-un
  [::timestamp]))

(s/def
 ::dialog-type
 #{"alert" "confirm" "prompt" "beforeunload"})

(s/def
 ::app-manifest-error
 (s/keys
  :req-un
  [::message
   ::critical
   ::line
   ::column]))

(s/def
 ::app-manifest-parsed-properties
 (s/keys
  :req-un
  [::scope]))

(s/def
 ::layout-viewport
 (s/keys
  :req-un
  [::page-x
   ::page-y
   ::client-width
   ::client-height]))

(s/def
 ::visual-viewport
 (s/keys
  :req-un
  [::offset-x
   ::offset-y
   ::page-x
   ::page-y
   ::client-width
   ::client-height
   ::scale]
  :opt-un
  [::zoom]))

(s/def
 ::viewport
 (s/keys
  :req-un
  [::x
   ::y
   ::width
   ::height
   ::scale]))

(s/def
 ::font-families
 (s/keys
  :opt-un
  [::standard
   ::fixed
   ::serif
   ::sans-serif
   ::cursive
   ::fantasy
   ::pictograph]))

(s/def
 :user/script-font-families
 (s/keys :req-un [:user/script :user/font-families]))

(s/def
 :user/font-sizes
 (s/keys :opt-un [:user/standard :user/fixed]))

(s/def
 :user/client-navigation-reason
 #{"scriptInitiated" "metaTagRefresh" "anchorClick" "reload"
   "formSubmissionPost" "pageBlockInterstitial" "formSubmissionGet"
   "httpHeaderRefresh"})

(s/def
 :user/client-navigation-disposition
 #{"download" "newTab" "currentTab" "newWindow"})

(s/def
 :user/installability-error-argument
 (s/keys :req-un [:user/name :user/value]))

(s/def
 :user/installability-error
 (s/keys
  :req-un
  [:user/error-id :user/error-arguments]))

(s/def
 :user/referrer-policy
 #{"origin" "noReferrerWhenDowngrade" "sameOrigin" "strictOrigin"
   "unsafeUrl" "originWhenCrossOrigin" "noReferrer"
   "strictOriginWhenCrossOrigin"})

(s/def
 :user/compilation-cache-params
 (s/keys :req-un [:user/url] :opt-un [:user/eager]))

(s/def
 :user/navigation-type
 #{"Navigation" "BackForwardCacheRestore"})

(s/def
 :user/back-forward-cache-not-restored-reason
 #{"UnloadHandlerExistsInMainFrame" "OutstandingNetworkRequestXHR"
   "WebNfc" "HTTPStatusNotOK" "PictureInPicture"
   "RequestedNotificationsPermission" "TimeoutPuttingInCache"
   "JavaScriptExecution" "InjectedStyleSheet"
   "EmbedderPermissionRequestManager"
   "EmbedderSafeBrowsingThreatDetails" "IgnoreEventAndEvict" "Timeout"
   "RequestedMIDIPermission" "NoResponseHead" "CacheControlNoStore"
   "BroadcastChannel" "SchemeNotHTTPOrHTTPS" "SubframeIsNavigating"
   "WasGrantedMediaAccess" "WebSocket" "ContentFileSystemAccess"
   "IndexedDBConnection" "KeyboardLock" "CacheLimit" "ContainsPlugins"
   "ContentSerial" "WebLocks" "EmbedderExtensionMessagingForOpenPort"
   "ContentSecurityHandler" "AppBanner" "ContentFileChooser"
   "OutstandingNetworkRequestOthers" "BrowsingInstanceNotSwapped"
   "EmbedderExtensionMessaging" "UserAgentOverrideDiffers"
   "EmbedderSafeBrowsingTriggeredPopupBlocker"
   "RequestedStorageAccessGrant" "SchedulerTrackedFeatureUsed"
   "ContentMediaSessionService" "RequestedBackgroundWorkPermission"
   "ContentMediaDevicesDispatcherHost" "NotPrimaryMainFrame"
   "BackForwardCacheDisabled" "PaymentManager"
   "MainResourceHasCacheControlNoStore" "WebShare"
   "NetworkRequestTimeout" "EmbedderAppBannerManager" "ErrorDocument"
   "NetworkExceedsBufferLimit" "BackForwardCacheDisabledByCommandLine"
   "Unknown" "OutstandingNetworkRequestFetch"
   "EmbedderDomDistillerSelfDeletingRequestDelegate"
   "EmbedderOomInterventionTabHelper" "ContentMediaSession"
   "OutstandingNetworkRequestDirectSocket" "ServiceWorkerClaim"
   "RequestedAudioCapturePermission" "ConflictingBrowsingInstance"
   "SubresourceHasCacheControlNoCache"
   "EmbedderDomDistillerViewerSource" "UnloadHandlerExistsInSubFrame"
   "WebDatabase" "RenderFrameHostReused_SameSite"
   "OptInUnloadHeaderNotPresent"
   "RequestedBackForwardCacheBlockedSensors" "SharedWorker"
   "BackForwardCacheDisabledByLowMemory" "ContentWebBluetooth"
   "RelatedActiveContentsExist" "CacheControlNoStoreCookieModified"
   "ContentWebUSB" "Printing" "Loading" "Dummy" "IdleManager" "WebXR"
   "ContentScreenReader" "DisableForRenderFrameHostCalled"
   "ServiceWorkerPostMessage" "GrantedMediaStreamAccess"
   "EmbedderOfflinePage" "ServiceWorkerUnregistration" "Portal"
   "WebHID" "SpeechSynthesis" "WebTransport"
   "ServiceWorkerVersionActivation"
   "EmbedderExtensionSentMessageToCachedFrame"
   "NetworkRequestRedirected" "CacheFlushed" "RendererProcessCrashed"
   "DomainNotAllowed" "RenderFrameHostReused_CrossSite"
   "EmbedderPopupBlockerTabHelper" "OutstandingIndexedDBTransaction"
   "ActivationNavigationsDisallowedForBug1234857"
   "EmbedderChromePasswordManagerClientBindCredentialManager"
   "MainResourceHasCacheControlNoCache" "HaveInnerContents"
   "NotMostRecentNavigationEntry" "WebRTC" "RendererProcessKilled"
   "NavigationCancelledWhileRestoring" "SessionRestored"
   "BackForwardCacheDisabledForPrerender"
   "EnteredBackForwardCacheBeforeServiceWorkerHostAdded"
   "NetworkRequestDatapipeDrainedAsBytesConsumer" "EmbedderModalDialog"
   "RequestedVideoCapturePermission" "WebOTPService"
   "CacheControlNoStoreHTTPOnlyCookieModified"
   "BackForwardCacheDisabledForDelegate" "HTTPMethodNotGET"
   "ForegroundCacheLimit" "ContentWebAuthenticationAPI"
   "InjectedJavascript" "EmbedderExtensions" "DocumentLoaded"
   "SpeechRecognizer" "DedicatedWorkerOrWorklet"
   "SubresourceHasCacheControlNoStore"})

(s/def
 :user/back-forward-cache-not-restored-reason-type
 #{"SupportPending" "PageSupportNeeded" "Circumstantial"})

(s/def
 :user/back-forward-cache-not-restored-explanation
 (s/keys
  :req-un
  [:user/type :user/reason]
  :opt-un
  [:user/context]))

(s/def
 :user/back-forward-cache-not-restored-explanation-tree
 (s/keys
  :req-un
  [:user/url :user/explanations :user/children]))
(defn
 add-script-to-evaluate-on-load
 "Deprecated, please use addScriptToEvaluateOnNewDocument instead.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :script-source | null\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | Identifier of the added script."
 ([]
  (add-script-to-evaluate-on-load
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [script-source]}]
  (add-script-to-evaluate-on-load
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [script-source]}]
  (cmd/command
   connection
   "Page"
   "addScriptToEvaluateOnLoad"
   params
   {:script-source "scriptSource"})))

(s/fdef
 add-script-to-evaluate-on-load
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::script-source]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::script-source])))
 :ret
 (s/keys
  :req-un
  [::identifier]))

(defn
 add-script-to-evaluate-on-new-document
 "Evaluates given script in every frame upon creation (before loading frame's scripts).\n\nParameters map keys:\n\n\n  Key                       | Description \n  --------------------------|------------ \n  :source                   | null\n  :world-name               | If specified, creates an isolated world with the given name and evaluates given script in it.\nThis world name will be used as the ExecutionContextDescription::name when the corresponding\nevent is emitted. (optional)\n  :include-command-line-api | Specifies whether command line API should be available to the script, defaults\nto false. (optional)\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | Identifier of the added script."
 ([]
  (add-script-to-evaluate-on-new-document
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [source world-name include-command-line-api]}]
  (add-script-to-evaluate-on-new-document
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [source world-name include-command-line-api]}]
  (cmd/command
   connection
   "Page"
   "addScriptToEvaluateOnNewDocument"
   params
   {:source "source",
    :world-name "worldName",
    :include-command-line-api "includeCommandLineAPI"})))

(s/fdef
 add-script-to-evaluate-on-new-document
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::source]
    :opt-un
    [::world-name
     ::include-command-line-api]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::source]
    :opt-un
    [::world-name
     ::include-command-line-api])))
 :ret
 (s/keys
  :req-un
  [::identifier]))

(defn
 bring-to-front
 "Brings page to front (activates tab)."
 ([]
  (bring-to-front
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (bring-to-front
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "bringToFront"
   params
   {})))

(s/fdef
 bring-to-front
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
 capture-screenshot
 "Capture page screenshot.\n\nParameters map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :format                  | Image compression format (defaults to png). (optional)\n  :quality                 | Compression quality from range [0..100] (jpeg only). (optional)\n  :clip                    | Capture the screenshot of a given region only. (optional)\n  :from-surface            | Capture the screenshot from the surface, rather than the view. Defaults to true. (optional)\n  :capture-beyond-viewport | Capture the screenshot beyond the viewport. Defaults to false. (optional)\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :data | Base64-encoded image data. (Encoded as a base64 string when passed over JSON)"
 ([]
  (capture-screenshot
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [format quality clip from-surface capture-beyond-viewport]}]
  (capture-screenshot
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [format quality clip from-surface capture-beyond-viewport]}]
  (cmd/command
   connection
   "Page"
   "captureScreenshot"
   params
   {:format "format",
    :quality "quality",
    :clip "clip",
    :from-surface "fromSurface",
    :capture-beyond-viewport "captureBeyondViewport"})))

(s/fdef
 capture-screenshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::format
     ::quality
     ::clip
     ::from-surface
     ::capture-beyond-viewport]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::format
     ::quality
     ::clip
     ::from-surface
     ::capture-beyond-viewport])))
 :ret
 (s/keys
  :req-un
  [::data]))

(defn
 capture-snapshot
 "Returns a snapshot of the page as a string. For MHTML format, the serialization includes\niframes, shadow DOM, external resources, and element-inline styles.\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :format | Format (defaults to mhtml). (optional)\n\nReturn map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :data | Serialized page data."
 ([]
  (capture-snapshot
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [format]}]
  (capture-snapshot
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [format]}]
  (cmd/command
   connection
   "Page"
   "captureSnapshot"
   params
   {:format "format"})))

(s/fdef
 capture-snapshot
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::format]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::format])))
 :ret
 (s/keys
  :req-un
  [::data]))

(defn
 clear-device-metrics-override
 "Clears the overridden device metrics."
 ([]
  (clear-device-metrics-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-device-metrics-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "clearDeviceMetricsOverride"
   params
   {})))

(s/fdef
 clear-device-metrics-override
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
   "Page"
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
 clear-geolocation-override
 "Clears the overridden Geolocation Position and Error."
 ([]
  (clear-geolocation-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-geolocation-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "clearGeolocationOverride"
   params
   {})))

(s/fdef
 clear-geolocation-override
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
 create-isolated-world
 "Creates an isolated world for the given frame.\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :frame-id              | Id of the frame in which the isolated world should be created.\n  :world-name            | An optional name which is reported in the Execution Context. (optional)\n  :grant-univeral-access | Whether or not universal access should be granted to the isolated world. This is a powerful\noption, use with caution. (optional)\n\nReturn map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :execution-context-id | Execution context of the isolated world."
 ([]
  (create-isolated-world
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id world-name grant-univeral-access]}]
  (create-isolated-world
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [frame-id world-name grant-univeral-access]}]
  (cmd/command
   connection
   "Page"
   "createIsolatedWorld"
   params
   {:frame-id "frameId",
    :world-name "worldName",
    :grant-univeral-access "grantUniveralAccess"})))

(s/fdef
 create-isolated-world
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id]
    :opt-un
    [::world-name
     ::grant-univeral-access]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id]
    :opt-un
    [::world-name
     ::grant-univeral-access])))
 :ret
 (s/keys
  :req-un
  [::execution-context-id]))

(defn
 delete-cookie
 "Deletes browser cookie with given name, domain and path.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :cookie-name | Name of the cookie to remove.\n  :url         | URL to match cooke domain and path."
 ([]
  (delete-cookie
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [cookie-name url]}]
  (delete-cookie
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [cookie-name url]}]
  (cmd/command
   connection
   "Page"
   "deleteCookie"
   params
   {:cookie-name "cookieName", :url "url"})))

(s/fdef
 delete-cookie
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::cookie-name
     ::url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::cookie-name
     ::url])))
 :ret
 (s/keys))

(defn
 disable
 "Disables page domain notifications."
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
   "Page"
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
 "Enables page domain notifications."
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
   "Page"
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
 get-app-manifest
 "\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :url    | Manifest location.\n  :errors | null\n  :data   | Manifest content. (optional)\n  :parsed | Parsed manifest properties (optional)"
 ([]
  (get-app-manifest
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-app-manifest
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getAppManifest"
   params
   {})))

(s/fdef
 get-app-manifest
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
  [::url
   ::errors]
  :opt-un
  [::data
   ::parsed]))

(defn
 get-installability-errors
 "\n\nReturn map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :installability-errors | null"
 ([]
  (get-installability-errors
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-installability-errors
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getInstallabilityErrors"
   params
   {})))

(s/fdef
 get-installability-errors
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
  [::installability-errors]))

(defn
 get-manifest-icons
 "\n\nReturn map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :primary-icon | null (optional)"
 ([]
  (get-manifest-icons
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-manifest-icons
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getManifestIcons"
   params
   {})))

(s/fdef
 get-manifest-icons
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
  :opt-un
  [::primary-icon]))

(defn
 get-app-id
 "Returns the unique (PWA) app id.\nOnly returns values if the feature flag 'WebAppEnableManifestId' is enabled\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :app-id         | App id, either from manifest's id attribute or computed from start_url (optional)\n  :recommended-id | Recommendation for manifest's id attribute to match current id computed from start_url (optional)"
 ([]
  (get-app-id
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-app-id
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getAppId"
   params
   {})))

(s/fdef
 get-app-id
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
  :opt-un
  [::app-id
   ::recommended-id]))

(defn
 get-cookies
 "Returns all browser cookies. Depending on the backend support, will return detailed cookie\ninformation in the `cookies` field.\n\nReturn map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :cookies | Array of cookie objects."
 ([]
  (get-cookies
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-cookies
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getCookies"
   params
   {})))

(s/fdef
 get-cookies
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
 get-frame-tree
 "Returns present frame tree structure.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :frame-tree | Present frame tree structure."
 ([]
  (get-frame-tree
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-frame-tree
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getFrameTree"
   params
   {})))

(s/fdef
 get-frame-tree
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
  [::frame-tree]))

(defn
 get-layout-metrics
 "Returns metrics relating to the layouting of the page, such as viewport bounds/scale.\n\nReturn map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :layout-viewport     | Deprecated metrics relating to the layout viewport. Can be in DP or in CSS pixels depending on the `enable-use-zoom-for-dsf` flag. Use `cssLayoutViewport` instead.\n  :visual-viewport     | Deprecated metrics relating to the visual viewport. Can be in DP or in CSS pixels depending on the `enable-use-zoom-for-dsf` flag. Use `cssVisualViewport` instead.\n  :content-size        | Deprecated size of scrollable area. Can be in DP or in CSS pixels depending on the `enable-use-zoom-for-dsf` flag. Use `cssContentSize` instead.\n  :css-layout-viewport | Metrics relating to the layout viewport in CSS pixels.\n  :css-visual-viewport | Metrics relating to the visual viewport in CSS pixels.\n  :css-content-size    | Size of scrollable area in CSS pixels."
 ([]
  (get-layout-metrics
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-layout-metrics
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getLayoutMetrics"
   params
   {})))

(s/fdef
 get-layout-metrics
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
  [::layout-viewport
   ::visual-viewport
   ::content-size
   ::css-layout-viewport
   ::css-visual-viewport
   ::css-content-size]))

(defn
 get-navigation-history
 "Returns navigation history for the current page.\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :current-index | Index of the current navigation history entry.\n  :entries       | Array of navigation history entries."
 ([]
  (get-navigation-history
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-navigation-history
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getNavigationHistory"
   params
   {})))

(s/fdef
 get-navigation-history
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
  [::current-index
   ::entries]))

(defn
 reset-navigation-history
 "Resets navigation history for the current page."
 ([]
  (reset-navigation-history
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (reset-navigation-history
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "resetNavigationHistory"
   params
   {})))

(s/fdef
 reset-navigation-history
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
 get-resource-content
 "Returns content of the given resource.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | Frame id to get resource for.\n  :url      | URL of the resource to get content for.\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :content        | Resource content.\n  :base64-encoded | True, if content was served as base64."
 ([]
  (get-resource-content
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id url]}]
  (get-resource-content
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id url]}]
  (cmd/command
   connection
   "Page"
   "getResourceContent"
   params
   {:frame-id "frameId", :url "url"})))

(s/fdef
 get-resource-content
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id
     ::url]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::frame-id
     ::url])))
 :ret
 (s/keys
  :req-un
  [::content
   ::base64-encoded]))

(defn
 get-resource-tree
 "Returns present frame / resource tree structure.\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :frame-tree | Present frame / resource tree structure."
 ([]
  (get-resource-tree
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (get-resource-tree
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "getResourceTree"
   params
   {})))

(s/fdef
 get-resource-tree
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
  [::frame-tree]))

(defn
 handle-java-script-dialog
 "Accepts or dismisses a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload).\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :accept      | Whether to accept or dismiss the dialog.\n  :prompt-text | The text to enter into the dialog prompt before accepting. Used only if this is a prompt\ndialog. (optional)"
 ([]
  (handle-java-script-dialog
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [accept prompt-text]}]
  (handle-java-script-dialog
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [accept prompt-text]}]
  (cmd/command
   connection
   "Page"
   "handleJavaScriptDialog"
   params
   {:accept "accept", :prompt-text "promptText"})))

(s/fdef
 handle-java-script-dialog
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::accept]
    :opt-un
    [::prompt-text]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::accept]
    :opt-un
    [::prompt-text])))
 :ret
 (s/keys))

(defn
 navigate
 "Navigates current page to the given URL.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :url             | URL to navigate the page to.\n  :referrer        | Referrer URL. (optional)\n  :transition-type | Intended transition type. (optional)\n  :frame-id        | Frame id to navigate, if not specified navigates the top frame. (optional)\n  :referrer-policy | Referrer-policy used for the navigation. (optional)\n\nReturn map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :frame-id   | Frame id that has navigated (or failed to navigate)\n  :loader-id  | Loader identifier. (optional)\n  :error-text | User friendly error message, present if and only if navigation has failed. (optional)"
 ([]
  (navigate
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [url referrer transition-type frame-id referrer-policy]}]
  (navigate
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [url referrer transition-type frame-id referrer-policy]}]
  (cmd/command
   connection
   "Page"
   "navigate"
   params
   {:url "url",
    :referrer "referrer",
    :transition-type "transitionType",
    :frame-id "frameId",
    :referrer-policy "referrerPolicy"})))

(s/fdef
 navigate
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::url]
    :opt-un
    [::referrer
     ::transition-type
     ::frame-id
     ::referrer-policy]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::url]
    :opt-un
    [::referrer
     ::transition-type
     ::frame-id
     ::referrer-policy])))
 :ret
 (s/keys
  :req-un
  [::frame-id]
  :opt-un
  [::loader-id
   ::error-text]))

(defn
 navigate-to-history-entry
 "Navigates current page to the given history entry.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :entry-id | Unique id of the entry to navigate to."
 ([]
  (navigate-to-history-entry
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [entry-id]}]
  (navigate-to-history-entry
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [entry-id]}]
  (cmd/command
   connection
   "Page"
   "navigateToHistoryEntry"
   params
   {:entry-id "entryId"})))

(s/fdef
 navigate-to-history-entry
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::entry-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::entry-id])))
 :ret
 (s/keys))

(defn
 print-to-pdf
 "Print page as PDF.\n\nParameters map keys:\n\n\n  Key                         | Description \n  ----------------------------|------------ \n  :landscape                  | Paper orientation. Defaults to false. (optional)\n  :display-header-footer      | Display header and footer. Defaults to false. (optional)\n  :print-background           | Print background graphics. Defaults to false. (optional)\n  :scale                      | Scale of the webpage rendering. Defaults to 1. (optional)\n  :paper-width                | Paper width in inches. Defaults to 8.5 inches. (optional)\n  :paper-height               | Paper height in inches. Defaults to 11 inches. (optional)\n  :margin-top                 | Top margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :margin-bottom              | Bottom margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :margin-left                | Left margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :margin-right               | Right margin in inches. Defaults to 1cm (~0.4 inches). (optional)\n  :page-ranges                | Paper ranges to print, e.g., '1-5, 8, 11-13'. Defaults to the empty string, which means\nprint all pages. (optional)\n  :ignore-invalid-page-ranges | Whether to silently ignore invalid but successfully parsed page ranges, such as '3-2'.\nDefaults to false. (optional)\n  :header-template            | HTML template for the print header. Should be valid HTML markup with following\nclasses used to inject printing values into them:\n- `date`: formatted print date\n- `title`: document title\n- `url`: document location\n- `pageNumber`: current page number\n- `totalPages`: total pages in the document\n\nFor example, `<span class=title></span>` would generate span containing the title. (optional)\n  :footer-template            | HTML template for the print footer. Should use the same format as the `headerTemplate`. (optional)\n  :prefer-css-page-size       | Whether or not to prefer page size as defined by css. Defaults to false,\nin which case the content will be scaled to fit the paper size. (optional)\n  :transfer-mode              | return as stream (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :data   | Base64-encoded pdf data. Empty if |returnAsStream| is specified. (Encoded as a base64 string when passed over JSON)\n  :stream | A handle of the stream that holds resulting PDF data. (optional)"
 ([]
  (print-to-pdf
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [landscape
     display-header-footer
     print-background
     scale
     paper-width
     paper-height
     margin-top
     margin-bottom
     margin-left
     margin-right
     page-ranges
     ignore-invalid-page-ranges
     header-template
     footer-template
     prefer-css-page-size
     transfer-mode]}]
  (print-to-pdf
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [landscape
     display-header-footer
     print-background
     scale
     paper-width
     paper-height
     margin-top
     margin-bottom
     margin-left
     margin-right
     page-ranges
     ignore-invalid-page-ranges
     header-template
     footer-template
     prefer-css-page-size
     transfer-mode]}]
  (cmd/command
   connection
   "Page"
   "printToPDF"
   params
   {:page-ranges "pageRanges",
    :ignore-invalid-page-ranges "ignoreInvalidPageRanges",
    :scale "scale",
    :header-template "headerTemplate",
    :display-header-footer "displayHeaderFooter",
    :margin-left "marginLeft",
    :margin-top "marginTop",
    :transfer-mode "transferMode",
    :prefer-css-page-size "preferCSSPageSize",
    :landscape "landscape",
    :footer-template "footerTemplate",
    :print-background "printBackground",
    :paper-width "paperWidth",
    :margin-right "marginRight",
    :margin-bottom "marginBottom",
    :paper-height "paperHeight"})))

(s/fdef
 print-to-pdf
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::landscape
     ::display-header-footer
     ::print-background
     ::scale
     ::paper-width
     ::paper-height
     ::margin-top
     ::margin-bottom
     ::margin-left
     ::margin-right
     ::page-ranges
     ::ignore-invalid-page-ranges
     ::header-template
     ::footer-template
     ::prefer-css-page-size
     ::transfer-mode]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::landscape
     ::display-header-footer
     ::print-background
     ::scale
     ::paper-width
     ::paper-height
     ::margin-top
     ::margin-bottom
     ::margin-left
     ::margin-right
     ::page-ranges
     ::ignore-invalid-page-ranges
     ::header-template
     ::footer-template
     ::prefer-css-page-size
     ::transfer-mode])))
 :ret
 (s/keys
  :req-un
  [::data]
  :opt-un
  [::stream]))

(defn
 reload
 "Reloads given page optionally ignoring the cache.\n\nParameters map keys:\n\n\n  Key                         | Description \n  ----------------------------|------------ \n  :ignore-cache               | If true, browser cache is ignored (as if the user pressed Shift+refresh). (optional)\n  :script-to-evaluate-on-load | If set, the script will be injected into all frames of the inspected page after reload.\nArgument will be ignored if reloading dataURL origin. (optional)"
 ([]
  (reload
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [ignore-cache script-to-evaluate-on-load]}]
  (reload
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [ignore-cache script-to-evaluate-on-load]}]
  (cmd/command
   connection
   "Page"
   "reload"
   params
   {:ignore-cache "ignoreCache",
    :script-to-evaluate-on-load "scriptToEvaluateOnLoad"})))

(s/fdef
 reload
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [::ignore-cache
     ::script-to-evaluate-on-load]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::ignore-cache
     ::script-to-evaluate-on-load])))
 :ret
 (s/keys))

(defn
 remove-script-to-evaluate-on-load
 "Deprecated, please use removeScriptToEvaluateOnNewDocument instead.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | null"
 ([]
  (remove-script-to-evaluate-on-load
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [identifier]}]
  (remove-script-to-evaluate-on-load
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [identifier]}]
  (cmd/command
   connection
   "Page"
   "removeScriptToEvaluateOnLoad"
   params
   {:identifier "identifier"})))

(s/fdef
 remove-script-to-evaluate-on-load
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::identifier]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::identifier])))
 :ret
 (s/keys))

(defn
 remove-script-to-evaluate-on-new-document
 "Removes given script from the list.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :identifier | null"
 ([]
  (remove-script-to-evaluate-on-new-document
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [identifier]}]
  (remove-script-to-evaluate-on-new-document
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [identifier]}]
  (cmd/command
   connection
   "Page"
   "removeScriptToEvaluateOnNewDocument"
   params
   {:identifier "identifier"})))

(s/fdef
 remove-script-to-evaluate-on-new-document
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::identifier]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::identifier])))
 :ret
 (s/keys))

(defn
 screencast-frame-ack
 "Acknowledges that a screencast frame has been received by the frontend.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :session-id | Frame number."
 ([]
  (screencast-frame-ack
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [session-id]}]
  (screencast-frame-ack
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [session-id]}]
  (cmd/command
   connection
   "Page"
   "screencastFrameAck"
   params
   {:session-id "sessionId"})))

(s/fdef
 screencast-frame-ack
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::session-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::session-id])))
 :ret
 (s/keys))

(defn
 search-in-resource
 "Searches for given string in resource content.\n\nParameters map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :frame-id       | Frame id for resource to search in.\n  :url            | URL of the resource to search in.\n  :query          | String to search for.\n  :case-sensitive | If true, search is case sensitive. (optional)\n  :is-regex       | If true, treats string parameter as regex. (optional)\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :result | List of search matches."
 ([]
  (search-in-resource
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id url query case-sensitive is-regex]}]
  (search-in-resource
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [frame-id url query case-sensitive is-regex]}]
  (cmd/command
   connection
   "Page"
   "searchInResource"
   params
   {:frame-id "frameId",
    :url "url",
    :query "query",
    :case-sensitive "caseSensitive",
    :is-regex "isRegex"})))

(s/fdef
 search-in-resource
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::frame-id
     ::url
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
    [::frame-id
     ::url
     ::query]
    :opt-un
    [::case-sensitive
     ::is-regex])))
 :ret
 (s/keys
  :req-un
  [::result]))

(defn
 set-ad-blocking-enabled
 "Enable Chrome's experimental ad filter on all sites.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | Whether to block ads."
 ([]
  (set-ad-blocking-enabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-ad-blocking-enabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (cmd/command
   connection
   "Page"
   "setAdBlockingEnabled"
   params
   {:enabled "enabled"})))

(s/fdef
 set-ad-blocking-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/enabled])))
 :ret
 (s/keys))

(defn
 set-bypass-csp
 "Enable page Content Security Policy by-passing.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | Whether to bypass page CSP."
 ([]
  (set-bypass-csp
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-bypass-csp
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (cmd/command
   connection
   "Page"
   "setBypassCSP"
   params
   {:enabled "enabled"})))

(s/fdef
 set-bypass-csp
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/enabled])))
 :ret
 (s/keys))

(defn
 get-permissions-policy-state
 "Get Permissions Policy state on given frame.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | null\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :states | null"
 ([]
  (get-permissions-policy-state
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id]}]
  (get-permissions-policy-state
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id]}]
  (cmd/command
   connection
   "Page"
   "getPermissionsPolicyState"
   params
   {:frame-id "frameId"})))

(s/fdef
 get-permissions-policy-state
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/frame-id])))
 :ret
 (s/keys :req-un [:user/states]))

(defn
 get-origin-trials
 "Get Origin Trials on given frame.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | null\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :origin-trials | null"
 ([]
  (get-origin-trials
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id]}]
  (get-origin-trials
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id]}]
  (cmd/command
   connection
   "Page"
   "getOriginTrials"
   params
   {:frame-id "frameId"})))

(s/fdef
 get-origin-trials
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/frame-id]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/frame-id])))
 :ret
 (s/keys :req-un [:user/origin-trials]))

(defn
 set-device-metrics-override
 "Overrides the values of device screen dimensions (window.screen.width, window.screen.height,\nwindow.innerWidth, window.innerHeight, and \"device-width\"/\"device-height\"-related CSS media\nquery results).\n\nParameters map keys:\n\n\n  Key                    | Description \n  -----------------------|------------ \n  :width                 | Overriding width value in pixels (minimum 0, maximum 10000000). 0 disables the override.\n  :height                | Overriding height value in pixels (minimum 0, maximum 10000000). 0 disables the override.\n  :device-scale-factor   | Overriding device scale factor value. 0 disables the override.\n  :mobile                | Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text\nautosizing and more.\n  :scale                 | Scale to apply to resulting view image. (optional)\n  :screen-width          | Overriding screen width value in pixels (minimum 0, maximum 10000000). (optional)\n  :screen-height         | Overriding screen height value in pixels (minimum 0, maximum 10000000). (optional)\n  :position-x            | Overriding view X position on screen in pixels (minimum 0, maximum 10000000). (optional)\n  :position-y            | Overriding view Y position on screen in pixels (minimum 0, maximum 10000000). (optional)\n  :dont-set-visible-size | Do not set visible view size, rely upon explicit setVisibleSize call. (optional)\n  :screen-orientation    | Screen orientation override. (optional)\n  :viewport              | The viewport dimensions and scale. If not set, the override is cleared. (optional)"
 ([]
  (set-device-metrics-override
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [width
     height
     device-scale-factor
     mobile
     scale
     screen-width
     screen-height
     position-x
     position-y
     dont-set-visible-size
     screen-orientation
     viewport]}]
  (set-device-metrics-override
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [width
     height
     device-scale-factor
     mobile
     scale
     screen-width
     screen-height
     position-x
     position-y
     dont-set-visible-size
     screen-orientation
     viewport]}]
  (cmd/command
   connection
   "Page"
   "setDeviceMetricsOverride"
   params
   {:dont-set-visible-size "dontSetVisibleSize",
    :device-scale-factor "deviceScaleFactor",
    :screen-orientation "screenOrientation",
    :scale "scale",
    :width "width",
    :position-y "positionY",
    :position-x "positionX",
    :screen-height "screenHeight",
    :mobile "mobile",
    :viewport "viewport",
    :height "height",
    :screen-width "screenWidth"})))

(s/fdef
 set-device-metrics-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:user/width :user/height :user/device-scale-factor :user/mobile]
    :opt-un
    [:user/scale
     :user/screen-width
     :user/screen-height
     :user/position-x
     :user/position-y
     :user/dont-set-visible-size
     :user/screen-orientation
     :user/viewport]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:user/width :user/height :user/device-scale-factor :user/mobile]
    :opt-un
    [:user/scale
     :user/screen-width
     :user/screen-height
     :user/position-x
     :user/position-y
     :user/dont-set-visible-size
     :user/screen-orientation
     :user/viewport])))
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
   "Page"
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
    [:user/alpha :user/beta :user/gamma]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:user/alpha :user/beta :user/gamma])))
 :ret
 (s/keys))

(defn
 set-font-families
 "Set generic font families.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :font-families | Specifies font families to set. If a font family is not specified, it won't be changed.\n  :for-scripts   | Specifies font families to set for individual scripts. (optional)"
 ([]
  (set-font-families
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [font-families for-scripts]}]
  (set-font-families
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [font-families for-scripts]}]
  (cmd/command
   connection
   "Page"
   "setFontFamilies"
   params
   {:font-families "fontFamilies", :for-scripts "forScripts"})))

(s/fdef
 set-font-families
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:user/font-families]
    :opt-un
    [:user/for-scripts]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:user/font-families]
    :opt-un
    [:user/for-scripts])))
 :ret
 (s/keys))

(defn
 set-font-sizes
 "Set default font sizes.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :font-sizes | Specifies font sizes to set. If a font size is not specified, it won't be changed."
 ([]
  (set-font-sizes
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [font-sizes]}]
  (set-font-sizes
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [font-sizes]}]
  (cmd/command
   connection
   "Page"
   "setFontSizes"
   params
   {:font-sizes "fontSizes"})))

(s/fdef
 set-font-sizes
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/font-sizes]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/font-sizes])))
 :ret
 (s/keys))

(defn
 set-document-content
 "Sets given markup as the document's HTML.\n\nParameters map keys:\n\n\n  Key       | Description \n  ----------|------------ \n  :frame-id | Frame id to set HTML for.\n  :html     | HTML content to set."
 ([]
  (set-document-content
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [frame-id html]}]
  (set-document-content
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [frame-id html]}]
  (cmd/command
   connection
   "Page"
   "setDocumentContent"
   params
   {:frame-id "frameId", :html "html"})))

(s/fdef
 set-document-content
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/frame-id :user/html]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/frame-id :user/html])))
 :ret
 (s/keys))

(defn
 set-download-behavior
 "Set the behavior when downloading a file.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :behavior      | Whether to allow all or deny all download requests, or use default Chrome behavior if\navailable (otherwise deny).\n  :download-path | The default path to save downloaded files to. This is required if behavior is set to 'allow' (optional)"
 ([]
  (set-download-behavior
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [behavior download-path]}]
  (set-download-behavior
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [behavior download-path]}]
  (cmd/command
   connection
   "Page"
   "setDownloadBehavior"
   params
   {:behavior "behavior", :download-path "downloadPath"})))

(s/fdef
 set-download-behavior
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:user/behavior]
    :opt-un
    [:user/download-path]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:user/behavior]
    :opt-un
    [:user/download-path])))
 :ret
 (s/keys))

(defn
 set-geolocation-override
 "Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position\nunavailable.\n\nParameters map keys:\n\n\n  Key        | Description \n  -----------|------------ \n  :latitude  | Mock latitude (optional)\n  :longitude | Mock longitude (optional)\n  :accuracy  | Mock accuracy (optional)"
 ([]
  (set-geolocation-override
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [latitude longitude accuracy]}]
  (set-geolocation-override
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [latitude longitude accuracy]}]
  (cmd/command
   connection
   "Page"
   "setGeolocationOverride"
   params
   {:latitude "latitude",
    :longitude "longitude",
    :accuracy "accuracy"})))

(s/fdef
 set-geolocation-override
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [:user/latitude :user/longitude :user/accuracy]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [:user/latitude :user/longitude :user/accuracy])))
 :ret
 (s/keys))

(defn
 set-lifecycle-events-enabled
 "Controls whether page will emit lifecycle events.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | If true, starts emitting lifecycle events."
 ([]
  (set-lifecycle-events-enabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-lifecycle-events-enabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (cmd/command
   connection
   "Page"
   "setLifecycleEventsEnabled"
   params
   {:enabled "enabled"})))

(s/fdef
 set-lifecycle-events-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/enabled])))
 :ret
 (s/keys))

(defn
 set-touch-emulation-enabled
 "Toggles mouse event-based touch event emulation.\n\nParameters map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :enabled       | Whether the touch event emulation should be enabled.\n  :configuration | Touch/gesture events configuration. Default: current platform. (optional)"
 ([]
  (set-touch-emulation-enabled
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled configuration]}]
  (set-touch-emulation-enabled
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled configuration]}]
  (cmd/command
   connection
   "Page"
   "setTouchEmulationEnabled"
   params
   {:enabled "enabled", :configuration "configuration"})))

(s/fdef
 set-touch-emulation-enabled
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:user/enabled]
    :opt-un
    [:user/configuration]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:user/enabled]
    :opt-un
    [:user/configuration])))
 :ret
 (s/keys))

(defn
 start-screencast
 "Starts sending each frame using the `screencastFrame` event.\n\nParameters map keys:\n\n\n  Key              | Description \n  -----------------|------------ \n  :format          | Image compression format. (optional)\n  :quality         | Compression quality from range [0..100]. (optional)\n  :max-width       | Maximum screenshot width. (optional)\n  :max-height      | Maximum screenshot height. (optional)\n  :every-nth-frame | Send every n-th frame. (optional)"
 ([]
  (start-screencast
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [format quality max-width max-height every-nth-frame]}]
  (start-screencast
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [format quality max-width max-height every-nth-frame]}]
  (cmd/command
   connection
   "Page"
   "startScreencast"
   params
   {:format "format",
    :quality "quality",
    :max-width "maxWidth",
    :max-height "maxHeight",
    :every-nth-frame "everyNthFrame"})))

(s/fdef
 start-screencast
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :opt-un
    [:user/format
     :user/quality
     :user/max-width
     :user/max-height
     :user/every-nth-frame]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [:user/format
     :user/quality
     :user/max-width
     :user/max-height
     :user/every-nth-frame])))
 :ret
 (s/keys))

(defn
 stop-loading
 "Force the page stop all navigations and pending resource fetches."
 ([]
  (stop-loading
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-loading
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "stopLoading"
   params
   {})))

(s/fdef
 stop-loading
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
 crash
 "Crashes renderer on the IO thread, generates minidumps."
 ([]
  (crash
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (crash
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "crash"
   params
   {})))

(s/fdef
 crash
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
 close
 "Tries to close page, running its beforeunload hooks, if any."
 ([]
  (close
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (close
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "close"
   params
   {})))

(s/fdef
 close
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
 set-web-lifecycle-state
 "Tries to update the web lifecycle state of the page.\nIt will transition the page to the given state according to:\nhttps://github.com/WICG/web-lifecycle/\n\nParameters map keys:\n\n\n  Key    | Description \n  -------|------------ \n  :state | Target lifecycle state"
 ([]
  (set-web-lifecycle-state
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [state]}]
  (set-web-lifecycle-state
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [state]}]
  (cmd/command
   connection
   "Page"
   "setWebLifecycleState"
   params
   {:state "state"})))

(s/fdef
 set-web-lifecycle-state
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/state]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/state])))
 :ret
 (s/keys))

(defn
 stop-screencast
 "Stops sending each frame in the `screencastFrame`."
 ([]
  (stop-screencast
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (stop-screencast
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "stopScreencast"
   params
   {})))

(s/fdef
 stop-screencast
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
 produce-compilation-cache
 "Requests backend to produce compilation cache for the specified scripts.\n`scripts` are appeneded to the list of scripts for which the cache\nwould be produced. The list may be reset during page navigation.\nWhen script with a matching URL is encountered, the cache is optionally\nproduced upon backend discretion, based on internal heuristics.\nSee also: `Page.compilationCacheProduced`.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :scripts | null"
 ([]
  (produce-compilation-cache
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [scripts]}]
  (produce-compilation-cache
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [scripts]}]
  (cmd/command
   connection
   "Page"
   "produceCompilationCache"
   params
   {:scripts "scripts"})))

(s/fdef
 produce-compilation-cache
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/scripts]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/scripts])))
 :ret
 (s/keys))

(defn
 add-compilation-cache
 "Seeds compilation cache for given url. Compilation cache does not survive\ncross-process navigation.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :url  | null\n  :data | Base64-encoded data (Encoded as a base64 string when passed over JSON)"
 ([]
  (add-compilation-cache
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [url data]}]
  (add-compilation-cache
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [url data]}]
  (cmd/command
   connection
   "Page"
   "addCompilationCache"
   params
   {:url "url", :data "data"})))

(s/fdef
 add-compilation-cache
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/url :user/data]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/url :user/data])))
 :ret
 (s/keys))

(defn
 clear-compilation-cache
 "Clears seeded compilation cache."
 ([]
  (clear-compilation-cache
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (clear-compilation-cache
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "clearCompilationCache"
   params
   {})))

(s/fdef
 clear-compilation-cache
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
 set-spc-transaction-mode
 "Sets the Secure Payment Confirmation transaction mode.\nhttps://w3c.github.io/secure-payment-confirmation/#sctn-automation-set-spc-transaction-mode\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :mode | null"
 ([]
  (set-spc-transaction-mode
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [mode]}]
  (set-spc-transaction-mode
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [mode]}]
  (cmd/command
   connection
   "Page"
   "setSPCTransactionMode"
   params
   {:mode "mode"})))

(s/fdef
 set-spc-transaction-mode
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/mode]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/mode])))
 :ret
 (s/keys))

(defn
 generate-test-report
 "Generates a report for testing.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :message | Message to be displayed in the report.\n  :group   | Specifies the endpoint group to deliver the report to. (optional)"
 ([]
  (generate-test-report
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [message group]}]
  (generate-test-report
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [message group]}]
  (cmd/command
   connection
   "Page"
   "generateTestReport"
   params
   {:message "message", :group "group"})))

(s/fdef
 generate-test-report
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [:user/message]
    :opt-un
    [:user/group]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [:user/message]
    :opt-un
    [:user/group])))
 :ret
 (s/keys))

(defn
 wait-for-debugger
 "Pauses page execution. Can be resumed using generic Runtime.runIfWaitingForDebugger."
 ([]
  (wait-for-debugger
   (c/get-current-connection)
   {}))
 ([{:as params, :keys []}]
  (wait-for-debugger
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys []}]
  (cmd/command
   connection
   "Page"
   "waitForDebugger"
   params
   {})))

(s/fdef
 wait-for-debugger
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
 set-intercept-file-chooser-dialog
 "Intercept file chooser requests and transfer control to protocol clients.\nWhen file chooser interception is enabled, native file chooser dialog is not shown.\nInstead, a protocol event `Page.fileChooserOpened` is emitted.\n\nParameters map keys:\n\n\n  Key      | Description \n  ---------|------------ \n  :enabled | null"
 ([]
  (set-intercept-file-chooser-dialog
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [enabled]}]
  (set-intercept-file-chooser-dialog
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [enabled]}]
  (cmd/command
   connection
   "Page"
   "setInterceptFileChooserDialog"
   params
   {:enabled "enabled"})))

(s/fdef
 set-intercept-file-chooser-dialog
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys :req-un [:user/enabled]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys :req-un [:user/enabled])))
 :ret
 (s/keys))
