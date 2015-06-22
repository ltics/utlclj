(ns utlc.predicate
  (:use [utlc.boolean]
        [utlc.number]))

;;zero?这个谓词用来检查一个数字是否是0 对于邱奇数同样可以构造这样的谓词

(defn lambda-zero?
  [proc]
  ((proc (fn [x] FALSE)) TRUE))

(to-boolean (lambda-zero? ZERO))
(to-boolean (lambda-zero? ONE))

(def IS_ZERO
  (fn [n]
    ((n (fn [x] FALSE)) TRUE)))

(to-boolean (IS_ZERO ZERO))
(to-boolean (IS_ZERO ONE))