(ns clj-chrome-devtools.impl.connection-test
  (:require [clj-chrome-devtools.automation :refer [create-automation current-automation evaluate
                                                    sel text-of to]]
            [clj-chrome-devtools.automation.fixture :refer [create-chrome-fixture]]
            [clj-chrome-devtools.impl.connection :as c]
            [clj-chrome-devtools.impl.util :as util]
            [clojure.java.io :as io]
            [clojure.spec.test.alpha :as stest]
            [clojure.string :refer [includes? join]]
            [clojure.test :as t :refer [deftest is testing]]
            [taoensso.timbre :as log])
  (:import [org.eclipse.jetty.websocket.api WebSocketException]))

(stest/instrument)

;; We need to start Chrome up listening for devtools connections on a random free port, and then
;; pass the same port number to c/connect.
(defonce port (util/random-free-port))

(defonce chrome-fixture (create-chrome-fixture {:remote-debugging-port port}))
(t/use-fixtures :each chrome-fixture)

(defn- reset-log! []
  (let [log (atom (vector))]
    (log/merge-config! {:appenders {:println {:enabled? false}
                                    :atom {:async false
                                           :enabled? true
                                           :min-level nil
                                           :output-fn :inherit
                                           :fn (fn [data]
                                                 (let [{:keys [output-fn]} data
                                                       formatted-output-str (output-fn data)]
                                                   (swap! log conj formatted-output-str)))}}})
    log))

(defn- make-conn
  "The main reason we need this is so it’ll use the value of the port var. See the comment on that
  var above."
  ([] (make-conn {}))
  ([client-config]
   (let [client (c/make-ws-client client-config)
         max-wait-time-ms 1000]
     (c/connect "localhost" port max-wait-time-ms client))))

(deftest logging
  (testing "messages that are larger than the max should cause an error to be logged"
    (testing "default limit (1MB)"
      (let [timeout-ms 1000 ; Specify a short timeout because this error case would otherwise cause
                            ; evaluate to hang until its default timeout of 60 seconds elapses,
                            ; which would just be too slow.
            log (reset-log!)]
        (is (thrown-with-msg? RuntimeException
                              #"^Timeout!.+"
                              (evaluate @current-automation
                                        "Array(1024 ** 2).fill(Math.random()).toString()"
                                        timeout-ms)))
        (let [output (join @log)]
          (is (re-seq #".+ERROR.+MessageTooLargeException.+message size.+exceeds maximum size.+"
                      output))
          (is (re-seq #".+WARN.+WebSocket connection closed with status code 1009 \(Too large\) and reason: Text message size.+exceeds maximum size.+"
                      output)))))
    (testing "specified limit (2MB)"
      (let [conn (make-conn {:max-msg-size-mb (* 1024 1024 2)})
            automation (create-automation conn)
            eval-timeout-ms 2000 ; Specify a short timeout because this error case would otherwise
                                 ; cause evaluate to hang until its default timeout of 60 seconds
                                 ; elapses, which would just be too slow.
            log (reset-log!)]
        (is (thrown-with-msg? RuntimeException
                              #"^Timeout!.+"
                              (evaluate automation
                                        "Array(1024 ** 2).fill(Math.random()).toString()"
                                        eval-timeout-ms)))
        (let [output (join @log)]
          (is (re-seq #".+ERROR.+MessageTooLargeException.+message size.+exceeds maximum size.+"
                      output))
          (is (re-seq #".+WARN.+WebSocket connection closed with status code 1009 \(Too large\) and reason: Text message size.+exceeds maximum size.+"
                      output)))))))

(def test-page
  (io/resource "test-page.html"))

(deftest closeable
  (testing "Connection objects should work with (be closed by) with-open"
    (let [conn (make-conn)
          auto (create-automation conn)]
      (with-open [conn conn]
        ; Simple validation that the connection works
        (to auto test-page)
        (is (= (map #(text-of auto %)
                    (sel auto "ul#thelist li"))
               '("foo" "bar" "baz"))))
      (is (thrown-with-msg? WebSocketException #"current state \[CLOSED\]" (to auto test-page))))))
