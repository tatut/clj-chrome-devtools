(ns clj-chrome-devtools.chrome-test
  "Some tests with an actual running Chrome"
  (:require [clj-chrome-devtools.automation.fixture :refer [create-chrome-fixture]]
            [clj-chrome-devtools.automation :refer :all]
            [clojure.test :as t :refer [deftest is testing]]
            [clojure.java.io :as io]))

(defonce chrome-fixture (create-chrome-fixture))
(t/use-fixtures :each chrome-fixture)

(defn load-test-page []
  (to (str "file://" (.getAbsolutePath (io/file "test/test-page.html")))))

(deftest simple-page-load
  (load-test-page)
  (is (= (map text-of (sel "ul#thelist li")) '("foo" "bar" "baz"))))

(deftest selectors
  (load-test-page)
  (testing "Selectors work in place of node-refences"
    (is (= "0" (text-of "#counter")))
    (click [:div.countertest :button.increment])
    (is (= "1" (text-of [:#counter])))
    (click (sel1 "button.increment"))
    (is (= "2" (text-of "div#counter")))))

(deftest input
  (load-test-page)
  (testing "Typing into an input field works"
    (is (= "NO GREETING YET" (text-of "#greeting")))
    (doseq [txt ["old friend"
                 "foo@bar"
                 "keys [ that require $ modifiers"]]
      (input-text [:#greeter :input] txt)
      (click "#greeter button")
      (is (= (str "Hello, " txt "!") (text-of [:#greeting])))
      (clear-text-input [:#greeter :input]))))
