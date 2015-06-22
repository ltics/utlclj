(ns utlc.fizzbuzz
  (:use [utlc.number]
        [utlc.list]
        [utlc.string]
        [utlc.boolean]
        [utlc.predicate]
        [utlc.numericalcomputation]))

;;用已经实现了基础操作的utlc来完成一个real world的任务 fizzbuzz

;;fizzbuzz in clojure

(map
  (fn [n]
    (cond
      (zero? (mod n 15)) (println "FizzBuzz")
      (zero? (mod n 3)) (println "Fizz")
      (zero? (mod n 5)) (println "Buzz")
      :else (println (str n))))
  (range 1 101))

;;fizzbuzz in utlc

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
;;加入不使用clojure内建的def来对一些重复使用的lambda进行替换 那么上面的fizzbuzz代码会展开为一大坨嵌套的lambda 普通的人类根本无法看懂这样的代码
