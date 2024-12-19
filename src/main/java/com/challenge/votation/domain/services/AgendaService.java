package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
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
        Agenda agenda = new Agenda();
        agenda.setName( body.name() );
        agenda.setDescription( body.description() );
        
        Agenda added = repository.save( agenda );
        
        return new AgendaResponseDTO( added.getId(), added.getName(), added.getDescription() );
    }
    
    public List<AgendaResponseDTO> findAll()
    {
        return repository.findAll()
                         .stream()
                         .map( agenda -> new AgendaResponseDTO( agenda.getId(), agenda.getName(), agenda.getDescription() ) )
                         .toList();
    }
}
