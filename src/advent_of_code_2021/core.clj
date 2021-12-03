(ns advent-of-code-2021.core
  (:gen-class)
  (:require   [clojure.data.csv :as csv]))

(declare count-increases input)

(defn -main
  "I will tell you the number of increases in that there input.txt file."
  []
  (println (count-increases input)))

(def input-txt
  "day-2.txt")

(defn parse-directions
  [[direction distance]]
  [(keyword direction) (read-string distance)])

(def input
  (map parse-directions (csv/read-csv (slurp input-txt) :separator \ )))
  ;;  todo: map the second value in input to an integer


(def submarine
  {:x 0 ; horizontal position
   :y 0 ; depth below origin
   })


(defn- move-sub-forward [sub distance]
  {:x (+ (:x sub) distance)
   :y (:y sub)})

(defn- move-sub-down [sub distance]
  {:x (:x sub)
   :y (+ (:y sub) distance)})

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
