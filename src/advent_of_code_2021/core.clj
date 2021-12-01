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

(defn compare-depths
  "Compare two sonar depths, returning 1 if second is deeper than the first."
  [first second]
  (if (< first second)
    1
    0))

(defn count-increases
  "Counts the number of increases in list of depths."
  [depths]
  (loop [count 0
         [first & rest] depths]
    (if (empty? rest)
      count
      (recur
       (+ count (compare-depths first (clojure.core/first rest)))
       rest))))
