(ns advent-of-code-2021.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code-2021.core :refer [count-window
                                              compare-depths
                                              count-increases]]))





;;
;; Day 1
;;

(deftest count-window-test
  (testing "Returns the sum of three values"
    (is (= (count-window [100 101 102]) 303))))

(deftest compare-depths-test
  (testing "Doesn't count decreases"
    (is (= (compare-depths [101 101 101] [100 100 100]) 0)))
  (testing "Doesn't count samesies"
    (is (= (compare-depths [100 100 100] [100 100 100]) 0)))
  (testing "Counts increases"
    (is (= (compare-depths [100 100 100] [101 101 101]) 1))))

(def test-input
  "Test input with 5 increases in consecutive sums of 3 entries."
  [199 200 208 210 200 207 240 269 260 263])

 
 
(deftest count-increases-test
  (testing "Counts increases in test-input correctly"
    (is (= (count-increases test-input) 5))))
