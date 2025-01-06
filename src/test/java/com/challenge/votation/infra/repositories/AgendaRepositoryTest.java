package com.challenge.votation.infra.repositories;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.domain.mappers.AgendaMapper;
import com.challenge.votation.domain.model.Agenda;
import jakarta.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.*;
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
class AgendaRepositoryTest
{
    @Autowired
    AgendaRepository agendaRepository;
    
    @Autowired
    EntityManager entityManager;
    
    @Test
    @DisplayName( "Should return true when exists a agenda with the given name" )
    public void findAgendaByIdSuccess()
    {
        AgendaRequestDTO agendaRequestDTO = new AgendaRequestDTO( "Teste", "descrição" );
        
        createAgenda( agendaRequestDTO );
        
        Agenda agenda = agendaRepository.findById( 1L ).orElse( null );
        
        assertNotNull( agenda );
    }
    
    @Test
    @DisplayName( "Should return false when there is no agenda with the given id" )
    public void findAgendaByIdFail()
    {
        Agenda agenda = agendaRepository.findById( 1L ).orElse( null );
        
        assertNull( agenda );
    }
    
    private void createAgenda( AgendaRequestDTO agendaRequestDTO )
    {
        Agenda newAgenda = AgendaMapper.INSTANCE.dtoToEntity( agendaRequestDTO );
        entityManager.persist( newAgenda );
    }
}