;; 1) Чем принципиально отличаются функции cons, list, append?
(setf lst1 '(a b))
(setf lst2 '(c d))

lst1

(cons lst1 lst2)
; > ((A B) C D)
(list lst1 lst2)
; > ((A B) (C D))
(append lst1 lst2)
; > (A B C D)

;; 2) Каковы результаты следующих выражений, и почему?
(reverse ())
; > NIL
(last ())
; > NIL
(reverse '(a))
; > (A)
(last '(a))
; > (A)
(reverse '((a b c)))
; > ((A B C))
(last '((a b c)))
; > ((A B C))

;; 3) Написать по крайней мере два варианта функции, которая возвращает последний элемент своего списка аргумента
(defun my-last (lst)
  (car (reverse lst)))
(my-last '(1 2 3))

(defun my-last (lst)
  (cond ((cdr lst) (my-last (cdr lst)))
	(T (car lst)))) 
(my-last '(1 2 3))

;; 4) Написать по крайней мере два варианта функции, которая возвращает свой список-аргумент без последнего элемента
(defun my-cut (lst)
  (reverse (cdr (reverse lst))))
(my-cut '(1 2 3 4 5))

(defun my-cut (lst)
  (cond ((cdr lst) (cons (car lst) (my-cut (cdr lst))))
	(T ())))

(defun my-cut (lst)
  (and (cdr lst) (cons (car lst) (my-cut (cdr lst)))))

;; Написать простой вариант игры в кости, в котором бросаются две правильные кости.
;; Если сумма выпавших очков равна 7 или 11 --- выигрыш,
;; если выпало (1,1) или (6,6) --- игрок получает право снова бросить кости,
;; во всех остальных случаях ход переходит ко второму игроку, но запоминается сумма выпавших очков.
;; Если второй игрок не выигрывает абсолютно, то выигрывает тот игрок, у которого больше очков.
;; Результат игры и значения выпавших костей выводить на экран с помощью функции print.

(defun cast-dice ()
  (+ 1 (random 6)))

(defun cast-2-dice ()
  (let ((n1 (cast-dice)) (n2 (cast-dice)))
    (print(format nil "Бросок: ~D и ~D" n1 n2))
    (+ n1 n2)))

(defun player-cast ()
  (let ((n (cast-2-dice)))
    (format nil "~D" n)
    (if (or (= n 2) (= n 12))
	(player-cast)
	n)))

(defun is-win (n) (or (= n 7) (= n 11)))

(defun declare-win (n) 
  (print (format nil "~A игрок победил!" (if (= n 1) `Первый `Второй))))

(defun play ()
  (print "Бросает первый игрок")
  (let ((n1 (player-cast)))
    (cond ((is-win n1) (declare-win 1))
	  (T (print "Бросает второй игрок")
	     (let ((n2 (player-cast)))
	       (cond ((or (is-win n2) (> n2 n1)) (declare-win 2))
		     ((< n2 n1) (declare-win 1))
		     (T (print "Ничья"))))))))

(play)

