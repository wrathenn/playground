; Используя функционалы:

; Напишите функцию, которая уменьшает на 10 все числа из списка-аргумента этой функции
(defun minus-10 (lst)
  (mapcar (lambda (e) (if (numberp e) (- e 10) e)) lst))

(minus-10 '(1 2 3))
(minus-10 '(a b c))

; Напишите функцию, которая умножает на заданное число-аргумент все числа из заданного списка-аргумента, когда
; а) все элементы списка -- числа
; б) элементы списка -- любые объекты
(defun mult-all (lst n)
  (mapcar (lambda (e) (if (numberp e) (* e n) e)) lst))

(mult-all '(1 2 a) 10)

(defun mult-all-deep (lst n)
  (mapcar (lambda (e) (cond ((numberp e) (* e n))
			    ((listp e) (mult-all-deep e n))
			    (T e)))
	  lst))
(mult-all-deep '(1 2 (3 4) (5 (6 (7))) a b) 10)
			    

; Напишите функцию, которая по своему списку-аргументу lst определяет,
; является ли он палиндромом
(defun my-reverse (lst)
  (reduce (lambda (res e) (cons e res)) lst :initial-value ()))

(defun palindromp (lst)
  (equal lst (my-reverse lst)))

; Написать предикат set-equal, который возвращает T, если два его множества-аргумента
; содержат одни и те же элементы, порядок которых не имеет значения
(defun in-set (el lst)
  (reduce (lambda (a b) (or a b))
	  (mapcar (lambda (e) (equal el e)) lst)))

(defun my-subsetp (l1 l2)
  (reduce (lambda (a b) (and a b))
	  (mapcar (lambda (e) (in-set e l2)) l1)))

(defun set-equal (l1 l2)
  (and (my-subsetp l1 l2) (my-subsetp l2 l1)))

(set-equal '(1 2 3) '(3 1 2))
(set-equal '(1 2 3) '(1 2 3 4))

; Написать функцию, которая получает как аргумент список чисел
; а возвращает список квадратов этих чисел в том же порядке
(defun sqr-lst (lst)
  (mapcar (lambda (e) (* e e)) lst))

(sqr-lst '(0 1 -2 3))

; Напишите функцию select-between, которая из списка-аргумента из 5 чисел выбирает те, которые
; расположены между двумя указанными границами-аргументами и возвращает их в виде списка
; (сортированного +2 балла)
(defun select-between (lst n1 n2)
  (mapcan (lambda (e) (and (< n1 e n2) (list e)))
	  lst))

(select-between '(0 1 2 3 4) 0 3)

(defun select-between-sorted (lst n1 n2)
  (sort (select-between lst n1 n2) #'<))

(select-between-sorted '(0 3 1 2 4) 0 3)

; Напишите функцию, вычисляющую декартово произведение двух своих списков-аргументов
(defun decart-mult (l1 l2)
  (mapcan (lambda (e1)
	    (mapcar (lambda (e2)
		      (list e1 e2))
		    l2))
	  l1))

(decart-mult '(0 1) '(2 3))

; Почему так реализовано reduce, в чем причина?
(reduce #'+ ())
; > 0
; Так как функция + возвращает 0 при количестве аргументов 0
; reduce сработает для пустого списка
; Если бы функция не могла принимать 0 аргументов, то была бы ошибка
(reduce #'/ ())
; invalid number of arguments: 0
;    [Condition of type SB-INT:SIMPLE-PROGRAM-ERROR]

; Пусть list-of-list - список, состоящий из списков.
; Написать функцию, которая вычисляет сумму длин всех элементов
(defun sum-of-len (lst)
  (reduce (lambda (res e) (+ res (length e)))
	  lst
	  :initial-value 0))

(sum-of-len '((1 2) (3 4 5)))
