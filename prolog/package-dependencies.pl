%package(name, ver).
package(snap, 2.5).
package(snap, 2.4).

package(py, 2.7).
package(py, 3.7).
package(py, 3.10).

%package(matplotlib, 1.5).
%package(vscode, 1.1).
%package(vscode, 1.2).
%package(vscode, 1.3).
%package(vslib, 1.1).
%package(vslib, 1.2).
%package(vslib, 1.3).

%package(snaplib, 1.2).

%package(unique, 1.0).

% dependency(package_name, depends_on_which). 
dependency(package(snap, 2.5), package(py, 3.7)).
dependency(package(snap, 2.5), package(snaplib, 1.2)).

dependency(package(matplotlib, 1.5), package(vslib, 1.3)).
dependency(package(matplotlib, 1.5), package(py, 2.7)).

dependency(package(vscode, 1.1), package(vslib, 1.1)).
dependency(package(vscode, 1.2), package(vslib, 1.2)).
dependency(package(vscode, 1.3), package(vslib, 1.3)).

% incompatible
compp(P1, P2) :-not(incompatible(P1, P2)), not(incompatible(P2, P1)), not(P1 = P2).
incompatible(package(snap, 2.5), package(snap, 2.4)).
incompatible(package(vslib, 1.1), package(vslib, 1.2)).
incompatible(package(vslib, 1.1), package(vslib, 1.3)).
incompatible(package(vslib, 1.2), package(vslib, 1.3)).

incompatible(package(unique, 1.0), package(snap, 2.5)).
incompatible(package(unique, 1.0), package(snap, 2.4)).
incompatible(package(unique, 1.0), package(py, 2.7)).
incompatible(package(unique, 1.0), package(py, 3.7)).
incompatible(package(unique, 1.0), package(py, 3.10)).
incompatible(package(unique, 1.0), package(matplotlib, 1.5)).
incompatible(package(unique, 1.0), package(vscode, 1.1)).
incompatible(package(unique, 1.0), package(vscode, 1.2)).
incompatible(package(unique, 1.0), package(vscode, 1.3)).
incompatible(package(unique, 1.0), package(vslib, 1.1)).
incompatible(package(unique, 1.0), package(vslib, 1.2)).
incompatible(package(unique, 1.0), package(vslib, 1.3)).
incompatible(package(unique, 1.0), package(snaplib, 1.2)).

% collect(PackList, Count).
collect(Rlst, Path) :- package(N, V), not(member(package(N, V), Path)), collect(A, [package(N, V) | Path]), Rlst = A.
collect(Path, Path).

% install_package() :- 

collect_dependencies(Pack, Res, Path) :-
    dependency(Pack, package(N, V)),
    not(member(package(N, V), Path)),
    collect_dependencies(Pack, Res1, [package(N, V) | Path]),
    Res = Res1,
    !.
collect_dependencies(_, Path, Path).

check_compatibility(true, _, []) :- write(asd), !.
check_compatibility(Res, Pack, [Installed | Rest]) :-
    write(test),
    check_compatibility(Res1, Pack, Rest),
    Res = Res1, Res1,
    Res is compp(Pack, Installed).

check_compatibility(Res, package(snap, 2.5), [package(py, 3.7)]).
    

%install_packages(Res, []) :- !.
%install_packages(Res, [Cur | RestPacks]) :-
%    install_packages(Res1, RestPacks),
%    not(member(Cur, Res1)),
%    compp(Cur
    
    
%collect_compatible(Rlst, Path) :- package(N, V), not(member(package(N, V)%package(name, ver).
package(snap, 2.5).
package(snap, 2.4).

package(py, 2.7).
package(py, 3.7).
package(py, 3.10).

%package(matplotlib, 1.5).
%package(vscode, 1.1).
%package(vscode, 1.2).
%package(vscode, 1.3).
%package(vslib, 1.1).
%package(vslib, 1.2).
%package(vslib, 1.3).

%package(snaplib, 1.2).

%package(unique, 1.0).

% dependency(package_name, depends_on_which). 
dependency(package(snap, 2.5), package(py, 3.7)).
dependency(package(snap, 2.5), package(snaplib, 1.2)).

dependency(package(matplotlib, 1.5), package(vslib, 1.3)).
dependency(package(matplotlib, 1.5), package(py, 2.7)).

dependency(package(vscode, 1.1), package(vslib, 1.1)).
dependency(package(vscode, 1.2), package(vslib, 1.2)).
dependency(package(vscode, 1.3), package(vslib, 1.3)).

% incompatible
compp(P1, P2) :-not(incompatible(P1, P2)), not(incompatible(P2, P1)), not(P1 = P2).
incompatible(package(snap, 2.5), package(snap, 2.4)).
incompatible(package(vslib, 1.1), package(vslib, 1.2)).
incompatible(package(vslib, 1.1), package(vslib, 1.3)).
incompatible(package(vslib, 1.2), package(vslib, 1.3)).

incompatible(package(unique, 1.0), package(snap, 2.5)).
incompatible(package(unique, 1.0), package(snap, 2.4)).
incompatible(package(unique, 1.0), package(py, 2.7)).
incompatible(package(unique, 1.0), package(py, 3.7)).
incompatible(package(unique, 1.0), package(py, 3.10)).
incompatible(package(unique, 1.0), package(matplotlib, 1.5)).
incompatible(package(unique, 1.0), package(vscode, 1.1)).
incompatible(package(unique, 1.0), package(vscode, 1.2)).
incompatible(package(unique, 1.0), package(vscode, 1.3)).
incompatible(package(unique, 1.0), package(vslib, 1.1)).
incompatible(package(unique, 1.0), package(vslib, 1.2)).
incompatible(package(unique, 1.0), package(vslib, 1.3)).
incompatible(package(unique, 1.0), package(snaplib, 1.2)).

% collect(PackList, Count).
collect(Rlst, Path) :- package(N, V), not(member(package(N, V), Path)), collect(A, [package(N, V) | Path]), Rlst = A.
collect(Path, Path).

% install_package() :- 

collect_dependencies(Pack, Res, Path) :-
    dependency(Pack, package(N, V)),
    not(member(package(N, V), Path)),
    collect_dependencies(Pack, Res1, [package(N, V) | Path]),
    Res = Res1,
    !.
collect_dependencies(_, Path, Path).

check_compatibility(true, _, []) :- write(asd), !.
check_compatibility(Res, Pack, [Installed | Rest]) :-
    write(test),
    check_compatibility(Res1, Pack, Rest),
    Res = Res1, Res1,
    Res is compp(Pack, Installed).

check_compatibility(Res, package(snap, 2.5), [package(py, 3.7)]).
    

%install_packages(Res, []) :- !.
%install_packages(Res, [Cur | RestPacks]) :-
%    install_packages(Res1, RestPacks),
%    not(member(Cur, Res1)),
%    compp(Cur
    
    
%collect_compatible(Rlst, Path) :- package(N, V), not(member(package(N, V)
