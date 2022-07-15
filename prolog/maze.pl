go(coords(X, Y), _, _, _, _, coords(X, Y), [], _) :- !.
go(T1, Size, Barrier, T1, T2, F, ["Teleport"|Res], Length) :- go(T2, Size, Barrier, T1, T2, F, Res, Length), !.


go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(Nx, Y)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Nx is X + 2, go(coords(Nx, Y), Size, Barrier, T1, T2, F, Res, NL).

go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(X, Ny)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Ny is Y + 1, go(coords(X, Ny), Size, Barrier, T1, T2, F, Res, NL).

go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(Nx, Y)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Nx is X - 1, go(coords(Nx, Y), Size, Barrier, T1, T2, F, Res, NL).


go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(X, Ny)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Ny is Y - 1, go(coords(X, Ny), Size, Barrier, T1, T2, F, Res, NL).


go(coords(X, Y), _, _, _, _, coords(X, Y), [], _) :- !.
go(T1, Size, Barrier, T1, T2, F, ["Teleport"|Res], Length) :- go(T2, Size, Barrier, T1, T2, F, Res, Length), !.


go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(Nx, Y)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Nx is X + 2, go(coords(Nx, Y), Size, Barrier, T1, T2, F, Res, NL).

go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(X, Ny)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Ny is Y + 1, go(coords(X, Ny), Size, Barrier, T1, T2, F, Res, NL).

go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(Nx, Y)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Nx is X - 1, go(coords(Nx, Y), Size, Barrier, T1, T2, F, Res, NL).


go(coords(X, Y), Size, Barrier, T1, T2, F, [coords(X, Ny)|Res], Length) :- X >= 0, Y >= 0, X < Size, not(Y = Barrier), Y < Size,
    Length < Size * Size, NL = Length + 2, Ny is Y - 1, go(coords(X, Ny), Size, Barrier, T1, T2, F, Res, NL).
