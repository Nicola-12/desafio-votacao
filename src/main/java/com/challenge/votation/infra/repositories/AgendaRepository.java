package com.challenge.votation.infra.repositories;

import com.challenge.votation.domain.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long>
{
}
