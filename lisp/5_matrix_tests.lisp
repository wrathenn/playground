(ql:quickload "fiveam")
(use-package :fiveam)

(load "d:/Progs/lisp_5/defense-qr-schur.lisp")

(test test-qr-list
  (is (qr-test '((1)) 1e-3) "1x1 QR check")
  (is (qr-test '((1 2 3) (4 1 5) (6 7 1)) 1e-3) "3x3 QR check")
  (is (qr-test '((1 2 3 4) (5 6 7 8) (9 10 11 12) (13 14 15 16)) 1e-3) "4x4 QR check")
  (is (qr-test '((14 20 13 14 15) (1 4 2 3 1) (12 15 17 3 39) (16 19 20 30 10)) 1e-3) "4x5 QR check")
  (is (qr-test '((12 13 14)) 1e-3) "1x3 QR check")
  (is (qr-test '((12 13 14) (15 4 5) (7 2 7) (13 19 0)) 1e-3) "4x3 QR check"))

(run! 'test-qr-list)

(test test-schur-list
  (is (schur-test '((1)) 1000 1e-3) "1x1 Schur check")
  (is (schur-test '((1 2 3) (4 1 5) (6 7 1)) 1000 1e-3) "3x3 Schur check")
  (is (schur-test '((1 2 3 4) (5 6 7 8) (9 10 11 12) (13 14 15 16)) 1000 1e-3) "4x4 Schur check"))

(run! 'test-schur-list)

(load "d:/Progs/lisp5/defense-array.lisp")

(test test-qr-arr
  (is (qr-test #2A((1)) 1e-3) "1x1 QR check")
  (is (qr-test #2A((1 2 3) (4 1 5) (6 7 1)) 1e-3) "3x3 QR check")
  (is (qr-test #2A((1 2 3 4) (5 6 7 8) (9 10 11 12) (13 14 15 16)) 1e-3) "4x4 QR check")
  (is (qr-test #2A((14 20 13 14 15) (1 4 2 3 1) (12 15 17 3 39) (16 19 20 30 10)) 1e-3) "4x5 QR check")
  (is (qr-test #2A((12 13 14)) 1e-3) "1x3 QR check"))

(run! 'test-qr-arr)

(test test-schur-arr
  (is (schur-test #2A((1)) 1000 1e-3) "1x1 Schur check")
  (is (schur-test #2A((1 2 3) (4 1 5) (6 7 1)) 1000 1e-3) "3x3 Schur check")
  (is (schur-test #2A((1 2 3 4) (5 6 7 8) (9 10 11 12) (13 14 15 16)) 1000 1e-3) "4x4 Schur check"))

(run! 'test-schur-arr)

(time (schur-test #2A((1 2 3 4) (5 6 7 8) (9 10 11 12) (13 14 15 16)) 1000 1e-3))
(time (schur-test '((1 2 3 4) (5 6 7 8) (9 10 11 12) (13 14 15 16)) 1000 1e-3))

; Для теста
(let* ((res (matrix-schur '((12 13 14)) 100000))
      (q (car res))
      (r (cadr res))
      (qt (caddr res)))
  (print 'q) (print q)
  (print 'r) (print r)
  (matrix-mult (matrix-mult q r) qt))

(let* ((res (matrix-qr #2A((12 13 14) (15 4 5) (7 2 7) (13 19 0))))
       (q (car res))
       (r (cadr res)))
  (print 'q) (print q)
  (print 'r) (print r)
  (matrix-mult q r))
