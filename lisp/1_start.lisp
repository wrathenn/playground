;; 2) Используя только функции CAR, CDR, написать выражение, возвращающее
; a) II элемент
(car (cdr '(1 2 3 4 5)))

; b) III элемент
(car (cdr (cdr '(1 2 3 4 5))))

; c) IV элемент
(car (cdr (cdr (cdr '(1 2 3 4 5)))))

;; 3) Что будет в результате вычисления выражений?
; a)
(caadr '((blue cube) (red pyramide)))
; > RED

; b)
(cdar '((abc)(def)(ghi)))
; > nil

; c)
(caddr '((abc)(def)(ghi)))
; > GHI

;; Напишите результат вычисления выражения и объясните, как он был получен
; 1)
(list 'Fred 'and 'Wilma)
; > (FRED AND WILMA)

; 2)
(list 'Fred '(and Wilma))
; > (FRED (AND WILMA))

; 3)
(cons nil nil)
; > (NIL)

; 4)
(cons T nil)
; > (T)

; 5)
(cons nil T)
; > (NIL . T)

; 6)
(list nil)
; > (NILL


; 7)
(cons '(T) NIL)
; > ((T))

; 8)
(list '(one two) '(free temp))
; > ((ONE TWO) (FREE TEMP))

; 9)
(cons 'Fred '(and Wilma))
; > (FRED AND WILMA)

; 10)
(cons 'Fred '(Wilma))
; > (FRED WILMA)

; 11)
(list nil nil)
; > (NIL NIL)

; 12)
(list T nil)
; > (T NIL)

; 13)
(list nil T)
; > (NIL T)

; 14)
(cons T (list nil))
; > (T NIL)

; 15)
(list '(T) nil)
; > ((T) NIL)

; 16)
(cons '(one two) '(free temp))
; > ((one two) free temp)

;; 5) Написать лямбда-выражение и соответствующую функцию. Представить в виде списочных ячеек.
; lambda "запускается" так:
((lambda (a1 a2)
   (list (cons a1 a2) (cons a2 a1)))
   1 2)
; a) ((ar1 ar2) (ar3 ar4))
(lambda (ar1 ar2 ar3 ar4) (list (list ar1 ar2) (list ar3 ar4)))
(defun fn_0 (ar1 ar2 ar3 ar4) (list (list ar1 ar2) (list ar3 ar4)))
(fn_0 1 2 3 4)
; Через cons:
(lambda (ar1 ar2 ar3 ar4)
  (cons
   (cons ar1 (cons ar2 nil))
   (cons
    (cons ar3 (cons ar4 nil))
    nil)))

; b) ((ar1) (ar2))
(lambda (ar1 ar2) (list (list ar1) (list ar2)))
(defun fn_1 (ar1 ar2) (list (list ar1) (list ar2)))
(fn_1 1 2)
; Через cons:
(lambda (ar1 ar2)
  (cons
   ar1
   (cons
    ar2
    nil)))

; c) (((ar1)))
(lambda (ar1) (list (list (list ar1))))
(defun fn_2 (ar1) (list (list (list ar1))))
(fn_2 1)
; Через cons:
(lambda (ar1) (cons (cons (cons ar1 nil) nil) nil))

; Подвохи:
(defvar a 9)
a

(defvar b (list 1 2 3))
b
; Различать две квоты ' и `
'b
`b
`,b
',b
; еще что-то с @ после `

; про 5 указателей (name, value, func, properties...)
(defvar x 1)
x ; > 1

(defun x () 7)
x ; > 1
(x) ; > 7

(defvar l '(1 2))
l
(setf *print-circle* t) ; Чтобы printer не выводил циклический список
(setf (cdr l) l)
l
