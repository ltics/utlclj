(ns ultc.numericalcomputation
  (:use [ultc.number]
        [ultc.sortedpair]
        [ultc.predicate]
        [ultc.boolean]))

;;now we have
;;numbers
;;booleans
;;if structure
;;pair data structure

;;we are ready to implement some advanced stuff such as numerical computation

;;inc操作
(def INCREMENT
  (fn [n]
    (fn [p]
      (fn [x]
        (p ((n p) x))))))

;;实现的思路其实很简单 就是用同样的proc在church numeral外面再操作一次 因为church numeral的定义就是操作的次数来表示数量这个概念

(to-integer (INCREMENT FIVE))

;;要实现dec操作先来看一个思路

(defn slide
  [pair]
  (vector (last pair) (+ 1 (last pair))))

(slide (slide (slide [0 0])))

;;可以看到用滑窗可以实现一个有序对的左边元素始终比右边元素小1 left就是right的dec操作的结果

(def SLIDE
  (fn [p]
    ((PAIR (RIGHT p))
      (INCREMENT (RIGHT p)))))

(def DECREMENT
  (fn [n]
    (LEFT ((n SLIDE) ((PAIR ZERO) ZERO)))))

(to-integer (DECREMENT FIVE))

;;now can implement add sub mul power use inc and dec operation

;;m + n可以理解为在m基础上进行n次inc操作
(def ADD
  (fn [m]
    (fn [n]
      ((n INCREMENT) m))))

(to-integer ((ADD ONE) TWO))

;;m - n可以理解为在m基础上进行n此dec操作
(def SUBSTRACT
  (fn [m]
    (fn [n]
      ((n DECREMENT) m))))

(to-integer ((SUBSTRACT TWO) ONE))

;;m * n可以理解为在0的基础上进行了n次加m操作
(def MULTIPLY
  (fn [m]
    (fn [n]
      ((n (ADD m)) ZERO))))

(to-integer ((MULTIPLY THREE) THREE))

;;m^n可以理解为在1的基础上进行了n此乘m操作
(def POWER
  (fn [m]
    (fn [n]
      ((n (MULTIPLY m)) ONE))))

(to-integer ((POWER THREE) THREE))

;;implement mod operation

(defn normal-mod
  [m, n]
  (if (<= n m)
    (mod (- m n) n)
    m))

(normal-mod 17 5)

;;要用lambda实现mod操作首先要实现<=操作

(defn less-or-equal?
  [m n]
  (<= (- m n) 0))

(less-or-equal? 1 3)

;;可以下面的代码看到 对于现在的ULTC系统 是不存在负数的 如果一个小数减去一个大数 那么减到ZERO了之后 由于ZERO的定义中不会执行传入的proc 所以之后所有对ZERO的操作都会一直返回ZERO

(to-integer (DECREMENT ZERO))

;;所以只要判断m减去n的结果是不是0就可以在当前的ultc体系中判断是否是小于等于了

(def IS_LESS_OR_EQUAL
  (fn [m]
    (fn [n]
      (IS_ZERO ((SUBSTRACT m) n)))))

(to-boolean ((IS_LESS_OR_EQUAL ONE) THREE))
(to-boolean ((IS_LESS_OR_EQUAL THREE) ONE))

;;so let's just finish mod operation

(def MOD
  (fn [m]
    (fn [n]
      (((IF ((IS_LESS_OR_EQUAL n) m))
         ((MOD ((SUBSTRACT m) n)) n)) m))))

;;上面这种实现会出现问题
;;CompilerException java.lang.StackOverflowError
;;因为传给IF的两个表达式不是lazy的 不管条件如何两个表达式都会求值 所以递归会一直进行 知道栈溢出了

;;回忆一下外延等价

;;外延等价
(def p (fn [n] (* n 2)))
(def q (fn [x] (p x)))
(p 5)
(q 5)

;;因为MOD最后返回的也是一个lambda 所以根据外延等价的性质 将MOD包在一个lambda内与直接用MOD将会得到一样的求值结果 但是包在lambda内的好处是可以将这个表达式lazy住 防止在传入给IF时就求值 造成stackoverflow
;;lazy evaluation的基础也就是lambda 或者说是一个thunk 就是包在一个惰性上下文中

(def MOD
  (fn [m]
    (fn [n]
      (((IF ((IS_LESS_OR_EQUAL n) m))
         (fn [x] (((MOD ((SUBSTRACT m) n)) n) x))) m))))

(to-integer ((MOD FIVE) TWO))
(to-integer ((MOD FIVE) THREE))

;;由于我们在定义MOD的时候用到了clojure内建的语法特性def 而在ULTC体系中是没有变量定义的 我们可以用著名的烧脑组合子Y-Combinator来处理匿名递归

(def Y
  (fn [f]
    ((fn [x]
       (f (x x)))
      (fn [x]
        (f (x x))))))

;;如果直接使用依然会出现无穷递归的问题 所以依然需要在调用自身的地方包一层惰性上下文 用Z-Combinator来表示

(def Z
  (fn [f]
    ((fn [x]
       (f (fn [y]
            ((x x) y))))
      (fn [x]
        (f (fn [y]
             ((x x) y)))))))

(def MOD
  (Z (fn [f]
       (fn [m]
         (fn [n]
           (((IF ((IS_LESS_OR_EQUAL n) m))
              (fn [x] (((f ((SUBSTRACT m) n)) n) x))) m))))))

(to-integer ((MOD FIVE) TWO))
(to-integer ((MOD FIVE) THREE))
(to-integer ((MOD ((POWER THREE) THREE)) ((ADD THREE) TWO)))
;;really cool right
