(ns advent-of-code-2021.core
  (:gen-class)
  (:require   [clojure.data.csv :as csv]
              [clojure.edn :as edn]))

(declare count-increases input)

(defn -main
  "I will tell you the number of increases in that there input.txt file."
  []
  (count-increases input))

(def input-txt
  "input.txt")

(def input
  (map edn/read-string (map first (csv/read-csv (slurp input-txt) :separator \n))))

  ;; todo: make -main do the thing

;; write a function that compares two depths, 
;;    and returns 1 if there is an increase
;; then reduce that function over the input with initial of 0
;;  nope, that's not gonna work - gotta apply it to each pair of the input.

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
