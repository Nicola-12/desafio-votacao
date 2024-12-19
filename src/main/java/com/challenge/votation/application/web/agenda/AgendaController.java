package com.challenge.votation.application.web.agenda;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import com.challenge.votation.domain.services.AgendaService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AgendaController
    implements
        AgendaApi
{
    private final AgendaService service;
    
    @Override
    public AgendaResponseDTO create( AgendaRequestDTO body )
    {
        return service.create( body );
    }
    
    @Override
    public List<AgendaResponseDTO> findAll()
    {
        return service.findAll();
    }
}
