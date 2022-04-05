(ns clj-chrome-devtools.impl.connection-test
  (:require [clj-chrome-devtools.automation :refer [create-automation sel text-of to]]
            [clj-chrome-devtools.automation.fixture :refer [create-chrome-fixture]]
            [clj-chrome-devtools.impl.connection :as c]
            [clj-chrome-devtools.impl.util :as util]
            [clojure.java.io :as io]
            [clojure.spec.test.alpha :as stest]
            [clojure.test :as t :refer [deftest is testing]])
  (:import [java.util.concurrent CompletionException]))

(stest/instrument)

;; We need to start Chrome up listening for devtools connections on a random free port, and then
;; pass the same port number to c/connect.
(defonce port (util/random-free-port))

(defonce chrome-fixture (create-chrome-fixture {:remote-debugging-port port}))
(t/use-fixtures :each chrome-fixture)

(defn- make-conn
  "The main reason we need this is so it’ll use the value of the port var. See the comment on that
  var above."
  []
  (c/connect "localhost" port))


(def test-page
  (io/resource "test-page.html"))

(deftest closeable
  (testing "Connection objects should work with (be closed by) with-open"
    (let [conn (make-conn)
          auto (create-automation conn)]
      (with-open [_ conn]
        ;; Simple validation that the connection works
        (to auto test-page)
        (is (= (map #(text-of auto %)
                    (sel auto "ul#thelist li"))
               '("foo" "bar" "baz"))))
      (is (thrown-with-msg?
           CompletionException #"Output closed"
           (to auto test-page))))))
