package com.challenge.votation.infra.repositories;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.domain.mappers.AgendaMapper;
import com.challenge.votation.domain.mappers.UserMapper;
import com.challenge.votation.domain.model.Agenda;
import com.challenge.votation.domain.model.User;
import com.challenge.votation.domain.model.Vote;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles( "test" )
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE )
class VoteRepositoryTest
{
    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AgendaRepository agendaRepository;
    
    private User testUser;
    private Agenda testAgenda;
    
    @BeforeEach
    void setUp()
    {
        testUser = userRepository.save( UserMapper.INSTANCE.dtoToEntity( new UserRequestDTO( "Test User", "99999999999" ) ) );
        testAgenda = agendaRepository.save( AgendaMapper.INSTANCE.dtoToEntity( new AgendaRequestDTO( "Test Agenda", "Test Agenda description" ) ) );
        
        Vote vote = new Vote();
        vote.setAgenda( testAgenda );
        vote.setUser( testUser );
        vote.setVote( true );
        
        voteRepository.save( vote );
    }
    
    @Test
    @DisplayName( "Should return a vote by user id and agenda id" )
    void findByUserIdAndAgendaId()
    {
        Optional<Vote> vote = voteRepository.findByUserIdAndAgendaId( testUser.getId(), testAgenda.getId() );
        
        assertThat( vote ).isPresent();
        assertThat( vote.get().getUser() ).isEqualTo( testUser );
        assertThat( vote.get().getAgenda() ).isEqualTo( testAgenda );
        assertThat( vote.get().getVote() ).isTrue();
    }
    
    @Test
    @DisplayName( "Should return a list of votes by agenda id" )
    void findAllByAgendaId()
    {
        List<Vote> votes = voteRepository.findAllByAgendaId( testAgenda.getId() );
        
        assertThat( votes).isNotEmpty();
        assertThat( votes).hasSize( 1 );
        assertThat( votes.getFirst().getUser() ).isEqualTo( testUser);
        assertThat( votes.getFirst().getAgenda() ).isEqualTo(testAgenda);
        assertThat( votes.getFirst().getVote() ).isTrue();
    }
}