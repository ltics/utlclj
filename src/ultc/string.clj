(ns ultc.string
  (:use [ultc.number]
        [ultc.numericalcomputation]
        [ultc.list]))

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