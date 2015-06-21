(ns ultc.number)

;;在LC中可以只是用lambda来表示数字 叫做邱奇编码(church encoding)
;;使用church encoding表示的数为邱奇数(church numeral)

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

(def FOUR (fn [proc]
             (fn [x]
               (proc (proc (proc (proc x)))))))

(def FIVE (fn [proc]
             (fn [x]
               (proc (proc (proc (proc (proc x))))))))

;;当然church numeral和一般的自然数也可以进行转换
(defn to-integer
  [proc]
  ((proc (fn [n]
           (+ n 1))) 0))

;;其实就是将(+ n 1)这个过程作用在0上对应的次数返回对应的自然数
(to-integer ZERO)
(to-integer ONE)
(to-integer TWO)
(to-integer THREE)

