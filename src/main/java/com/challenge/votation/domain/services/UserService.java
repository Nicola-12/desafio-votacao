package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
import com.challenge.votation.domain.exceptions.UserRegisterException;
import com.challenge.votation.domain.mappers.UserMapper;
import com.challenge.votation.domain.model.User;
import com.challenge.votation.infra.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    
    public UserResponseDTO create( UserRequestDTO userRequestDTO )
    {
        userRepository.findByCpf( userRequestDTO.cpf() )
                      .ifPresent( user -> {
                          throw new UserRegisterException( "Usuário já está cadastrado" );
                      } );
        
        User newUser = userRepository.save( UserMapper.INSTANCE.dtoToEntity( userRequestDTO ) );
        
        return UserMapper.INSTANCE.entityToDto( newUser );
    }
    
    public List<UserResponseDTO> findAll()
    {
        return userRepository.findAll()
                             .stream()
                             .map( UserMapper.INSTANCE::entityToDto )
                             .toList();
    }
}
