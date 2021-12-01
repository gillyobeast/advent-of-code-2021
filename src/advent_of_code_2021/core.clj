(ns advent-of-code-2021.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Hello, World!"))

(def input
  "Test input with 3 increases."
  [100 101 102 101 102 101 100])

;; write a function that compares two depths, 
;;    and returns 1 if there is an increase
;; then reduce that function over the input with initial of 0
