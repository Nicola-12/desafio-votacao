package com.challenge.votation.application.web.session;

import com.challenge.votation.application.dto.request.SessionRequestDTO;
import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.application.dto.response.SessionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag( name = "Sessões API", description = "Gerenciamento das sessões vinculadas às pautas de assembleias." )
@RequestMapping( value = "/api/v1/session", produces = MediaType.APPLICATION_JSON_VALUE )
public interface SessionApi {
    
    @Operation(
            summary = "Criar nova sessão",
            description = "Cria uma nova sessão vinculada a uma pauta específica."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Sessão criada com sucesso.",
                    content = @Content( schema = @Schema( implementation = SessionResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a criação da sessão.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao criar a sessão.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) )
    } )
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    SessionResponseDTO create( @RequestBody @Valid SessionRequestDTO body );
    
    @Operation(
            summary = "Listar todas as sessões",
            description = "Obtém uma lista de todas as sessões registradas no sistema."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de sessões obtida com sucesso.",
                    content = @Content( schema = @Schema( implementation = List.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao buscar as sessões.",
                    content =  @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) )
    } )
    @GetMapping
    List<SessionResponseDTO> findAll();
}
