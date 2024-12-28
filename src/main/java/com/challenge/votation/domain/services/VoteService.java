package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.request.VoteRequestDTO;
import com.challenge.votation.application.dto.response.VoteResponseDTO;
import com.challenge.votation.domain.exceptions.NotFoundException;
import com.challenge.votation.domain.mappers.VoteMapper;
import com.challenge.votation.domain.model.Agenda;
import com.challenge.votation.domain.model.User;
import com.challenge.votation.domain.model.Vote;
import com.challenge.votation.infra.repositories.AgendaRepository;
import com.challenge.votation.infra.repositories.UserRepository;
import com.challenge.votation.infra.repositories.VoteRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {
    
    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;
    
    public VoteResponseDTO vote( VoteRequestDTO body ) {
        User user = userRepository.findById( body.userId() )
                                  .orElseThrow( () -> new NotFoundException( "Usuário não foi encontrado" ) );
        
        Agenda agenda = agendaRepository.findById( body.agendaId() )
                                        .orElseThrow( () -> new NotFoundException( "Pauta não foi encontrada" ) );
        
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
}
