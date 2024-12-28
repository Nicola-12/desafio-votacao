package com.challenge.votation.domain.mappers;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import com.challenge.votation.domain.model.Agenda;
import com.challenge.votation.infra.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( config = BaseMapperConfig.class )
public interface AgendaMapper
{
    AgendaMapper INSTANCE = Mappers.getMapper( AgendaMapper.class );
    
    Agenda dtoToEntity( AgendaRequestDTO dto );
    
    AgendaResponseDTO entityToDto( Agenda entity );
}
