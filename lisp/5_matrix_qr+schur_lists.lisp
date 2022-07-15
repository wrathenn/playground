(defun print-matrix (m)
  (format t "matrix:")
  (mapcar (lambda (e) (print e)) m))

;;;; extra functions
(defun range (min max &optional (step 1))
  (when (< min max)
    (cons min (range (+ min step) max step))))

(defun zero-list (cnt &optional res)
  (if (> cnt 0) (zero-list (- cnt 1) (cons 0 res))
      res))

(defun _cofactors (row pred &optional (i 0))
  (let ((cur (car row)))
    (if row
	(cons (if (funcall pred i) cur (- 0 cur))
	      (_cofactors (cdr row) pred (1+ i))))))


(defun cofactors (row-i row)
  (_cofactors row (if (oddp row-i) #'oddp #'evenp)))
		   
;;;;

(defun matrix-cols-cnt (m)
  (length (car m)))

(defun matrix-rows-cnt (m)
  (length m))

(defun matrix-get (row-i col-i m)
  (nth col-i (nth row-i m)))

(defun matrix-get-row (row-i m)
  (nth row-i m))

(defun matrix-get-col (col-i m)
  (mapcar (lambda (row) (nth col-i row)) m))

(defun matrix-transpose (m)
  (if m (apply #'mapcar #'list m)))

(defun matrix-compare (m1 m2 eps)
  (if (and (= (length m1) (length m2)) (= (length (car m1)) (length (car m2))))
     (every (lambda (it) it)
	    (mapcar (lambda (r1 r2)
		      (every (lambda (it) it)
			     (mapcar (lambda (el1 el2)
				       (<= (abs (- el1 el2)) eps))
				     r1 r2)))
		    m1 m2))))

(defun matrix-rm-row (row-i m &optional res (cur 0))
  (if m
      (cond ((= cur row-i) (matrix-rm-row row-i (cdr m) res (1+ cur)))
	    (T (matrix-rm-row row-i (cdr m) (cons (car m) res) (1+ cur))))
      (reverse res)))

(defun matrix-rm-col (col-i m)
  (mapcar (lambda (row) (matrix-rm-row col-i row)) m))

(defun matrix-minor (row-i col-i m)
  (matrix-rm-col col-i (matrix-rm-row row-i m)))

(defun matrix-det (m)
  (when (= (length m) (length (car m)) 0)
    (return-from matrix-det 1))
  (if (= (length m) 1)
      (matrix-get 0 0 m)
      (apply #'+ (mapcar (lambda (col-i k)
			   (* (matrix-det (matrix-minor 0 col-i m)) k))
			 (range 0 (length (car m)))
			 (cofactors 0 (car m))))))

(defun matrix-mult-num (num m)
  (mapcar (lambda (row)
	    (mapcar (lambda (el) (* el num))
	    row))
	  m))

(defun matrix-associated (m)
  (let ((m (matrix-transpose m)))
    (mapcar (lambda (row-i)
	      (mapcar (lambda (col-i)
			(* (matrix-det (matrix-minor row-i col-i m))
			   (if (oddp (+ col-i row-i)) -1 1)))
		      (range 0 (matrix-cols-cnt m))))
	    (range 0 (matrix-rows-cnt m)))))

(matrix-inverse '((1 2 3) (4 5 6) (7 9 8)))

(defun matrix-inverse (m)
  (cond ((= (matrix-rows-cnt m) (matrix-cols-cnt m))
	 (matrix-mult-num (/ 1 (matrix-det m)) (matrix-associated m)))
	((< (matrix-rows-cnt m) (matrix-cols-cnt m))
	 (matrix-mult (matrix-transpose m)
		      (matrix-inverse (matrix-mult m (matrix-transpose m)))))
	(T (matrix-mult (matrix-inverse (matrix-mult (matrix-transpose m) m))
			(matrix-transpose m)))))

; Функция для конструирования строки матрицы R
; Матрица R - верхне-треугольная, поэтому необходимо дополнять ее нулями
(defun mgs-r-row-with-zeroes (r-row col-len)
  (let ((z-cnt (- col-len (length r-row))))
    (if (> z-cnt 0) (append (zero-list z-cnt) r-row)
	r-row)))

; Функция, вычисляющая Q и R матрицы для матрицы из первого аргумента, представленной в виде списка СТОЛБЦОВ
; (car res) ~ Q
; (cadr res) ~ R
(defun modified-gram-schmidt (matrix-cols &optional q-res r-res)
  (if (not matrix-cols)
      (list (matrix-transpose (reverse Q-res)) (reverse R-res))
      (let ((rkk (sqrt (reduce #'+ (mapcar #'* (car matrix-cols) (car matrix-cols))))))
	(if (< (abs rkk) 1e-4)
	    (modified-gram-schmidt () q-res r-res)
	    (let* ((k-col (car matrix-cols)) ; Столбец, который рассматривается на данном шаге
		   (rkk (sqrt (reduce #'+ (mapcar #'* k-col k-col)))) ; Значение матрицы R на диагонали
		   (q-col (mapcar (lambda (el) (/ el rkk)) k-col)) ; Вычисление следующего столбца матрицы Q
		   (r-row (cons rkk (mapcar (lambda (cur-col) ; Вычисление следующей строки матрицы R (без нулей в начале)
					      (reduce #'+ (mapcar #'* cur-col q-col)))
					    (cdr matrix-cols)))) 
		   (new-m (if (cdr r-row) ; Вычисление измененных значений матрицы A, если это был не последний столбец
			      (mapcar (lambda (m-col rki)
					(mapcar (lambda (aji qjk)
						  (- aji (* rki qjk)))
						m-col q-col))
				      (cdr matrix-cols) (cdr r-row)))))
	      ; Вычисление следующих столбцов Q и строк R
	      (modified-gram-schmidt new-m (cons q-col q-res) (cons (mgs-r-row-with-zeroes r-row (length (car r-res))) 
								    r-res)))))))

(defun matrix-qr (m)
  (modified-gram-schmidt (matrix-transpose m)))

(defun matrix-mult (m1 m2)
  (mapcar (lambda (row-1)
	    (mapcar (lambda (col-2) (reduce #'+ (mapcar #'* row-1 col-2))) 
		    (matrix-transpose m2)))
	  m1))

(defun matrix-schur (m &optional (cnt 100) prev-q)
  (if (= cnt 0)
      (list prev-q m (matrix-transpose prev-q))
      (let* ((res (matrix-qr m))
	     (Q (car res))
	     (R (cadr res))
	     (next-q (if prev-q (matrix-mult prev-q Q) Q)))
	(matrix-schur (matrix-mult R Q) (- cnt 1) next-q))))

(defun matrix-unitaryp (m eps)
  (matrix-compare (matrix-inverse m) (matrix-transpose m) eps))

(defun qr-test (m eps)
  (let ((res (matrix-qr m)))
    (and
     (matrix-compare m
		     (matrix-mult (car res) (cadr res))
		     eps)
     (matrix-unitaryp (car res) eps))))

(defun schur-test (m cnt eps)
  (let ((res (matrix-schur m cnt)))
    (and
     (matrix-compare
      m
      (matrix-mult (matrix-mult (car res) (cadr res))
		   (caddr res))
		     eps)
     (matrix-compare (matrix-transpose (car res)) (caddr res) eps))))
