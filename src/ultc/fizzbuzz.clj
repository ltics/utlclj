(ns ultc.fizzbuzz
  (:use [ultc.number]
        [ultc.list]
        [ultc.string]
        [ultc.boolean]
        [ultc.predicate]
        [ultc.numericalcomputation]))

;;用已经实现了基础操作的ULTC来完成一个real world的任务 fizzbuzz

;;fizzbuzz in clojure

(map
  (fn [n]
    (cond
      (zero? (mod n 15)) (println "FizzBuzz")
      (zero? (mod n 3)) (println "Fizz")
      (zero? (mod n 5)) (println "Buzz")
      :else (println (str n))))
  (range 1 101))

;;fizzbuzz in ultc

(def FIFTEEN
  ((MULTIPLY THREE) FIVE))

(def HUNDRED
  (let [TEN ((MULTIPLY TWO) FIVE)]
    ((MULTIPLY TEN) TEN)))

(def solution
  ((MAP
     ((RANGE ONE) HUNDRED))
    (fn [n]
      (((IF (IS_ZERO ((MOD n) FIFTEEN)))
         FIZZBUZZ)
        (((IF (IS_ZERO ((MOD n) THREE)))
           FIZZ)
          (((IF (IS_ZERO ((MOD n) FIVE)))
             BUZZ)
            (TO_DIGITS n)))))))

(map #(println (to-string %)) (to-vector solution))

;;done :)
