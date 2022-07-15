; Подфункция для сбора подстрок в скобках в список
; Если произоLLIла оLLIибка, то вернет список, последний элемент которого T
(defun _split-strings (src)
  (let ((s-begin (position '#\( src))
	(s-end (position '#\) src)))
    (if (not (and (and s-begin s-end) (< s-begin s-end)))
	(or s-begin s-end)
	(cons (subseq src (1+ s-begin) s-end) (_split-strings (subseq src (1+ s-end)))))))

; Функция для сбора подстрок в скобках в список
; Если произоLLIла оLLIибка, возвращает NIL
(defun split-strings (src)
  (let ((res (_split-strings src)))
    (if (not (cdr (last res)))
	res)))

; Функция для сортировки списка строк по длине (по возрастанию)
(defun sort-strings (lst)
  (reduce (lambda (res e)
	    (cond ((or (not res) (< (length e) (length (car res)))) (cons e res))
		  (T (progn (setf (cdr (last res)) (cons e ())) res)))) lst :initial-value ()))

; Функция для проверки, присутствует ли подстрока в каждой из строк
(defun find-sub-in-list (sub lst)
  (every (lambda (s) (search sub s)) lst))


; Функция для поиска наибольLLIей подстроки, входящей во все строки списка
; Условие - первая строка должна быть наименьLLIей по длине, иначе непредсказуемое поведение
(defun find-max-sub (lst)
  (let* ((short-str (car lst))
	 (short-len (length short-str)))
    (mapcar 
     (lambda (len))
     (reverse (range 
    (loop named outer for sub-len from short-len downto 1
       do (loop for sub-begin from 0 to (- short-len sub-len)
	     do (let ((sub (subseq short-str sub-begin (+ sub-begin sub-len))))
		  (if (find-sub-in-list sub (cdr lst))
		      (return-from outer sub)))))))

(defun find-max-sub-from-str (str)
  (find-max-sub (sort-strings (split-strings str)))) 

(defun find-max-sub-from-file (filename)
  (with-open-file (input filename :direction :input)
    (let ((str (read-line input nil)))
      (find-max-sub-from-str str))))

