package com.challenge.votation.application.web.session;

import com.challenge.votation.application.dto.request.SessionRequestDTO;
import com.challenge.votation.application.dto.response.SessionResponseDTO;
import com.challenge.votation.domain.services.SessionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SessionController
    implements
        SessionApi
{
    private final SessionService service;
    
    @Override
    public SessionResponseDTO create( SessionRequestDTO body )
    {
        return service.create( body );
    }
    
    @Override
    public List<SessionResponseDTO> findAll()
    {
        return service.findAll();
    }
}
