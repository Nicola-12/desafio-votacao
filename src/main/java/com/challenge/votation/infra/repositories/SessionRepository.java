package com.challenge.votation.infra.repositories;

import com.challenge.votation.domain.model.Session;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    
    List<Session> findAllByAgendaIdAndFinishedAtAfter( Long agendaId, LocalDateTime finishedAt );
}