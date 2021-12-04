(ns advent-of-code-2021.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code-2021.core :refer [gamma epsilon oxygen-rating co2-rating;; day 3
                                              move-sub submarine follow-map ;; day 2
                                              count-window compare-depths count-increases]])) ;; day 1


;;
;; Day 3
;;

(def test-input-day-3
  [2r00100
   2r11110
   2r10110
   2r10111
   2r10101
   2r01111
   2r00111
   2r11100
   2r10000
   2r11001
   2r00010
   2r01010])

(deftest gamma-test
  (testing "gamma works"
    (is (= (gamma test-input-day-3) 22))))
(deftest epsilon-test
  (testing "epsilon works"
    (is (= (epsilon test-input-day-3) 9))))

(deftest oxy-test
  (testing "calculate oxygen rating"
    (is (= (oxygen-rating test-input-day-3) 23))))
(deftest co2-test
  (testing "calculate CO2 rating"
    (is (= (co2-rating test-input-day-3) 10))))

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
