(ql:quickload "fiveam")
(use-package :fiveam)

(load "d:/Progs/lisp_4/defense.lisp")

(test test-sub-find
  "Testing function for finding the biggest substring."
  (is (equal (find-max-sub-from-file "d:/Progs/lisp_4/tests/1-one-str.txt") "ONE STRING") "1 - One String Test")
  (is (equal (find-max-sub-from-file "d:/Progs/lisp_4/tests/2-zero-str.txt") nil) "2 - Empty String Test")
  (is (equal (find-max-sub-from-file "d:/Progs/lisp_4/tests/3-empty-file.txt") nil) "3 - Empty File Test")
  (is (equal (find-max-sub-from-file "d:/Progs/lisp_4/tests/4-no-subs.txt") nil) "4 - No Substrings Test")
  (is (equal (find-max-sub-from-file "d:/Progs/lisp_4/tests/5-normal-test.txt") "ELL") "5 - Normal Test")
)
(run! 'test-sub-find)

(defmacro measure-time (&rest body)
  "Measure the time it takes to evaluate BODY."
  `(let ((time (current-time)))
     ,@body
     (message "%.06f" (float-time (time-since time)))))

(time (dotimes (i 1000000) (find-max-sub-from-str "(HELLO)(ELLO)(FELLOW)")))
