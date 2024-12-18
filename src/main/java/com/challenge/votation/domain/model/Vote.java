package com.challenge.votation.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "vote",
        uniqueConstraints = {
            @UniqueConstraint( columnNames = { "user_id", "agenda_id" } )
        }
)
@Getter
@Setter
@RequiredArgsConstructor
public class Vote
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    private Boolean vote;
    
    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User user;
    
    @ManyToOne
    @JoinColumn( name = "agenda_id" )
    private Agenda agenda;
}
