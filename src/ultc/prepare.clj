(ns ultc.prepare)

;;programing with lambda
;;一个lambda就只是一个单参数的匿名函数 LC中的基础元素就是一个单参数的匿名函数 任何结构都由这些神奇的基础元素组成

((fn [x]
   (+ x 2)) 1)

((fn [x y]
   (+ x y)) 3 4)

(((fn [x]
    (fn [y]
      (+ x y))) 3) 4)

;;外延等价
(def p (fn [n] (* n 2)))
(def q (fn [x] (p x)))
(p 5)
(q 5)

;;p q是外延等价的 因为FP中函数的返回值只依赖参数
