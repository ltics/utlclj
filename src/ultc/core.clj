(ns ultc.core
  (:gen-class))

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

;;church numeral
(def ZERO (fn [proc]
            (fn [x]
              x)))

(def ONE (fn [proc]
           (fn [x]
             (proc x))))

(def TWO (fn [proc]
           (fn [x]
             (proc (proc x)))))

(def THREE (fn [proc]
             (fn [x]
               (proc (proc (proc x))))))

(defn -main
  "just work with untyped lambda calculus"
  [& args])
