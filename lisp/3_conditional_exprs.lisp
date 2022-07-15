;; 1) Написать функцию, которая принимает целое число и возвращает первое четное число, не меньшее аргумента
(defun f1 (x) (if (= (mod x 2) 1) (+ x 1) x))
(f1 3)

;; 2) Написать функцию, которая принимает число и возвращает число того же знака, но с модулем на 1 больше модуля аргумента
(defun f2 (x) (* (+ (abs x) 1) (if (> x 0) 1 -1)))
(defun f2 (x) (if (>= x 0) (+ x 1) (- x 1))) 
(f2 3)

;; 3) Написать функцию, которая принимает два числа и возвращает список из этих чисел, расположенных по возрастанию
(defun f3 (x y) (cond ((< x y) (list x y)) (t (list y x))))
(f3 1 3)
(f3 3 1)
(f3 2 2)

;; 4) Написать функцию, которая принимает три числа и возвращает T только тогда, когда первое число расположено между вторым и третьим
(defun f4 (x y z) (if (> x y) (< x z) (> x z)))
(f4 1 2 3)
(f4 2 1 3)
(f4 3 5 1)

;; 5) Каков результат вычисления следующих выражений?
; Основная суть в том, что AND возвращает первое встертившееся NIL значение (если не встретилось, то последний аргумент)
; А OR возвращает первое встретившееся не-NIL значение (если не встретилось, то последний аргумент)
(and 'fee 'fie 'foe)
; > FOE

(or nil 'fie 'foe)
; > FIE

(and (equal 'abc 'abc) 'yes)
; > YES

(or 'fee 'fie 'foe)
; > FEE

(and nil 'fie 'foe)
; > NIL

(or (equal 'abc 'abc) 'yes)
; > T

;; 6) Написать предикат, который принимает два числа-аргумента и возвращает:
;;      T, если первое число меньше второго
(defun less (x y) (< x y))
(less 3 4)
(less 4 3)
(less 4 4)

;; 7) Какой из следующих двух вариантов предиката ошибочен и почему?
; Сначала проверяет, число ли Х, затем - положительный ли Х
; т.е. проверяет, положительное ли число
(defun pred1 (x) (and (numberp x) (plusp x)))
(pred1 5) ; T
(pred1 -5) ; NIL
(pred1 'asd) ; Вернет NIL
; Сначала проверяет, положительное ли число X, затем - число ли X
(defun pred2 (x) (and (plusp x) (numberp x)))
(pred2 5) ; T
(pred2 -5) ; NIL
(pred2 'asd) ; Выдаст ошибку

;; 8) Решить задачу 4, используя для ее решения конструкции IF, COND, AND/OR.
; Написать функцию, которая принимает три числа и возвращает T только тогда, когда первое число расположено между вторым и третьим
; IF:
(defun f8_1 (x y z) (if (> x y) (< x z) (> x z)))
(f8_1 1 2 3)
(f8_1 2 1 3)
; COND:
(defun f8_2 (x y z) (cond ((> x y) (< x z)) (T (> x z))))
(f8_2 1 2 3)
(f8_2 2 1 3)
; AND/OR:
(defun f8_3 (x y z) (or (and (> x y) (< x z)) (and (> x z) (< x y))))
(f8_3 1 2 3)
(f8_3 2 1 3)

;; 9) Переписать функцию how-alike, приведенную в лекции и использующую COND, используя только конструкции IF, AND/OR
(defun how-alike (x y)
  (cond
    (( or (= x y) (equal x y)) `the_same)
    ((and (oddp x) (oddp y)) `both_odd)
    ((and (evenp x) (evenp y)) `both_even)
    (t `difference)
  )
)
(how-alike 1 2)
(how-alike 4 4)
(how-alike 3 5)
(how-alike 4 6)

(defun how-alike2 (x y)
  (if (= x y)
      `the_same
      (if (and (oddp x) (oddp y))
	  `both_odd
	  (if (and (evenp x) (evenp y))
	      `both_even
	      `difference)))
)
(how-alike2 1 2)
(how-alike2 4 4)
(how-alike2 3 5)
(how-alike2 4 6)

(defun how-alike2 (x y)
  (if (= x y) `the_same
      (if (equal x y) `the_same
	  (if (oddp x) (if (oddp y) `BOTH_ODD `DIFFERENCE)
	      (if (evenp y) `BOTH_EVEN `DIFFERENCE)))))

(or (= '(1 2) 3) (equal '(1 2) 3))

(defun how-alike2 (x y)
  (or (and (or (= x y) (equal x y)) 'the_same)
      (and (oddp x) (oddp y) 'both_odd)
      (and (evenp x) (evenp y) 'both_even)
      'difference))
      
