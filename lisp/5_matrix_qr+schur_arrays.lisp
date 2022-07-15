(ql:quickload :array-operations)

(defun matrix-cols-cnt (m)
  (array-dimension m 1))

(defun matrix-rows-cnt (m)
  (array-dimension m 0))

(defun matrix-get (row-i col-i m)
  (aref m row-i col-i))

(defun matrix-get-row (row-i m)
  (make-array (array-dimension m 1) 
	      :displaced-to m 
	      :displaced-index-offset (* row-i (array-dimension m 1))))

(defun matrix-get-col (col-i m)
  (let ((res (make-array (array-dimension m 0))))
    (loop for i from 0 below (array-dimension m 0) do
	 (setf (row-major-aref res i) (aref m i col-i)))
    res))

(defun matrix-compare (m1 m2 eps)
  (if (and (= (matrix-rows-cnt m1) (matrix-rows-cnt m2))
	   (= (matrix-cols-cnt m1) (matrix-cols-cnt m2)))
      (loop for r from 0 below (matrix-rows-cnt m1) do
	   (loop for c from 0 below (matrix-cols-cnt m2) do
		(if (> (abs (- (aref m1 r c) (aref m2 r c))) eps)
		    (return-from matrix-compare nil)))))
  T)

(defun matrix-transpose (m)
  (let ((res (make-array (reverse (array-dimensions m)))))
    (loop for i from 0 below (array-dimension m 0) do
	 (loop for j from 0 below (array-dimension m 1) do
	      (setf (aref res j i) (aref m i j))))
    res))

(defun matrix-minor (row-i col-i m)
  (let ((res (make-array (mapcar #'- (array-dimensions m) '(1 1)))))
    (loop for i from 0 below (array-dimension m 0) do
	 (loop for j from 0 below (array-dimension m 1) do
	      (if (and (/= i row-i) (/= j col-i))
		  (setf (aref res
			      (if (> i row-i) (- i 1) i)
			      (if (> j col-i) (- j 1) j))
			(aref m i j)))))
    res))

(defun matrix-det (m)
  (when (= (matrix-cols-cnt m) (matrix-rows-cnt m) 0)
    (return-from matrix-det 1))
  (let ((results (make-array (array-dimension m 1))))
    (if (= (array-dimension m 1) 1) (aref m 0 0)
	(progn
	  (loop for i from 0 below (array-dimension m 0) do
	       (setf (aref results i) (* (matrix-det (matrix-minor 0 i m))
					 (aref m 0 i)
					 (if (oddp i) -1 1))))
	(reduce #'+ results)))))

(defun matrix-mult-num (num m)
  (aops:each (lambda (it) (* num it)) m))

(defun matrix-associated (m)
  (let ((res (make-array (array-dimensions m)))
	(m (matrix-transpose m)))
    (loop for i from 0 below (array-dimension m 0) do
	 (loop for j from 0 below (array-dimension m 1) do
	      (setf (aref res i j) 
		    (* (matrix-det (matrix-minor i j m))
		       (if (oddp (+ i j)) -1 1)))))
    res))

(defun matrix-inverse (m)
  (cond ((= (matrix-rows-cnt m) (matrix-cols-cnt m))
	 (matrix-mult-num (/ 1 (matrix-det m)) (matrix-associated m)))
	((< (matrix-rows-cnt m) (matrix-cols-cnt m))
	 (matrix-mult (matrix-transpose m)
		      (matrix-inverse (matrix-mult m (matrix-transpose m)))))
	(T (matrix-mult (matrix-inverse (matrix-mult (matrix-transpose m) m))
			(matrix-transpose m)))))

(defun mgs-r-row-with-zeroes (r-row col-len)
  (let ((res (make-array col-len :initial-element 0))
	(zeroes-cnt (- col-len (array-dimension r-row 0))))
    (loop for i from zeroes-cnt below col-len do
	 (setf (aref res i) (aref r-row (- i zeroes-cnt))))
    res))

(defun modified-gram-schmidt (matrix)
  (let ((M (make-array (array-dimensions matrix) :displaced-to (aops:each (lambda (it) it) matrix)))
	(Q (make-array (array-dimensions matrix)))
	(R (make-array (array-dimensions matrix))))
  (loop for k from 0 below (array-dimension M 1) do
       (let ((rkk-val (sqrt (reduce #'+ (aops:each #'* (matrix-get-col k M) (matrix-get-col k M))))))
	 (when (< (abs rkk-val) 1e-4)
	   (return-from modified-gram-schmidt
	     (list (matrix-transpose (make-array (list k (array-dimension M 0)) :displaced-to (matrix-transpose Q)))
		   (make-array (list k (array-dimension M 1)) :displaced-to R))))
	 (setf (aref R k k) rkk-val))
       (loop for j from 0 below (array-dimension M 0) do
	    (setf (aref Q j k) (/ (aref M j k) (aref R k k))))
       (loop for i from (+ k 1) below (array-dimension M 1) do
	    (setf (aref R k i) (reduce #'+ (aops:each #'* (matrix-get-col i M) (matrix-get-col k Q))))
	    (loop for j from 0 below (array-dimension M 0) do
		 (setf (aref M j i) (- (aref M j i) (* (aref R k i) (aref Q j k)))))))
  (list Q R)))

(defun matrix-qr (m)
  (modified-gram-schmidt m))

(defun matrix-mult (m1 m2)
  (let ((res (make-array (list (array-dimension m1 0) (array-dimension m2 1)))))
    (loop for i from 0 below (array-dimension m1 0) do
	 (loop for j from 0 below (array-dimension m2 1) do
	      (loop for k from 0 below (array-dimension m2 0) do
		   (setf (aref res i j) (+ (aref res i j)
					   (* (aref m1 i k) (aref m2 k j)))))))
    res))

(defun matrix-schur (m &optional (cnt 100) prev-q)
  (if (= cnt 0)
      (list prev-q m (matrix-transpose prev-q))
      (let* ((res (matrix-qr m))
	     (Q (car res))
	     (R (cadr res))
	     (next-q (if prev-q (matrix-mult prev-q Q) Q)))
	(matrix-schur (matrix-mult R Q) (- cnt 1) next-q))))

(defun qr-test (m eps)
  (let ((res (matrix-qr m)))
    (matrix-compare m
		    (matrix-mult (car res) (cadr res))
		    eps)))

(qr-test #2A((1 2 3) (5 1 3)) 1e-3)

(defun schur-test (m cnt eps)
  (let ((res (matrix-schur m cnt)))
    (matrix-compare
     m
     (matrix-mult (matrix-mult (car res) (cadr res))
		   (caddr res))
     eps)))


