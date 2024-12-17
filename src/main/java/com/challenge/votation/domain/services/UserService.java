package com.challenge.votation.domain.services;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
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
        User user = new User();
        user.setName( userRequestDTO.name() );
        user.setCpf( userRequestDTO.cpf() );
        
        userRepository.save( user );
        
        return new UserResponseDTO( user.getId(), user.getName(), user.getCpf() );
    }
    
    public List<UserResponseDTO> findAll()
    {
        return userRepository.findAll()
                             .stream()
                             .map( user -> new UserResponseDTO( user.getId(), user.getName(), user.getCpf() ) )
                             .toList();
    }
}
