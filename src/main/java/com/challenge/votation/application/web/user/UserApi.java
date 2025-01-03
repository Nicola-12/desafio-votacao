package com.challenge.votation.application.web.user;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag( name = "Usuário API", description = "Operações relacionadas ao gerenciamento de usuários." )
@RequestMapping( value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE )
public interface UserApi
{
    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Realiza o cadastro de um novo usuário com os dados fornecidos."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado com sucesso.",
                    content = @Content( schema = @Schema( implementation = UserResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para o cadastro.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao cadastrar o usuário.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) )
    })
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    UserResponseDTO create( @RequestBody @Valid UserRequestDTO body );
    
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados no sistema."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários recuperada com sucesso.",
                    content = @Content( schema = @Schema( implementation = List.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao buscar a lista de usuários.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) )
    } )
    @GetMapping
    @ResponseStatus( HttpStatus.OK )
    Collection<UserResponseDTO> findAll();
    
}
