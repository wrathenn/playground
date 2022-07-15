; 5)
(defun longer_then (a b) (> (length a) (length b)))
(longer_then '(1 2 3) '(2 3 3))

; 6)
(cons 3 (list 5 6))
; > (3 5 6)

(list 3 'from 9 'lives (- 9 3))
; > (3 FROM 9 LIVES 6)

(+ (length for 2 too))
; Ошибка - значения for, too не определены + length - ф-я от 1 аргумента

(car '(21 22 23)))
; Неправильная скобочная конструкция

(cdr '(cons is short for ans))
; (IS SHORT FOR ANS)

(car (list one two))
; Ошибка - значения one и two не определены

(cons 3 '(list 5 6))
; (3 LIST 5 6)

(car (list 'one 'two))
; ONE

;; 7)
(defun mystery(x) (list (second x) (first x)))
(mystery (one two))
(mystery (last one two))
(mystery free)
(mystery one 'two)
; все в ошибки :(

; 8) Перевод из Фаренгейтов в Цельсии
(defun f-to-c (temp)
  (* (/ 5 9) (- temp 32)))

(f-to-c 451)
; 2095/9
(float (f-to-c 451))
; 232.777

; 9) Что получится при вычислении каждого из выражений?
(list 'cons t NIL)
; (CONS T NIL)

(eval (eval (list 'cons t NIL)))
; Ошибка, т.к. нет ф-ии T

(apply #cons ''(T NIL)) 
; Ошибка, скорее так:
(apply #'cons ''(T NIL))
; (QUOTE T NIL)

(eval (list 'cons t NIL))
; (T)

(eval NIL)
; NIL

(eval (list 'eval NIL))
; NIL

;; Дополнительно
; Катет по гипотенузе и катету + схема
(defun katet (hyp kat)
  (sqrt (- (* hyp hyp) (* kat kat))))
(katet 5 3)

; Площадь трапеции
(defun strap (a b h)
  (* (/ (+ a b) 2) h))
(strap 2 4 1)
