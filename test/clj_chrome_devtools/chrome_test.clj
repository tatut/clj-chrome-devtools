(ns clj-chrome-devtools.chrome-test
  "Some tests with an actual running Chrome"
  (:require [clj-chrome-devtools.automation.fixture :refer [create-chrome-fixture]]
            [clj-chrome-devtools.automation :refer :all]
            [clojure.test :as t :refer [deftest is]]
            [clojure.java.io :as io]))

(t/use-fixtures :each (create-chrome-fixture))

;; Very primitive test to just check that chrome is up and running
;; and can inspect a page.

(deftest simple-page-load
  (to (str "file://" (.getAbsolutePath (io/file "test/test-page.html"))))
  (is (= (map text-of (sel "ul#thelist li")) '("foo" "bar" "baz"))))
