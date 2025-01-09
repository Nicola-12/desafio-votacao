package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.response.CpfFacadeResponseDTO;
import com.challenge.votation.domain.exceptions.NotFoundException;
import com.challenge.votation.infra.utilities.CPFUtils;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class CpfFacadeService
{
    private final Random random = new Random();
    
    public CpfFacadeResponseDTO validate( String cpf )
    {
        if ( !CPFUtils.isCpfValid( cpf ) )
        {
            throw new NotFoundException( "CPF inválido" );
        }
        
        return new CpfFacadeResponseDTO( random.nextBoolean() ? CpfFacadeResponseDTO.Status.ABLE_TO_VOTE : CpfFacadeResponseDTO.Status.UNABLE_TO_VOTE );
    }
}
