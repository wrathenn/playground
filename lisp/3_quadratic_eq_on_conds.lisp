; Решение квадратного уравнения через COND
(defun discriminant (a b c)
  (- (* b b) (* (* 4 a) c)))

(defun solve-quad-eq (a b c)
  (cond ((not (= a 0)) (let ((d (discriminant a b c)))
			 (cond ((= d 0) (let ((x1 (/ (- b) (* 2 a))))
					  (list x1 x1)))
			       (T (list (/ (+ (- b) (sqrt d)) (* 2 a))
				      (/ (- (- b) (sqrt d)) (* 2 a)))))))
	; a = 0
	((not (= b 0)) (/ (- c) b))
	; a = b = 0
	((= c 0) T) ; Любые корни
	(T ())))

(defun solve-quad-eq-out (a b c)
  (with-open-file (stream "D:/Progs/lisp_3/out.txt" :direction :output :if-exists :supersede)
    (format stream "Уравнение: ~Fx^2 + ~Fx + ~F = 0~%Ответ: " a b c) 
    (let ((res (solve-quad-eq a b c)))
      (cond ((not res) (format stream "Корней нет"))
	    ((eq res T) (format stream "Любой x"))
	    ((complexp (car res)) (format stream "x1 = ~F + (~F) * i, x2 = ~F + (~F) * i~%" (realpart (car res)) (imagpart (car res)) (realpart (cadr res)) (imagpart(cadr res))))
	    (T (format stream "x1 = ~F, x2 = ~F~%" (car res) (cadr res)))))))
