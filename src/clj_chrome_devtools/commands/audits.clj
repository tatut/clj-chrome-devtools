(ns clj-chrome-devtools.commands.audits
  "Audits domain allows investigation of page violations and possible improvements."
  (:require [clojure.spec.alpha :as s]))
(defn
 get-encoded-response
 "Returns the response body and size if it were re-encoded with the specified settings. Only\napplies to images.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | Identifier of the network request to get content for.\n  :encoding   | The encoding to use.\n  :quality    | The quality of the encoding (0-1). (defaults to 1) (optional)\n  :size-only  | Whether to only return the size information (defaults to false). (optional)\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :body          | The encoded body as a base64 string. Omitted if sizeOnly is true. (optional)\n  :original-size | Size before re-encoding.\n  :encoded-size  | Size after re-encoding."
 ([]
  (get-encoded-response
   (clj-chrome-devtools.impl.connection/get-current-connection)
   {}))
 ([{:as params, :keys [request-id encoding quality size-only]}]
  (get-encoded-response
   (clj-chrome-devtools.impl.connection/get-current-connection)
   params))
 ([connection
   {:as params, :keys [request-id encoding quality size-only]}]
  (let
   [id__36878__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__36879__auto__
    "Audits.getEncodedResponse"
    ch__36880__auto__
    (clojure.core.async/chan)
    payload__36881__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__36878__auto__
     method__36879__auto__
     params
     {:request-id "requestId",
      :encoding "encoding",
      :quality "quality",
      :size-only "sizeOnly"})]
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
    clj-chrome-devtools.impl.connection/connection?)
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
