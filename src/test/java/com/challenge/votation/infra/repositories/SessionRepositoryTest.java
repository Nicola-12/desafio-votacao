package com.challenge.votation.infra.repositories;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.request.SessionRequestDTO;
import com.challenge.votation.domain.exceptions.NotFoundException;
import com.challenge.votation.domain.mappers.AgendaMapper;
import com.challenge.votation.domain.mappers.SessionMapper;
import com.challenge.votation.domain.model.Agenda;
import com.challenge.votation.domain.model.Session;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SessionRepositoryTest
{
    @Autowired
    AgendaRepository agendaRepository;
    
    @Autowired
    SessionRepository sessionRepository;
    
    @Autowired
    EntityManager entityManager;
    
    @Test
    @DisplayName( "Should return true when exists a session with the given agenda id" )
    public void startSessionSuccess()
    {
        Agenda agenda = buildAgenda();
        
        SessionRequestDTO sessionRequestDTO = new SessionRequestDTO( "Sessão 01", "descrição", 1, agenda.getId() );
        
        createSession( sessionRequestDTO, agenda );
        
        Optional<Session> result = sessionRepository.findByAgendaId( agenda.getId() );
        
        Assertions.assertThat( result.isPresent() ).isTrue();
    }
    
    @Test
    @DisplayName( "Should return false when there is session with the given agenda id" )
    public void sessionAlreadyStarted()
    {
        Agenda agenda = buildAgenda();
        
        SessionRequestDTO sessionRequestDTO = new SessionRequestDTO( "Sessão 01", "descrição", 4, agenda.getId() );
        
        createSession( sessionRequestDTO, agenda );
        
        List<Session> result = sessionRepository.findAllByAgendaIdAndFinishedAtAfter( agenda.getId(), LocalDateTime.now() );
     
        Assertions.assertThat( result.isEmpty() ).isFalse();
    }
    
    private void createSession( SessionRequestDTO sessionRequestDTO, Agenda agenda )
    {
        Session session = SessionMapper.INSTANCE.dtoToEntity( sessionRequestDTO );
        session.setAgenda( agenda );
        entityManager.persist( session );
    }
    
    private Agenda buildAgenda()
    {
        AgendaRequestDTO agendaRequestDTO = new AgendaRequestDTO( "Teste", "descrição" );
        
        Agenda agenda = createAgenda( agendaRequestDTO );
        
        agendaRepository.findById( agenda.getId() ).orElseThrow( () -> new NotFoundException( "Pauta não foi encontrada" ) );
        
        return agenda;
    }
    
    private Agenda createAgenda( AgendaRequestDTO agendaRequestDTO )
    {
        Agenda newAgenda = AgendaMapper.INSTANCE.dtoToEntity( agendaRequestDTO );
        entityManager.persist( newAgenda );
        return newAgenda;
    }
}