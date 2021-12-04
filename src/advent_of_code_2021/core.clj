(ns advent-of-code-2021.core
  (:gen-class)
  (:require   [clojure.data.csv :as csv]))


(declare power-consumption oxygen-rating co2-rating)

(defn -main
  "I will tell you answer to today's puzzle."
  []
  (println "Power consumption:       " (power-consumption))
  (let [oxygen-rating (oxygen-rating)
        co2-rating (co2-rating)]
    (println "Oxygen generator rating: " oxygen-rating)
    (println "CO2 Scrubber rating:     " co2-rating)
    (println "Day 3 part 2 answer:     " (* oxygen-rating co2-rating))))

(def input-txt
  "input/day-3.txt")


;;
;; Day 3
;;

(declare parse-binary)
(defn get-input []
  (map
   parse-binary
   (map first
        (csv/read-csv (slurp input-txt) :separator \n))))

(def input (get-input))

(def number-of-bits 5)

;; to get the actual answer from main, set number-of-bits to 12. Sorry, it's late.
; (def number-of-bits 12)

(defn bits [n s]
  (reverse
   (take s
         (map
          (fn [i] (bit-and 0x01 i))
          (iterate
           (fn [i] (bit-shift-right i 1))
           n)))))

(defn get-bits [input] (map #(bits % number-of-bits) input))

(defn add-counts
  [counts bits]
  (map + counts bits))

(defn count-set-bits
  [input]
  (loop [[x & remaining] input
         counts (repeat number-of-bits 0)]
    (comment println x counts)
    (if (nil? x)
      counts
      (recur remaining (add-counts counts x)))))

(defn get-bit-counts
  [input]
  (count-set-bits (get-bits input)))

(defn get-most-common-bit
  [input count]
  (if (> count (/ (clojure.core/count input) 2))
    1
    0))

(defn get-most-common-bits
  [input]
  (map #(get-most-common-bit input %) (get-bit-counts input)))

(defn get-least-common-bits
  [input]
  (map #(- 1 %) (get-most-common-bits input)))

(defn- parse-binary [int]
  (Integer/parseInt int 2))

(defn gamma
  "returns the gamma rate of the input"
  [input]
  (parse-binary (apply str (get-most-common-bits input))))


(defn epsilon
  "returns the epsilon rate of the input"
  [input]
  (parse-binary (apply str (get-least-common-bits input))))

(defn power-consumption
  []
  (* (gamma input) (epsilon input)))


(defn- calculate-oxy [bits])

(defn oxygen-rating
  ([] (oxygen-rating input))
  ([input] (calculate-oxy (get-bits input))))

(defn- calculate-co2 [bits])

(defn co2-rating
  ([] co2-rating input)
  ([input] (calculate-co2 (get-bits input))))


;;
;; Day 2
;;



(def submarine
  {:x 0   ; horizontal position
   :y 0   ; depth below origin
   :aim 0}) ; direction we're pointing 



(defn- move-sub-forward [sub distance]
  {:x (+ (:x sub) distance)
   :y (+ (:y sub) (* (:aim sub) distance))
   :aim (:aim sub)})

(defn- move-sub-down [sub distance]
  {:x (:x sub)
   :y (:y sub)
   :aim (+ (:aim sub) distance)})

(defn move-sub
  "Moves submarine sub distance units in direction direction. Duh."
  [sub [direction distance]]
  (comment println "Moving" sub direction distance)
  (case direction
    :forward (move-sub-forward sub distance)
    :down (move-sub-down sub distance)
    :up (move-sub-down sub (- distance))
    sub))

(defn follow-map
  [map initial-sub]
  (loop [[next & remaining-steps] map
         sub initial-sub]
    (if (nil? next)
      sub
      (do
        (comment println sub next (first remaining-steps))
        (recur remaining-steps (move-sub sub next))))))

(defn day-2-1-answer
  []
  (let [sub (follow-map input submarine)]
    (* (:x sub) (:y sub))))

;; 
;; Day 1 
;;

(defn count-window
  "Returns the sum over a window of three values."
  [[first second third]]
  ;; (println first second third)
  (+ first second third))

(defn compare-depths
  "Compare two windows of sonar depths, returning 1 if second window is deeper than the first."
  [first second]
  (if (< (count-window first) (count-window second))
    1
    0))

(defn count-increases
  "Counts the number of times consecutive windows of 3 depths increases in list of depths."
  [depths]
  (loop [count 0
         [first & rest] depths]
    (if (nil? (nth rest 2 nil))
      count
      (recur
       (let [window-1 (cons first (take 2 rest))
             window-2 (take 3 rest)
             increase? (compare-depths window-1 window-2)]
        ;;  (println window-1 window-2 " - increase?" increase? " - count" count)
         (+ count increase?))
       rest))))
