package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.request.SessionRequestDTO;
import com.challenge.votation.application.dto.response.SessionResponseDTO;
import com.challenge.votation.domain.exceptions.ActiveSessionException;
import com.challenge.votation.domain.exceptions.NotFoundException;
import com.challenge.votation.domain.mappers.SessionMapper;
import com.challenge.votation.domain.model.Agenda;
import com.challenge.votation.domain.model.Session;
import com.challenge.votation.infra.repositories.AgendaRepository;
import com.challenge.votation.infra.repositories.SessionRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.catalina.session.TooManyActiveSessionsException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService
{
    private final SessionRepository sessionRepository;
    private final AgendaRepository agendaRepository;
    
    public SessionResponseDTO create( SessionRequestDTO body )
    {
        Agenda agenda = agendaRepository.findById( body.agendaId() )
                                        .orElseThrow( () -> new NotFoundException( "Pauta não foi encontrada" ) );
        
        Session session = SessionMapper.INSTANCE.dtoToEntity( body );
        session.setAgenda( agenda );
        
        List<Session> activeSessions = sessionRepository.findAllByAgendaIdAndFinishedAtAfter( body.agendaId(), LocalDateTime.now() );
        
        if ( ! activeSessions.isEmpty() ) {
            throw new ActiveSessionException( "Já existe uma sessão ativa para esta pauta" );
        }
        
        session = sessionRepository.save( session );
        
        return SessionMapper.INSTANCE.entityToDto( session );
    }
    
    public List<SessionResponseDTO> findAll()
    {
        return sessionRepository.findAll()
                                .stream()
                                .map( SessionMapper.INSTANCE::entityToDto )
                                .toList();
    }
}
