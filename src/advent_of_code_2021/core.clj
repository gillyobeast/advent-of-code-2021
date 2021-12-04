(ns advent-of-code-2021.core
  (:gen-class)
  (:require   [clojure.data.csv :as csv]))


(declare day-3-answer)

(defn -main
  "I will tell you answer to today's puzzle."
  []
  (println (day-3-answer)))

(def input-txt
  "input/day-3.txt")


;;
;; Day 3
;;

(defn get-input []
  (map
   #(Integer/parseInt % 2)
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

(defn input-bits [input] (map #(bits % number-of-bits) input))

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
  (count-set-bits (input-bits input)))

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


(defn gamma
  "returns the gamma rate of the input"
  [input]
  (Integer/parseInt (apply str (get-most-common-bits input)) 2))


(defn epsilon
  "returns the epsilon rate of the input"
  [input]
  (Integer/parseInt (apply str (get-least-common-bits input)) 2))

(defn day-3-answer
  []
  (* (gamma input) (epsilon input)))


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
