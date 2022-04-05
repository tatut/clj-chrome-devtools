(ns clj-chrome-devtools.automation-test
  (:require [clj-chrome-devtools.automation :as a]
            [clj-chrome-devtools.automation.fixture :refer [create-chrome-fixture]]
            [clojure.spec.test.alpha :as stest]
            [clojure.test :as t :refer [deftest is testing]]
            [clojure.string :as str]))

(stest/instrument)

(defonce chrome-fixture (create-chrome-fixture))
(t/use-fixtures :each chrome-fixture)

(deftest evaluate
  (testing "a call that takes longer than the default timeout should throw an exception"
    (let [[prior-default-timeout _] (reset-vals! a/default-timeout-ms 100)]
      (is (thrown-with-msg?
           RuntimeException
           #"^Timeout!.+"
           (a/evaluate "Array(1024 ** 3).fill().map(Math.random).length")))
      (reset! a/default-timeout-ms prior-default-timeout)))
  (testing "a call that takes longer than the supplied timeout should throw an exception"
    (is (thrown-with-msg?
         RuntimeException
         #"^Timeout!.+"
         (a/evaluate @a/current-automation
                    "Array(1024 ** 3).fill().map(Math.random).length"
                    100)))))


(deftest pdf-print-test
  (a/to "http://example.com")
  (a/print-pdf @a/current-automation "example.pdf")
  (let [pdf (slurp "example.pdf")]
    (is (str/starts-with? pdf "%PDF-1.4")) ; file exists and starts with proper PDF header
    (is (> (count pdf) 30000)))) ; file should be little over 30k
