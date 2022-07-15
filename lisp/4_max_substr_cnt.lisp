(defun find-substr-count (sub src)
  (let ((found-i (search sub src)))
    (if found-i
	(1+ (find-substr-count sub (subseq src (+ found-i 1))))
	0)))

(ql:quickload "iterate")
(use-package :iterate)

; Функция для нахождения самой частовстречающейся подстроки в строке src
(defun find-max-substr (src)
  (let ((src-len (length src))
	(max-cnt 0)
	(max-sub ""))
    (iter (for sub-begin from 0 to src-len)
	  (iter (for sub-end from (+ sub-begin 2) to src-len)
		(let* ((sub (subseq src sub-begin sub-end))
		      (sub-cnt (find-substr-count sub src)))
		  (if (or (> sub-cnt max-cnt) (and (= sub-cnt max-cnt) (> (length sub) (length max-sub))))
		      (progn (setf max-cnt sub-cnt) (setf max-sub sub))))))
    (list max-sub max-cnt)))

(find-max-substr "testest est")	
