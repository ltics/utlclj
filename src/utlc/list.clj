(ns utlc.list
  (:use [utlc.sortedpair]
        [utlc.boolean]
        [utlc.number]
        [utlc.numericalcomputation]))

;;使用pair来构造list

(def EMPTY
  ((PAIR TRUE) TRUE))

(def UNSHIFT
  (fn [l]
    (fn [x]
      ((PAIR FALSE) ((PAIR x) l)))))

(def IS_EMPTY
  LEFT)

(def FIRST
  (fn [l]
    (LEFT (RIGHT l))))

(def REST
  (fn [l]
    (RIGHT (RIGHT l))))

(def my-list
  ((UNSHIFT
     ((UNSHIFT
        ((UNSHIFT EMPTY) THREE)) TWO)) ONE))

;;UNSHIFT其实就是lisp中的cons操作

(cons 1 (cons 2 (cons 3 '())))
;;=> (1 2 3)

;;因为判断是否是空列表就是取有序对左边元素操作 所以有序对左边的元素是用来标明列表是否为空的布尔值 有序对的右边的元素又是一个有序对其中这个有序对左边的元素是当前列表的头元素 而右边的元素是rest部分 也是一个列表结构

(to-integer (FIRST my-list))
(to-integer (FIRST (REST my-list)))
(to-integer (FIRST (REST (REST my-list))))
(to-boolean (IS_EMPTY my-list))
(to-boolean (IS_EMPTY EMPTY))

(defn to-vector
  [proc]
  (loop [proc proc
         array []]
    (if (to-boolean (IS_EMPTY proc))
      array
      (recur (REST proc) (conj array (FIRST proc))))))

(map to-integer (to-vector my-list))

;;实现了list 我们来实现以下更加高级的range结构
;;实现一个proc 可以构建range范围内所有元素的列表

(defn normal-range
  [m n]
  (if (<= m n)
    (cons m (range (inc m) n))
    []))

(normal-range 1 3)

;;有需要用到递归所以需要用到Y-comb

(def RANGE
  (Z (fn [f]
       (fn [m]
         (fn [n]
           (((IF ((IS_LESS_OR_EQUAL m) n))
              (fn [x] (((UNSHIFT ((f (INCREMENT m)) n)) m) x)))
             EMPTY))))))

(def my-range ((RANGE ONE) FIVE))
(map to-integer (to-vector my-range))

;;先在要实现对list的map操作
;;首先实现fold操作来辅助实现map操作

(def FOLD
  (Z (fn [f]
       (fn [l]
         (fn [x]
           (fn [g]
             (((IF (IS_EMPTY l))
                x)
               (fn [y]
                 (((g (((f (REST l)) x) g)) (FIRST l)) y)))))))))

(to-integer (((FOLD ((RANGE ONE) FIVE)) ZERO) ADD))
(to-integer (((FOLD ((RANGE ONE) FIVE)) ONE) MULTIPLY))

;;map操作就是fold操作中不断往一个列表中加入结果而不是reduce成一个值

(def MAP
  (fn [k]
    (fn [f]
      (((FOLD k) EMPTY)
        (fn [l]
          (fn [x]
            ((UNSHIFT l) (f x))))))))

(def my-list ((MAP ((RANGE ONE) FIVE)) INCREMENT))
(map to-integer (to-vector my-list))

(def PUSH
  (fn [l]
    (fn [x]
      (((FOLD l) ((UNSHIFT EMPTY) x)) UNSHIFT))))

(map to-integer (to-vector (def my-list
                             ((PUSH
                                ((PUSH
                                   ((PUSH EMPTY) THREE)) TWO)) ONE))))

;;PUSH类似于conj UNSHIFT类似于cons 前者将元素放在容器的尾部 后者将元素放在容器的头部
