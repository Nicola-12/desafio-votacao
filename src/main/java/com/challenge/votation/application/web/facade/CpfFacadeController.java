package com.challenge.votation.application.web.facade;

import com.challenge.votation.application.dto.response.CpfFacadeResponseDTO;
import com.challenge.votation.domain.services.CpfFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CpfFacadeController
        implements
        CpfFacadeApi
{
    private final CpfFacadeService cpfFacadeService;
    
    @Override
    public CpfFacadeResponseDTO validate( String cpf )
    {
        return cpfFacadeService.validate( cpf );
    }
}
