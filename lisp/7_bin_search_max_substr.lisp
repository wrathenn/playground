(defun range (min max &optional (step 1))
  (declare (optimize (speed 3)))
  (when (< min max)
    (cons min (range (+ min step) max step))))

; Функция для сортировки списка строк по длине (по возрастанию)
(defun sort-strings (lst)
  (declare (optimize (speed 3)))
  (reduce (lambda (res e)
	    (cond ((or (not res) (< (length e) (length (car res)))) (cons e res))
		  (T (progn (setf (cdr (last res)) (cons e ())) res)))) lst :initial-value ()))

; Разбить строку на подстроки
(defun make-subs (str len)
  (declare (optimize (speed 3)))
  (mapcar (lambda (pos) (subseq str pos (+ pos len)))
	  (range 0 (- (length str) len -1))))

; Найти, какие подстроки существуют в strs
(defun find-subs (subs strs)
  (declare (optimize (speed 3)))
  (mapcar (lambda (it) (every (lambda (i) i) it))
	  (mapcar (lambda (sub)
		    (mapcar (lambda (str) (search sub str))
			    strs))
		  subs)))

; Определить все найденные во всех строках подстроки
; Возвращает:
; список найденных подстрок, которые наLLIлись во всех строках
; nil иначе
(defun match-on-found (subs found)
  (declare (optimize (speed 3)))
  (reduce (lambda (res sub) (if sub (cons sub res) res))
	  (mapcar (lambda (sub is-f) (if is-f sub))
		  subs found)
	  :initial-value ()))

(defun get-found-subs (subs strs)
  (match-on-found subs (find-subs subs strs)))

(defun bin-search (lst &optional res (len-b 0) (len-e (length (car lst))))
  (declare (optimize (speed 3)))
  (print "started bin-search")
  (print "len-b=")
  (print len-b)
  (print "len-e=")
  (print len-e)
  (if (/= len-b len-e)
      (let* ((len (ceiling (/ (+ len-e len-b) 2)))
	     (new-res (get-found-subs
		       (make-subs (car lst) len)
		       (cdr lst))))
	(if new-res
	    (bin-search lst new-res len len-e)
	    (bin-search lst res len-b (- len 1))))
      res))

(defun del-enter-on-end (line)
  (declare (optimize (speed 3)))
  (print (length line))
  (if (equal (char line (- (length line) 1)) #\Return)
      (subseq line 0 (- (length line) 1))
      line))

(defun read-file (filename)
  (declare (optimize (speed 3)))
  (with-open-file (inp filename :direction :input)
    (loop for line = (progn (print "read str") (read-line inp nil))
	 while line
       collect (del-enter-on-end line))))

(defun find-max-from-file (filename)
  (bin-search (sort-strings (read-file filename))))

(defun asd ()
  (declare (optimize (speed 3)))
  (find-max-from-file "d:/Progs/lisp_7/tests/pseudo10001.fasta"))

(find-max-from-file "d:/Progs/lisp_7/tests/pseudo10001.fasta")

;; (progn (read-file "d:/Progs/lisp_7/tests/pseudo10001.fasta")
;;        T)

