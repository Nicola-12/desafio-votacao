package com.challenge.votation.domain.mappers;

import com.challenge.votation.application.dto.request.SessionRequestDTO;
import com.challenge.votation.application.dto.response.SessionResponseDTO;
import com.challenge.votation.domain.model.Session;
import com.challenge.votation.infra.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( config = BaseMapperConfig.class, uses = AgendaMapper.class )
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper( SessionMapper.class );
    
    Session dtoToEntity( SessionRequestDTO dto );
    
    SessionResponseDTO entityToDto( Session entity );
}
