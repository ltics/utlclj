(ns utlc.withoutrecursion
  ("use" [utlc.number]
        [utlc.boolean]
        [utlc.list]
        [utlc.sortedpair]
        [utlc.withoutrecursion]
        [utlc.numericalcomputation]))

;;由于理解Y-comb有一定的难度 有时候我们可以不用递归来进行一些操作 比如mod这个操作 只要n<=m 就不断从m中减去n 并且总是检查是否满足继续减的条件 我们无法知道需要执行几次 但是最多m次

;;下面的实现就是将m减去m次n 并且每次减去操作之前都先检查n<=m这个条件

(defn decrease
  [m n]
  (if (<= n m)
    (- m n)
    m))

(decrease (decrease (decrease (decrease 17 5) 5) 5) 5)

(def NORECUR-MOD
  (fn [m]
    (fn [n]
      ((m (fn [x]
             (((IF ((IS_LESS_OR_EQUAL n) x))
               ((SUBSTRACT x) n))
              x)))
       m))))

(to-integer ((NORECUR-MOD FIVE) TWO))
(to-integer ((NORECUR-MOD ((POWER THREE) THREE)) ((ADD THREE) TWO)))

;;这个实现比用Y组合子的版本要易懂的多 但是带来的问题就是效率低下
;;这个非递归的实现与递归实现的mod操作不是外延等价的 对于n为0的情况 两者返回值不同

;;(to-integer ((MOD THREE) ZERO)) ;;java.lang.StackOverflowError 无穷递归
(to-integer ((NORECUR-MOD THREE) ZERO)) ;;3

;;不使用递归实现range操作

(defn countdown
  [pair]
  [(cons (last pair) (first pair)) (dec (last pair))])

(countdown (countdown (countdown [[] 10])))

(def COUNTDOWN
  (fn [p]
    ((PAIR ((UNSHIFT (LEFT p)) (RIGHT p))) (DECREMENT (RIGHT p)))))

(map to-integer
     (to-vector
      (LEFT (let [TEN ((MULTIPLY FIVE) TWO)]
              (COUNTDOWN (COUNTDOWN (COUNTDOWN ((PAIR EMPTY) TEN))))))))

(to-integer
 (RIGHT (let [TEN ((MULTIPLY FIVE) TWO)]
          (COUNTDOWN (COUNTDOWN (COUNTDOWN ((PAIR EMPTY) TEN)))))))

(def NORECUR-RANGE
  (fn [m]
    (fn [n]
      (LEFT (((INCREMENT ((SUBSTRACT n) m))
              COUNTDOWN)
             ((PAIR EMPTY) n))))))

(let [TEN ((MULTIPLY FIVE) TWO)]
  (map to-integer (to-vector ((NORECUR-RANGE FIVE) TEN))))

;;如果我们要用10和countdown产生一个5到10之间的range 那么countdown的结果应该是([5, 6, 7, 8, 9, 10], 4) 也就是说countdown的次数要执行10-5+1也就是6次 然后取左边的列表结果 上面非递归的range实现就是这个思路

;;下面是这个思路的非lambda版本

(countdown (countdown (countdown (countdown (countdown (countdown [[] 10]))))))

;;在number中实现的church numeral本身就是一种可以重复对某一个值执行一定次数行为的逻辑定义

(let [SIX ((ADD ONE) FIVE)]
  ((SIX countdown) [[] 10]))
