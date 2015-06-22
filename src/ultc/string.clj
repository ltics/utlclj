(ns ultc.string
  (:use [ultc.number]
        [ultc.numericalcomputation]
        [ultc.list]
        [ultc.boolean]))

;;字符串只要表示成邱奇数的列表 然后建立好数字显示的字符的映射关系即可

;;0-9 -> '0' ~ '9'
;;10-14 -> ['B' 'F' 'i' 'z' 'u']

(def TEN
  ((MULTIPLY TWO) FIVE))

(def B TEN)
(def F (INCREMENT B))
(def I (INCREMENT F))
(def U (INCREMENT I))
(def ZED (INCREMENT U))

(def FIZZ ((UNSHIFT ((UNSHIFT ((UNSHIFT ((UNSHIFT EMPTY) ZED)) ZED)) I)) F))
(def BUZZ ((UNSHIFT ((UNSHIFT ((UNSHIFT ((UNSHIFT EMPTY) ZED)) ZED)) U)) B))
;;(def FIZZBUZZ ((UNSHIFT BUZZ) FIZZ)) ;;this is wrong
(def FIZZBUZZ ((UNSHIFT ((UNSHIFT ((UNSHIFT ((UNSHIFT BUZZ) ZED)) ZED)) I)) F))

(defn to-char
  [c]
  (let [index (to-integer c)]
    (nth "0123456789BFiuz" index)))

(defn to-string
  [s]
  (clojure.string/join (map to-char (to-vector s))))

(to-char ZED)
(to-string FIZZ)
(to-string BUZZ)
(to-string FIZZBUZZ)

;;将一个数字转换为一个字符串

(defn to-digis
  [n]
  (let [previous-digits (if (< n 10)
                          []
                          (to-digis (int (/ n 10))))]
    (conj previous-digits (mod n 10))))

(to-digis 125)

(def TO_DIGITS
  (Z (fn [f]
       (fn [n]
         ((PUSH (((IF ((IS_LESS_OR_EQUAL n) (DECREMENT TEN)))
                   EMPTY)
                  (fn [x]
                    ((f ((DIV n) TEN)) x))))
           ((MOD n) TEN))))))

(map to-integer (to-vector (TO_DIGITS FIVE)))
(map to-integer (to-vector (TO_DIGITS ((POWER FIVE) THREE))))

(to-string (TO_DIGITS FIVE))
(to-string (TO_DIGITS ((POWER FIVE) THREE)))