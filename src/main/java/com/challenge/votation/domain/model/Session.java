package com.challenge.votation.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "session")
@Getter
@Setter
@NoArgsConstructor
public class Session {
    public static final int DEFAULT_DURATION = 1;
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @NotNull
    private String name;
    
    private String description;
    
    private Integer duration;
    
    @Column( name = "created_at" )
    private LocalDateTime createdAt;
    
    @Column( name = "finished_at" )
    private LocalDateTime finishedAt;
    
    @ManyToOne
    @JoinColumn( name = "agenda_id" )
    private Agenda agenda;
    
    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
        
        finishedAt = createdAt.plusMinutes( Optional.ofNullable( duration ).orElse( DEFAULT_DURATION ) );
    }
}
