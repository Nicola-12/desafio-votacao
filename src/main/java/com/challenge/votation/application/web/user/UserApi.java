package com.challenge.votation.application.web.user;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag( name = "User", description = "Operações de Usuário" )
@RequestMapping( value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE )
public interface UserApi
{
    @Operation( description = "Realiza o cadastro de um usuário" )
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    UserResponseDTO create( @RequestBody @Valid UserRequestDTO body );
    
    @Operation( description = "Retorna uma lista com todos os usuários cadastrados" )
    @GetMapping
    @ResponseStatus( HttpStatus.OK )
    Collection<UserResponseDTO> findAll();
    
}
