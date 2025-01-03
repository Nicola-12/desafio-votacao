package com.challenge.votation.application.web.vote;

import com.challenge.votation.application.dto.request.VoteRequestDTO;
import com.challenge.votation.application.dto.response.VoteResponseDTO;
import com.challenge.votation.application.dto.response.VoteResultResponseDTO;
import com.challenge.votation.domain.services.VoteService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class VoteController
    implements
        VoteApi
{
    private final VoteService service;
    
    @Override
    public VoteResponseDTO vote( VoteRequestDTO body )
    {
        return service.vote( body );
    }
    
    @Override
    public List<VoteResponseDTO> findAll()
    {
        return service.findAll();
    }
    
    @Override
    public VoteResultResponseDTO result( Long agendaId )
    {
        return service.result( agendaId );
    }
}
