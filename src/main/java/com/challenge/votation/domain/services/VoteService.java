package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.request.VoteRequestDTO;
import com.challenge.votation.application.dto.response.VoteResponseDTO;
import com.challenge.votation.application.dto.response.VoteResultResponseDTO;
import com.challenge.votation.domain.enums.VoteStatus;
import com.challenge.votation.domain.exceptions.NotFoundException;
import com.challenge.votation.domain.exceptions.SessionExpiredException;
import com.challenge.votation.domain.exceptions.UserAlreadyVotedException;
import com.challenge.votation.domain.mappers.AgendaMapper;
import com.challenge.votation.domain.mappers.VoteMapper;
import com.challenge.votation.domain.model.Agenda;
import com.challenge.votation.domain.model.Session;
import com.challenge.votation.domain.model.User;
import com.challenge.votation.domain.model.Vote;
import com.challenge.votation.infra.repositories.AgendaRepository;
import com.challenge.votation.infra.repositories.SessionRepository;
import com.challenge.votation.infra.repositories.UserRepository;
import com.challenge.votation.infra.repositories.VoteRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {
    
    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    
    public VoteResponseDTO vote( VoteRequestDTO body ) {
        User user = userRepository.findById( body.userId() )
                                  .orElseThrow( () -> new NotFoundException( "Usuário não foi encontrado" ) );
        
        Agenda agenda = agendaRepository.findById( body.agendaId() )
                                        .orElseThrow( () -> new NotFoundException( "Pauta não foi encontrada" ) );
        
        voteRepository.findByUserIdAndAgendaId( user.getId(), agenda.getId() )
                      .ifPresent( vote -> {
                          throw new UserAlreadyVotedException( "Usuário já votou nessa pauta" );
                      } );
        
        Session session = sessionRepository.findByAgendaId( agenda.getId() )
                                           .orElseThrow( () -> new NotFoundException( "Sessão não encontrada" ) );
        
        if ( session.getFinishedAt().isBefore( LocalDateTime.now() ) ) {
            throw new SessionExpiredException();
        }
        
        Vote vote = new Vote();
        vote.setAgenda( agenda );
        vote.setUser( user );
        vote.setVote( body.vote() );
        
        return VoteMapper.INSTANCE.entityToDto( voteRepository.save( vote ) );
    }
    
    public List<VoteResponseDTO> findAll() {
        return voteRepository.findAll()
                             .stream()
                             .map( VoteMapper.INSTANCE::entityToDto )
                             .toList();
    }
    
    public VoteResultResponseDTO result( Long agendaId ) {
        Agenda agenda = agendaRepository.findById( agendaId )
                                        .orElseThrow( () -> new NotFoundException( "Pauta não foi encontrada" ) );
        
        List<Vote> votes = voteRepository.findAllByAgendaId( agenda.getId() );
        
        if ( votes.isEmpty() ) {
            throw new NotFoundException( "Nenhum voto encontrado para a pauta" );
        }
        
        long acceptedVotes = votes.stream().filter( Vote::getVote ).count();
        long rejectedVotes = votes.size() - acceptedVotes;
        
        VoteStatus voteStatus = acceptedVotes == rejectedVotes ? VoteStatus.TIE : acceptedVotes > rejectedVotes ? VoteStatus.ACCEPTED : VoteStatus.REFUSED;
        
        return new VoteResultResponseDTO( voteStatus, AgendaMapper.INSTANCE.entityToDto( agenda ) );
    }
}
