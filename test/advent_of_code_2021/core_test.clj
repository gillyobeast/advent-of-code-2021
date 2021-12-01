(ns advent-of-code-2021.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code-2021.core :refer [compare-depths count-increases]]))

(deftest compare-depths-test
  (testing "Doesn't count decreases"
    (is (= (compare-depths 101 100) 0)))
  (testing "Doesn't count samesies"
    (is (= (compare-depths 100 100) 0)))
  (testing "Counts increases"
    (is (= (compare-depths 100 101) 1))))

(def test-input
  "Test input with 3 increases."
  [100 101 102 101 102 102 101 100])

(deftest count-increases-test
  (testing "Counts increases in test-input correctly"
    (is (= (count-increases test-input) 3))))