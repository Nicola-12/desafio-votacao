package com.challenge.votation.application.web.user;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
import com.challenge.votation.domain.services.UserService;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController
    implements
        UserApi
{
    private final UserService service;
    
    @Override
    public UserResponseDTO create( UserRequestDTO body )
    {
        return service.create( body );
    }
    
    @Override
    public Collection<UserResponseDTO> findAll()
    {
        return service.findAll();
    }
}
