package com.challenge.votation.domain.mappers;

import com.challenge.votation.application.dto.response.VoteResponseDTO;
import com.challenge.votation.domain.model.Vote;
import com.challenge.votation.infra.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( config = BaseMapperConfig.class, uses = { UserMapper.class, SessionMapper.class } )
public interface VoteMapper {
    
    VoteMapper INSTANCE = Mappers.getMapper( VoteMapper.class );
    
    Vote dtoToEntity( VoteResponseDTO dto );
    
    VoteResponseDTO entityToDto( Vote entity );
}
