(define(domain planet_domain)
    (:requirements
        :strips
        :typing
        :equality
        :adl
    )

    (:types
        ;flat hilly mountainous - region                                 ; region types
        ;commander engineer scienceOfficer security - personnel          ; personnel types
        ;shieldEmitter terraformingMach - equipment                      ; equipment types
    )

    (:predicates
        
        (personnel ?p)                                ; declare personnel
        (region ?r)                                   ; declare region              
        (adj_regions ?r1 ?r2)                         ; regions adjacent to each other     
        (personnel_at_region ?p ?r)            ; personnel at the region

        (equipment ?e)                                   ; declare equipment 
        (equipment_at_region ?e ?r)            ; equipment at the region
        (terra_region ?r1 ?r2)                           ; terraform the region from one terrain to another   

        (shield_active)
        (shield_not_active)
    )

    ; This action is to move personnel from one terrain to another terrain

    (:action move_personnel
        :parameters (?p ?fr ?to)
        
        :precondition (and
            (personnel ?p)
            (region ?fr)
            (region ?to)
            (personnel_at_region ?p ?fr)
            (or(adj_regions ?fr ?to)(adj_regions ?to ?fr))
        )

        :effect (and 
            (not (personnel_at_region ?p ?fr))
            (personnel_at_region ?p ?to)
        )
    )

    ; This action is to move equipment from one terrain to another  

    (:action move_equipment
        :parameters (?p ?e ?fr ?to)
        :precondition (and 
            (equipment ?e)
            (personnel ?p)
            
            (region ?fr)
            (region ?to)
            
            (personnel_at_region ?p ?fr)
            (or(adj_regions ?fr ?to)(adj_regions ?to ?fr))
            (equipment_at_region ?e ?fr)    
        )
        :effect (and 
            (not (equipment_at_region ?e ?fr))
            (equipment_at_region ?e ?to)
        )
    )

    ; This action is to terraform a flat or hilly terrain to a flat terrain 

    (:action terraform_region
        :parameters (?p1 ?p2 ?e1 ?e2 ?fr ?to)
        :precondition (and 
            (equipment ?e1)
            (equipment ?e2)
            (personnel ?p1)
            (personnel ?p2)

            (region ?fr)
            (region ?to)
            
            (personnel_at_region ?p1 ?fr)
            (personnel_at_region ?p2 ?fr)
            (equipment_at_region ?e1 ?fr)
            (equipment_at_region ?e2 ?fr)    
        )
        :effect (and 
            (not (region ?fr))
            (region ?to)
        )
    )
     
)