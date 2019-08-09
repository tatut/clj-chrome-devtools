(ns clj-chrome-devtools.commands.audits
  "Audits domain allows investigation of page violations and possible improvements."
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.connection :as c]))
(defn
 get-encoded-response
 "Returns the response body and size if it were re-encoded with the specified settings. Only\napplies to images.\n\nParameters map keys:\n\n\n  Key         | Description \n  ------------|------------ \n  :request-id | Identifier of the network request to get content for.\n  :encoding   | The encoding to use.\n  :quality    | The quality of the encoding (0-1). (defaults to 1) (optional)\n  :size-only  | Whether to only return the size information (defaults to false). (optional)\n\nReturn map keys:\n\n\n  Key            | Description \n  ---------------|------------ \n  :body          | The encoded body as a base64 string. Omitted if sizeOnly is true. (optional)\n  :original-size | Size before re-encoding.\n  :encoded-size  | Size after re-encoding."
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
  (let
   [id__62694__auto__
    (clj-chrome-devtools.impl.define/next-command-id!)
    method__62695__auto__
    "Audits.getEncodedResponse"
    ch__62696__auto__
    (clojure.core.async/chan)
    payload__62697__auto__
    (clj-chrome-devtools.impl.define/command-payload
     id__62694__auto__
     method__62695__auto__
     params
     {:request-id "requestId",
      :encoding "encoding",
      :quality "quality",
      :size-only "sizeOnly"})]
   (c/send-command
    connection
    payload__62697__auto__
    id__62694__auto__
    (fn*
     [p1__62693__62698__auto__]
     (clojure.core.async/go
      (clojure.core.async/>!
       ch__62696__auto__
       p1__62693__62698__auto__))))
   (let
    [result__62699__auto__ (clojure.core.async/<!! ch__62696__auto__)]
    (if-let
     [error__62700__auto__ (:error result__62699__auto__)]
     (throw
      (ex-info
       (str
        "Error in command "
        method__62695__auto__
        ": "
        (:message error__62700__auto__))
       {:request payload__62697__auto__, :error error__62700__auto__}))
     (:result result__62699__auto__))))))

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
