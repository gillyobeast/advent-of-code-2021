(ns advent-of-code-2021.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code-2021.core :refer [gamma epsilon ;; day 3
                                              move-sub submarine follow-map ;; day 2
                                              count-window compare-depths count-increases]])) ;; day 1


;;
;; Day 3
;;

(def test-input-day-3
  [000000000100
   000000011110
   000000010110
   000000010111
   000000010101
   000000001111
   000000000111
   000000011100
   000000010000
   000000011001
   000000000010
   000000001010])

(deftest gamma-test
  (testing "gamma works"
    (is (= (gamma test-input-day-3) 22))))
(deftest epsilon-test
  (testing "epsilon works"
    (is (= (epsilon test-input-day-3) 22))))


;;
;; Day 2
;;

(def test-input-day-2
  '([:forward 5]    ;; 5  0  0 
    [:down 5]       ;; 5  5  5
    [:forward 8]    ;; 13 45 5
    [:up 3]         ;; 13 45 2
    [:down 8]       ;; 13 45 10
    [:forward 2]))  ;; 15 60 10 


(deftest move-sub-test
  (testing "Moves the sub appropriately"
    (is (= (move-sub {:x 0 :y 0 :aim 1} [:forward 5])
           {:x 5 :y 5 :aim 1}))

    (is (= (move-sub submarine [:down 5])
           {:x 0 :y 0 :aim 5}))

    (is (= (move-sub submarine [:up 2])
           {:x 0 :y 0 :aim -2}))))

(deftest follow-map-test
  (testing "Follows the test map to (15,10)"
    (is (= (follow-map test-input-day-2 submarine) {:x 15 :y 60 :aim 10}))))

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
