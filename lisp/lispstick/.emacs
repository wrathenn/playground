(setq inferior-lisp-program "sbcl --dynamic-space-size 1024")
(add-to-list 'load-path (concat (getenv "HOME") "\\" (getenv "SLIME")))
(require 'slime)
(slime-setup '(slime-fancy))
(define-key global-map (kbd "<f12>") 'slime-selector)
(slime)

(add-to-list 'custom-theme-load-path "~/.emacs.d/themes/")
(set-frame-font "JetBrains Mono 12" nil t)

;; Copy/paste
(global-set-key (kbd "C-c") 'kill-ring-save)
(global-set-key (kbd "C-v") 'yank)
(global-set-key (kbd "C-x") 'kill-region)
(global-set-key (kbd "C-z") 'undo)
(global-set-key (kbd "C-y") 'redo)
(global-set-key (kbd "C-a") 'mark-whole-buffer)
(global-set-key (kbd "C-s") 'save-buffer)

;; Other
(global-set-key (kbd "C-/") 'comment-or-uncomment-region)
(global-set-key (kbd "C-+") 'text-scale-increase)
(global-set-key (kbd "C--") 'text-scale-decrease)

;; Buffers and windows
(global-set-key (kbd "C-M-d") 'next-buffer)
(global-set-key (kbd "C-M-a") 'previous-buffer)
(global-set-key (kbd "C-<tab>") 'other-window)
(global-set-key (kbd "<esc>") 'keyboard-quit)
(global-set-key (kbd "C-.") 'kill-buffer)

(setq mouse-wheel-scroll-amount '(1 ((shift) . 1) ((control) . nil)))
(setq mouse-wheel-progressive-speed nil)

(define-key slime-mode-map (kbd "TAB")
  'slime-indent-and-complete-symbol)
(define-key slime-mode-map (kbd "C-c TAB")
  'slime-complete-symbol)
(define-key slime-mode-map (kbd "M-x")
  'slime-eval-last-expression)

(define-key slime-repl-mode-map (kbd "TAB")
  'slime-indent-and-complete-symbol)
(define-key slime-repl-mode-map (kbd "C-c TAB")
  'slime-complete-symbol)
(define-key slime-repl-mode-map (kbd "M-x")
  'slime-eval-last-expression)

(setq w32-use-visible-system-caret t)
(cua-mode)
(show-paren-mode)

(define-common-lisp-style "my-indent-style"
"My custom indent style."
(:inherit "modern")
(:variables
  (lisp-loop-indent-subclauses t))    
(:indentation
  (if (4 2 2))
  (define (&lambda 2))
  (with-gensyms ((&whole 4 &rest 1) &body))
  (once-only (as with-gensyms))))

(setq common-lisp-style-default "my-indent-style")

