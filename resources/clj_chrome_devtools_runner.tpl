(ns clj-chrome-devtools-runner
  (:require [cljs.test :refer [run-tests]]
            __REQUIRE_NAMESPACES__))

(def PRINTED (atom []))

(defn get-printed []
  (let [v @PRINTED]
    (reset! PRINTED [])
    (clj->js v)))

(def screenshot-number (atom {:name nil :number 0}))

(defn screenshot-file-name []
  (let [test-name (-> (cljs.test/get-current-env) :testing-vars first meta :name)]
    (str test-name
         (:number (swap! screenshot-number
                         (fn [{:keys [name number]}]
                           {:name test-name
                            :number (if (= name test-name)
                                      (inc number)
                                      0)})))
         ".png")))

(defn screenshot [& file-name]
  (let [file-name (or file-name (screenshot-file-name))]
    (js/Promise.
     (fn [resolve _]
       (aset js/window "CLJ_SCREENSHOT_NAME" file-name)
       (aset js/window "CLJ_SCREENSHOT_RESOLVE" resolve)))))

(aset js/window "screenshot" screenshot)

(defn run-chrome-tests []
  (set! *print-fn* (fn [& msg] (swap! PRINTED conj (apply str msg))))
  (run-tests __TEST_NAMESPACES__ ))
