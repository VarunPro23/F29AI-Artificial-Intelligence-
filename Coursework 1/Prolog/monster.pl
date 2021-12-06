/* This is Part 2 of Coursework 1. This coursework is part of the course F29AI, Artificial Intelligence and Intelligent Agents.
This program is about Knowledge representation problem. The program is about a monster game as given in the 
coursework specifications sheet.

@Author: Varun Senthil Kumar, H00332328 
*/

/* Sub Part 1

Encode information about the monsters and their abilities using Prolog facts of the following form:−
    a. type(t): to represent the idea that tis a basic type.
    b. monster(mo,t): to represent the idea that mo is a monster of type t.
    c. move(mv,t): to represent the idea that mv is a move of type t.
    d. monsterMove(mo,mv): to represent the idea that monster mo has a move mv.
*/

% type(t) : to represent the idea that mo is a monster of type t.
type(normal).
type(dragon).
type(ghost).
type(flying).
type(fighting).

% monster(mo,t) : to represent the idea that mo is a monster of type t.
monster(bewear,normal).
monster(fraxure,dragon).
monster(polteageist,ghost).
monster(rookidee,flying).
monster(sirfetchd,fighting).

% move(mv,t) : to represent the idea that mv is a move of type t.
move(strength,normal).
move(takedown,normal).
move(falseSwipe,normal).
move(facade,normal).
move(teaTime,normal).
move(furyAttack,normal).
move(bodySlam,normal).

move(superpower,fighting).
move(revenge,fighting).
move(brickBreak,fighting).
move(closeCombat,fighting).

move(shadowClaw,ghost).
move(shadowBall,ghost).
move(astonish,ghost).

move(peck,flying).
move(braveBird,flying).

move(dragonClaw,dragon).
move(outrage,dragon).

% monsterMove(mo,mv): to represent the idea that monster mo has a move mv.

monsterMove(bewear,strength).
monsterMove(bewear,takedown).
monsterMove(bewear,superpower).
monsterMove(bewear,shadowClaw).

monsterMove(fraxure,falseSwipe).
monsterMove(fraxure,dragonClaw).
monsterMove(fraxure,outrage).
monsterMove(fraxure,shadowClaw).

monsterMove(polteageist,facade).
monsterMove(polteageist,shadowBall).
monsterMove(polteageist,astonish).
monsterMove(polteageist,teaTime).

monsterMove(rookidee,revenge).
monsterMove(rookidee,peck).
monsterMove(rookidee,braveBird).
monsterMove(rookidee,furyAttack).

monsterMove(sirfetchd,brickBreak).
monsterMove(sirfetchd,braveBird).
monsterMove(sirfetchd,bodySlam).
monsterMove(sirfetchd,closeCombat).


/* Sub Part 2

Encode effectiveness information using Prolog facts of the form typeEffectiveness(t1,t2,e):
a move of type t1 used against monsters of type t2 has effectiveness e.

*/

typeEffectiveness(dragon,dragon,strong).
typeEffectiveness(dragon,fighting,ordinary).
typeEffectiveness(dragon,flying,ordinary).
typeEffectiveness(dragon,ghost,ordinary).
typeEffectiveness(dragon,normal,ordinary).

typeEffectiveness(fighting,dragon,ordinary).
typeEffectiveness(fighting,fighting,ordinary).
typeEffectiveness(fighting,flying,weak).
typeEffectiveness(fighting,ghost,superweak).
typeEffectiveness(fighting,normal,strong).

typeEffectiveness(flying,dragon,ordinary).
typeEffectiveness(flying,fighting,strong).
typeEffectiveness(flying,flying,ordinary).
typeEffectiveness(flying,ghost,ordinary).
typeEffectiveness(flying,normal,ordinary).

typeEffectiveness(ghost,dragon,ordinary).
typeEffectiveness(ghost,fighting,ordinary).
typeEffectiveness(ghost,flying,ordinary).
typeEffectiveness(ghost,ghost,strong).
typeEffectiveness(ghost,normal,superweak).

typeEffectiveness(normal,dragon,ordinary).
typeEffectiveness(normal,fighting,ordinary).
typeEffectiveness(normal,flying,ordinary).
typeEffectiveness(normal,ghost,superweak).
typeEffectiveness(normal,normal,ordinary).


/* Sub Part 3

Encode basic effectiveness information using Prolog facts of the form moreEffective(e1,e2): 
e1 is more effective than e2. 
You should only encode the strict ordering on effectiveness in this way, i.e.,strong is more effective than ordinary, ordinary is 
more effective than weak, and weak is more effective than superweak.

*/

moreEffective(strong,ordinary).
moreEffective(ordinary,weak).
moreEffective(weak,superweak).


/* Sub Part 4

Encode transitive effectiveness information using a Prolog rule of the form moreEffectiveThan(E1,E2): 
E1 is more effective than E2. 
The rule should cover information not represented by the facts in part 3, e.g., strong is more effective than weak, ordinary is
more effective than superweak, etc. E1 and E2 are variables in this definition.

*/

moreEffectiveThan(E1,E2) :- moreEffective(E1,X) , moreEffective(Y,E2).


/* Sub Part 5

Define a Prolog rule called monsterMoveMatch(MO,MV) which represents the idea that monster MO has a move MV 
and that MO and MV have the same type. MO and MV should be variables in your rule definition.

*/

monsterMoveMatch(MO,MV) :- monsterMove(MO,MV) , (monster(MO,X) , move(MV,X)).


/* Sub Part 6   

Define a Prolog rule called moreEffectiveMove(MV1,MV2,T) to represent the idea that move MV1 is more effective 
than move MV2 against monsters of type T. MV1, MV2, and T should be variables in your rule definition.

*/

moreEffectiveMove(MV1,MV2,T) :- move(MV1,X) , move(MV2,Y) , typeEffectiveness(X,T,Z), typeEffectiveness(Y,T,W), moreEffectiveThan(Z,W).


/* Sub Part 7   

Define a Prolog rule called moreEffectiveMonster(MO1,MV1,MO2,MV2) to represent the idea that if monster MO1 
performs move MV1 and monster MO2 performs move MV2 then MV1 is more effective against MO2 than MV2 is against MO1.
MO1, MV1, MO2, and MV2 should be variables in your rule definition.

*/

moreEffectiveMonster(MO1,MV1,MO2,MV2) :- monster(MO1,MOT1) , monster(MO2,MOT2) , move(MV1,MVT1) , move(MV2,MVT2) , 
                                         typeEffectiveness(MVT1,MOT2,X) , typeEffectiveness(MVT2,MOT1,Y) ,
                                         moreEffectiveThan(X,Y).  