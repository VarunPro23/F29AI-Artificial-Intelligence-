;------------------Problem 2------------------------
;
;This is problem 2.
;
;This problem aims to terraform a terrain from either flat or hilly to flat.


(define (problem planet_problem-2) 
    (:domain planet_domain)
(:objects 

    commander                                       ; personnel
    science_officer
    engineer

    shieldEmitter                                   ; equipment
    terraformingMach

    flat                                            ; terrain
    hilly
    mountainous
)

(:init
    (personnel commander)                           ; initialize the personnel
    (personnel science_officer)
    (personnel engineer)

    (equipment shieldEmitter)                       ; initialize the equipment
    (equipment terraformingMach)

    (region flat)                                   ; initialize the terrain
    (region hilly)
    (region mountainous)

    (personnel_at_region science_officer flat)      ; initialize the personnel at the region
    (personnel_at_region engineer flat)

    (equipment_at_region shieldEmitter flat)        ; initialize the equipment at the region
    (equipment_at_region terraformingMach flat)

    (shield_not_active)

    (adj_regions flat hilly)                        ; initialize the regions adjacent to other regions
    (adj_regions hilly mountainous)
    (adj_regions mountainous flat)
    (adj_regions hilly flat)
)

(:goal (and
 (personnel_at_region science_officer hilly)
 (personnel_at_region engineer hilly)
 (equipment_at_region shieldEmitter hilly)
 (equipment_at_region terraformingMach hilly)
 (region flat)   
))

)
