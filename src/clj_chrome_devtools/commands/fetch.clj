(ns clj-chrome-devtools.commands.fetch
  "A domain for letting clients substitute browser's network layer with client code."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::request-id
 string?)

(s/def
 ::request-stage
 #{"Request" "Response"})

(s/def
 ::request-pattern
 (s/keys
  :opt-un
  [::url-pattern
   ::resource-type
   ::request-stage]))

(s/def
 ::header-entry
 (s/keys
  :req-un
  [::name
   ::value]))

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
(defn
 disable
 "Disables the fetch domain."
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
   "Fetch"
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
 "Enables issuing of requestPaused events. A request will be paused until client\ncalls one of failRequest, fulfillRequest or continueRequest/continueWithAuth.\n\nParameters map keys:\n\n\n  Key                   | Description \n  ----------------------|------------ \n  :patterns             | If specified, only requests matching any of these patterns will produce\nfetchRequested event and will be paused until clients response. If not set,\nall requests will be affected. (optional)\n  :handle-auth-requests | If true, authRequired events will be issued and requests will be paused\nexpecting a call to continueWithAuth. (optional)"
 ([]
  (enable
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [patterns handle-auth-requests]}]
  (enable
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [patterns handle-auth-requests]}]
  (cmd/command
   connection
   "Fetch"
   "enable"
   params
   {:patterns "patterns", :handle-auth-requests "handleAuthRequests"})))

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
    [::patterns
     ::handle-auth-requests]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :opt-un
    [::patterns
     ::handle-auth-requests])))
 :ret
 (s/keys))

(defn
 fail-request
 "Causes the request to fail with specified reason.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :request-id   | An id the client received in requestPaused event.\n  :error-reason | Causes the request to fail with the given reason."
 ([]
  (fail-request
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id error-reason]}]
  (fail-request
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id error-reason]}]
  (cmd/command
   connection
   "Fetch"
   "failRequest"
   params
   {:request-id "requestId", :error-reason "errorReason"})))

(s/fdef
 fail-request
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
     ::error-reason]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::request-id
     ::error-reason])))
 :ret
 (s/keys))

(defn
 fulfill-request
 "Provides response to the request.\n\nParameters map keys:\n\n\n  Key               | Description \n  ------------------|------------ \n  :request-id       | An id the client received in requestPaused event.\n  :response-code    | An HTTP response code.\n  :response-headers | Response headers.\n  :body             | A response body. (optional)\n  :response-phrase  | A textual representation of responseCode.\nIf absent, a standard phrase mathcing responseCode is used. (optional)"
 ([]
  (fulfill-request
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [request-id response-code response-headers body response-phrase]}]
  (fulfill-request
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [request-id response-code response-headers body response-phrase]}]
  (cmd/command
   connection
   "Fetch"
   "fulfillRequest"
   params
   {:request-id "requestId",
    :response-code "responseCode",
    :response-headers "responseHeaders",
    :body "body",
    :response-phrase "responsePhrase"})))

(s/fdef
 fulfill-request
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
     ::response-code
     ::response-headers]
    :opt-un
    [::body
     ::response-phrase]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::request-id
     ::response-code
     ::response-headers]
    :opt-un
    [::body
     ::response-phrase])))
 :ret
 (s/keys))

(defn
 continue-request
 "Continues the request, optionally modifying some of its parameters.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | An id the client received in requestPaused event.\n  :url        | If set, the request url will be modified in a way that's not observable by page. (optional)\n  :method     | If set, the request method is overridden. (optional)\n  :post-data  | If set, overrides the post data in the request. (optional)\n  :headers    | If set, overrides the request headrts. (optional)"
 ([]
  (continue-request
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id url method post-data headers]}]
  (continue-request
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [request-id url method post-data headers]}]
  (cmd/command
   connection
   "Fetch"
   "continueRequest"
   params
   {:request-id "requestId",
    :url "url",
    :method "method",
    :post-data "postData",
    :headers "headers"})))

(s/fdef
 continue-request
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::request-id]
    :opt-un
    [::url
     ::method
     ::post-data
     ::headers]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::request-id]
    :opt-un
    [::url
     ::method
     ::post-data
     ::headers])))
 :ret
 (s/keys))

(defn
 continue-with-auth
 "Continues a request supplying authChallengeResponse following authRequired event.\n\nParameters map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :request-id              | An id the client received in authRequired event.\n  :auth-challenge-response | Response to  with an authChallenge."
 ([]
  (continue-with-auth
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id auth-challenge-response]}]
  (continue-with-auth
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id auth-challenge-response]}]
  (cmd/command
   connection
   "Fetch"
   "continueWithAuth"
   params
   {:request-id "requestId",
    :auth-challenge-response "authChallengeResponse"})))

(s/fdef
 continue-with-auth
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
     ::auth-challenge-response]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::request-id
     ::auth-challenge-response])))
 :ret
 (s/keys))

(defn
 get-response-body
 "Causes the body of the response to be received from the server and\nreturned as a single string. May only be issued for a request that\nis paused in the Response stage and is mutually exclusive with\ntakeResponseBodyForInterceptionAsStream. Calling other methods that\naffect the request or disabling fetch domain before body is received\nresults in an undefined behavior.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | Identifier for the intercepted request to get body for.\n\nReturn map keys:\n\n\n  Key             | Description \n  ----------------|------------ \n  :body           | Response body.\n  :base64-encoded | True, if content was sent as base64."
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
   "Fetch"
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
 take-response-body-as-stream
 "Returns a handle to the stream representing the response body.\nThe request must be paused in the HeadersReceived stage.\nNote that after this command the request can't be continued\nas is -- client either needs to cancel it or to provide the\nresponse body.\nThe stream only supports sequential read, IO.read will fail if the position\nis specified.\nThis method is mutually exclusive with getResponseBody.\nCalling other methods that affect the request or disabling fetch\ndomain before body is received results in an undefined behavior.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | null\n\nReturn map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :stream | null"
 ([]
  (take-response-body-as-stream
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [request-id]}]
  (take-response-body-as-stream
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [request-id]}]
  (cmd/command
   connection
   "Fetch"
   "takeResponseBodyAsStream"
   params
   {:request-id "requestId"})))

(s/fdef
 take-response-body-as-stream
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
  [::stream]))
