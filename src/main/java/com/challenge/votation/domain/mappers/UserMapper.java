package com.challenge.votation.domain.mappers;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
import com.challenge.votation.domain.model.User;
import com.challenge.votation.infra.config.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper( config = BaseMapperConfig.class )
public interface UserMapper {
    
    User dtoToEntity( UserRequestDTO dto );
    
    UserResponseDTO entityToDto( User entity );
}
