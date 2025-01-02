package com.challenge.votation.infra.repositories;

import com.challenge.votation.domain.model.Vote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long>
{
    Optional<Vote> findByUserIdAndAgendaId( Long userId, Long agendaId );
}
