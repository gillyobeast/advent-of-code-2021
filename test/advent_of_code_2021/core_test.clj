(ns advent-of-code-2021.core-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.core :refer :all]))

(deftest compare-depths-test
  (testing "Doesn't count decreases"
    (is (= (compare-depths 101 100) 0)))
  (testing "Doesn't count samesies"
    (is (= (compare-depths 100 100) 0)))
  (testing "Counts increases"
    (is (= (compare-depths 100 101) 1))))