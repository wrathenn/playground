; Написать функцию, которая по списку-аргументу lst определяет, является ли она палиндромом
(defun palindromp (lst) (equal lst (reverse lst)))

(palindromp '(1 2))
(palindromp '(1 2 1))

; Написать предикат set-equal, который возвращает T, если два его множества-аргумента
; содержат одни и те же элементы, порядок которых не имеет значения
(defun set-equal (l1 l2)
  (and (subsetp l1 l2) (subsetp l2 l1)))

(set-equal '(1 2 3) '(3 1 2))
(set-equal '(1 2 3) '(1 2 3 4))

; Напишите свои необходимые функции, которые обрабатывают
; таблицу из 4 точечных пар (страна . столица)
; и возвращают по стране - столицу, а по столице - страну
(defun get-capital (country table)
  (cond ((equal (caar table) country) (cdar table))
	((cdr table) (get-capital country (cdr table)))
	(T ())))

(defun get-country (capital table)
  (cond ((equal (cdar table) capital) (caar table))
	((cdr table) (get-country capital (cdr table)))
	(T ())))

(setf table '(("Россия" . "Москва")
	      ("Мексика" . "Мехико")
	      ("Сербия" . "Белград")
	      ("Франция" . "Париж")))
(get-capital "Россия" table)
(get-capital "asd" table)
(get-country "Париж" table)
(get-country "asd" table)

; Напишите функцию swap-first-last, которая переставляет
; в списке-аргументе первый и последний элементы
(defun swap-first-last (lst)
  (if lst 
      (let ((first (car lst))
	    (last (car (last lst))))
	(setf (car lst) last)
	(setf (car (last lst)) first)
	lst)))

(swap-first-last '(1 5 5 5 3))
(swap-first-last '(1))
(swap-first-last ())

; Напишите функцию swap-two-element, которая переставляет в списке аргумента два указанных
; своими порядковыми номерами элемента в этом списке
(defun swap-two-element (lst n1 n2)
  (if (and lst (< n1 (length lst)) (< n2 (length lst)))
      (let ((temp (nth n1 lst)))
	(setf (nth n1 lst) (nth n2 lst))
	(setf (nth n2 lst) temp)
	lst)))

(swap-two-element '(0 1 2 3 4 5) 1 3)
(swap-two-element () 0 0)
(swap-two-element '(0 1 2) 1 3)

; Напишите две функции, swap-to-left и swap-to-right, которые производят одну круговую перестановку
; в списке-аргументе влево и вправо, соответственно
(defun swap-to-left (lst)
  (if lst
      (progn
	(setf (cdr (last lst)) (cons (car lst) ()))
	(cdr lst))))

; или
(defun swap-to-left (lst)
  (let ((first (car lst)))
    (reverse (cons first (reverse (cdr lst))))))

(swap-to-left '(0 1 2 3))
(swap-to-left '(1))
(swap-to-left ())

(defun swap-to-right (lst)
  (let ((last (car (last lst))))
    (reverse (cdr (reverse (cons last lst))))))

(swap-to-right '(0 1 2 3))
(swap-to-right '(1))
(swap-to-right ())

; Напишите функцию, которая добавляет к множеству двухэлементных списков новый двухэлементный список, если его там нет
(defun add-2list (src lst)
  (if (find lst src :test #'equalp)
      src
      (cons lst src)))

(add-2list '((1 2) (3 4) (5 6)) '(1 2))
(add-2list '() '(1 2))

; Напишите функцию, которая умножает на заданное число-аргумент первый числовой элемент списка
; из заданного 3-х элементного списка-аргумента, когда
; а) все элементы списка -- числа
; б) элементы списка -- любые объекты
(defun mult-first (lst n)
  (cons (* (car lst) n) (cdr lst)))

(mult-first '(3 2 1) 3)

; б)
;; Вспомогательная
(defun mult-first-all (lst n)
  (let ((cur (car lst)))
    (cond ((not lst) ())
	  ((listp cur) (or (mult-first-all cur n) (mult-first-all (cdr lst) n)))
	  ((numberp cur) (setf (car lst) (* cur n)))
	  (T (mult-first-all (cdr lst) n)))))

(defun mult-first (lst n)
  (mult-first-all lst n)
  lst)

(mult-first '((3 2) 2 1) 3)
(mult-first '((a 2) 1) 3)
(mult-first '(a b) 3)
(mult-first () 3)
(mult-first '((a b) (a 1 2)) 3)

; Напишите функцию select-between, которая из списка-аргумента из 5 чисел выбирает те, которые
; расположены между двумя указанными границами-аргументами и возвращает их в виде списка
; (сортированного +2 балла)
(defun select-between (lst n1 n2)
  (if lst (if (< n1 (car lst) n2)
	      (cons (car lst) (select-between (cdr lst) n1 n2))
	      (select-between (cdr lst) n1 n2))))

(select-between '(0 1 2 3 4) 0 3)

(defun select-between-sorted (lst n1 n2)
  (sort (select-between lst n1 n2) #'<))

(select-between-sorted '(0 3 2 1 4) 0 3)
