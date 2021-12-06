;-------------------------Problem 1----------------------------
;
;This is the first problem.
;
;In this problem, we try to move a personnel from a flat terrain to a hilly terrain


(define (problem planet_problem-1) 
    (:domain planet_domain)
(:objects 
    flat                            ; terrain
    hilly
    mountainous
    
    commander                       ; personnel
    science_officer
)

(:init
   (personnel commander)
   
   (region flat)
   (region hilly)
   (region mountainous)
   
   (personnel_at_region commander flat)
   (adj_regions flat hilly)
   (adj_regions hilly flat)
   (adj_regions hilly mountainous)
   (adj_regions mountainous flat)
)

(:goal (and
    (personnel_at_region commander hilly))
)

)
