package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import com.challenge.votation.domain.mappers.AgendaMapper;
import com.challenge.votation.domain.model.Agenda;
import com.challenge.votation.infra.repositories.AgendaRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgendaService
{
    private final AgendaRepository repository;
    
    public AgendaResponseDTO create( AgendaRequestDTO body )
    {
        Agenda added = repository.save( AgendaMapper.INSTANCE.dtoToEntity( body ) );
        
        return AgendaMapper.INSTANCE.entityToDto( added );
    }
    
    public List<AgendaResponseDTO> findAll()
    {
        return repository.findAll()
                         .stream()
                         .map( AgendaMapper.INSTANCE::entityToDto )
                         .toList();
    }
}
