(setf base '(2 3 5))
(gcd 3 3)

;;;;;

(defun gcd-table (base)
  (mapcar (lambda (it1)
	    (mapcar (lambda (it2) (gcd it1 it2)) base))
	  base))

(defun sum-deep-list (lst)
  (reduce (lambda (res it)
	    (+ res (if (listp it) (sum-deep-list it) it)))
	  lst :initial-value 0))

;;;;;

(defun check-base (base)
  (if (= (- (sum-deep-list (gcd-table base))
	    (sum-deep-list base)
	    (* (length base) (- (length base) 1)))
	 0)
      T
      ()))

(defun decimal-to-sok (base num)
  (mapcar (lambda (it) (mod num it)) base))
;shadow
(defun sok-plus (base &rest s-numbers)
  (mapcar #'mod (apply #'mapcar #'+ s-numbers) base))

(defun sok-minus (base &rest s-numbers)
  (mapcar #'mod (apply #'mapcar #'- s-numbers) base))

(defun sok-mult (base &rest s-numbers)
  (mapcar #'mod (apply #'mapcar #'* s-numbers) base))

(defun sok-inverse-num (base-num num)
  (mod (expt num (- base-num 2)) base-num))

(defun sok-inverse (base &rest s-numbers)
  (mapcar (lambda (number)
	    (mapcar #'sok-inverse-num
		    base number))
	  s-numbers))

(defun sok-div (base &rest s-numbers)
  (apply #'sok-mult base (car s-numbers)
	 (apply #'sok-inverse base (cdr s-numbers))))

(defun sok-to-decimal (base s-number)
  (let ((M (reduce #'* base)))
    (mod (apply #'+ (mapcar 
		     (lambda (b n)
		       (* n (/ M b) (sok-inverse-num b (/ M b))))
		     base s-number))
	 M)))

  
  




