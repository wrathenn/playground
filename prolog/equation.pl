eq1([A], A, []) :- !.
% A + T
eq1([A | T], Res, Path) :- eq1(T, Res1, Path1), Res is A + Res1, Path = [+ | Path1].
% A - B ... T
eq1([A, B | T], Res, Path) :- eq1([-B | T], Res1, Path1), Res is A + Res1, Path = [- | Path1].
% A * B ... T
eq1([A, B | T], Res, Path) :- C is A * B, eq1([C | T], Res, Path1), Path = [ * | Path1].
% A / B ... T
eq1([A, B | T], Res, Path) :- C is A / B, eq1([C | T], Res, Path1), Path = [ / | Path1].

% Попытки сделать по
% https://learntutorials.net/ru/prolog/topic/2426/%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B0%D1%82%D0%B8%D0%BA%D0%B8-%D1%81-%D0%BE%D0%BF%D1%80%D0%B5%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%BD%D1%8B%D0%BC%D0%B8-%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0%D0%BC%D0%B8--dcg-
plus(A, B, C) :- C is A + B.
minus(A, B, C) :- C is A - B.
mult(A, B, C) :- C is A * B.
divide(A, B, C) :- B =\= 0, C is A / B.

% Это как
% exp(C,A,B) :- A=[AA, + | A2], exp(C2,A2,B), plus(AA,C2,C)
exp(C) --> [A], [*], exp(B), {mult(A, B, C)}.
%exp(C) --> [A], [-], exp(B), {minus(A, B, C)}.
%exp(C) --> [A], [*], exp(B), {mult(A, B, C)}.
%exp(C) --> [A], [/], exp(B), {divide(A, B, C)}.
exp(X) --> [X].
% ?- phrase(exp(X), [1,+,2,+,3]).

