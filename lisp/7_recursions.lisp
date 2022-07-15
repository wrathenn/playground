; Написать хвостовую рекурсивную функцию my-reverse, которая
; развернет верхний уровень списка-аргумента lst
(defun my-reverse (lst &optional res)
  (if lst
      (my-reverse (cdr lst) (cons (car lst) res))
      res))

(my-reverse '(1 2 3))

; Написать функцию, которая возвращает первый элемент списка-аргумента,
; который сам является непустым списком
(defun find-first-deep (lst)
  (cond ((and (listp (car lst)) lst) (caar lst))
	(lst (find-first-deep (cdr lst)))
	(T ())))

(find-first-deep '(1 2 3))
(find-first-deep '((1) 2 3))

; Написать функцию, которая выбирает из заданного списка только те числа,
; которые больше 1 и меньше 10 или нефиксированные
(defun find-nums (lst n1 n2 &optional res)
  (if lst
      (find-nums (cdr lst) n1 n2 (if (< n1 (car lst) n2) (cons (car lst) res) res))
      (reverse res)))

(find-nums '(-1 -2 0 3 5 6 10 11) 1 10)

; Напишите рекурсивную функцию, которая умножает на заданное число-аргумент все числа
; а) все элементы списка -- числа
; б) элементы списка -- любые объекты
(defun mult-nums (lst n &optional res)
  (if lst
      (mult-nums (cdr lst) n (cons (* (car lst) n) res))
      (reverse res)))

(defun mult-nums (lst n &optional res)
  (if lst
      (mult-nums (cdr lst) n (cons (cond ((listp (car lst)) (mult-nums (car lst) n))
					 ((numberp (car lst)) (* (car lst) n))
					 (T (car lst)))
				   res))
      (reverse res)))

(mult-nums '(-1 (-2 0 a) 4 5) 10)

; Напишите функцию select-between, которая из списка-аргумента из чисел выбирает те, которые
; расположены между двумя указанными границами-аргументами и возвращает их в виде списка
; (сортированного +2 балла)
(defun select-between (lst n1 n2 &optional res)
  (if lst
      (select-between (cdr lst) n1 n2 (if (< n1 (car lst) n2) (cons (car lst) res) res))
      res))

(select-between '(0 1 2 3 4) 0 3)
(select-between '(0 1 2 3 4) 3 3)
(select-between () 3 3)
(select-between '(0 1 2) 3 4)

(defun select-between-sorted (lst n1 n2)
  (sort (select-between lst n1 n2) #'<))

(select-between-sorted '(0 3 1 2 4) 0 3)

; Написать рекурсивную версию (с именем rec-add) вычисления суммы чисел заданного списка:
; а) одноуровнего смешанного
; б) структурированного
(defun rec-add (lst &optional (res 0))
  (if lst
      (rec-add (cdr lst) (+ res (car lst)))
      res))

(rec-add '(1 2 3))

(defun rec-add (lst &optional (res 0))
  (if lst
      (rec-add (cdr lst) (cond ((listp (car lst)) (rec-add (car lst) res))
			       ((numberp (car lst)) (+ (car lst) res))
			       (T res)))
      res))

(rec-add '(1 2 3))
(rec-add '(1 (2 3) (4 5)))

; Написать рекурсивную версию с именем recnth функции nth
(defun recnth (n lst &optional (cnt 0))
  (if (= n cnt)
      (car lst)
      (recnth n (cdr lst) (1+ cnt))))

; или даже лучше
(defun recnth (n lst)
  (if (= n 0)
      (car lst)
      (recnth (- n 1) (cdr lst))))

(recnth 2 '(0 1 2 3))

; Написать рекурсивную функцию allodd, которая возвращает 
; t когда все элементы списка нечетные
(defun allodd (lst)
  (if lst
      (if (oddp (car lst)) (allodd (cdr lst)) ())
      T))

(allodd '(1 2 3))
(allodd '(1 3))

; Написать рекурсивную функцию, которая возвращает первое нечетное число из списка
; (структурированного), возможно создавая некоторые вспомогательные функции.
(defun _first-odd (lst)
  (if lst
      (cond ((listp (car lst)) (or (_first-odd (car lst)) (_first-odd (cdr lst))))
	    ((numberp (car lst)) (if (oddp (car lst)) (car lst) (_first-odd (cdr lst)))))))

(_first-odd '(1 2 3))
(_first-odd '(2 4 5))
(_first-odd '((2 4) (3 5) 2))

; Используя cons-дополняемую рекурсию с одним тестом завершения,
; написать функцию которая получает как аргумент список чисел,
; а возвращает список квадратов этих чисел в том же порядке 

(defun sqr-lst (lst)
  (if lst
      (cons (* (car lst) (car lst))(sqr-lst (cdr lst)))))
(nconc 

(sqr-lst '(-2 3 4))

