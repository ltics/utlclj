## basic utlc

### church numeral

* 0 = λf. λx. x
* 1 = λf. λx. f x
* 2 = λf. λx. f (f x)
......
* n = λf. λx. f^n x

### numeral operation

* succ = λn. λf. λx. f (n f x)
* plus = λn. λn. λf. λx. m f (n f x)

### church boolean

* true  = λa. λb. a
* false = λa. λb. b

### boolean logic

* and = λm. λn. m n m
* or  = λm. λn. m m n
* not = λm. λa. λb. m b a
* if  = λm. λa. λb. m a b
