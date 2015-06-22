(ns utlc.sortedpair
  (:use [utlc.number]))

;;有序对

;;类似于tuple的数据结构

(def PAIR
  (fn [x]
    (fn [y]
      (fn [f]
        ((f x) y)))))

(def LEFT
  (fn [p]
    (p (fn [x]
         (fn [y]
           x)))))

(def RIGHT
  (fn [p]
    (p (fn [x]
         (fn [y]
           y)))))

;;pair结构以及两个取值操作的实现利用了闭包性

(def my-pair ((PAIR THREE) FIVE))
(to-integer (LEFT my-pair))
(to-integer (RIGHT my-pair))