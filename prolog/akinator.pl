:- dynamic yes/1,no/1.

start :- nl,
      write('Think about character...'),nl,
      write('Answer yes if it is true:'),nl, nl,
      guess(Character),
      write('Your character is: '),
      write(Character),nl,
      undo.

/* hypotheses to be tested */
%guess(human) :- human, !.
guess(you) :- you, !.
guess(fumo) :- fumo, !.
guess(cthulhu) :- cthulhu, !.
guess(boar) :- boar, !.
guess(unknown).

% sub rules
human :- has_gender.
animal :- verify(is_animal), !.
animal :- not(human).
has_gender:- verify(is_male); verify(is_female), !.    
no(is_female):- yes(is_male).
yes(is_female):- no(is_male).

% characters rules
you :- human, verify(is_from_your_family), verify(looks_at_this_text), !.
fumo :- verify(is_female), verify(is_cool), verify(is_famous), !.
cthulhu :- animal, verify(is_evil), verify(is_cool), verify(doesnt_exist), !.
boar :- animal, verify(is_evil), verify(is_cool), !.

% ask a question
ask(Question) :-
    write('Your character '),
    write(Question),
    write('? '),
    read(Response), nl,
    (Response == yes ->
      	assert(yes(Question)) ;
       	assert(no(Question)), fail).


/* How to verify something */
verify(S) :- (yes(S) -> true; (no(S) -> fail ; ask(S))).

/* undo all yes/no assertions */
undo :- retract(yes(_)), fail. 
undo :- retract(no(_)), fail.
undo.

