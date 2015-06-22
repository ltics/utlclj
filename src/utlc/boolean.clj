(ns utlc.boolean)

;;我们来从另一个角度理解一下布尔值的意义

(let [success true]
  (if success
    "happy"
    "sad"))

;;可以看到布尔值实际上只是通过一个上下文来进行控制流的切换 所以基于lambda可以模拟出布尔值

(def TRUE (fn [x]
            (fn [y]
              x)))

(def FALSE (fn [x]
             (fn [y]
               y)))

;;也可以转化为一般意义的布尔值

(defn to-boolean
  [proc]
  ((proc true) false))

(to-boolean TRUE)
(to-boolean FALSE)

;;可以发现布尔值在控制流中的意义就是如果为true那么就执行第一段continuation 如果是false就执行第二段continuation

(defn lambda-if
  [proc x y]
  ((proc x) y))

(lambda-if TRUE "happy" "sad")
(lambda-if FALSE "happy" "sad")

(def IF
  (fn [b]
    (fn [x]
      (fn [y]
        ((b x) y)))))

(((IF TRUE) "happy") "sad")
(((IF FALSE) "happy") "sad")

;;进一步我们可以利用外延等价简化我们的IF表达式 其实就只是把TRUE或者FALSE返回就是了 因为本来所有工作都是在TRUE和FALSE这两个lambda完成的

(def IF
  (fn [b] b))

(((IF TRUE) "happy") "sad")
(((IF FALSE) "happy") "sad")

