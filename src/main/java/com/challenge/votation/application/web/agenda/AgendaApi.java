package com.challenge.votation.application.web.agenda;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import com.challenge.votation.application.dto.response.ErrorResponseDTO;
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

@Tag( name = "Pautas API", description = "Responsável por controlar o ciclo de vida das pautas em assembleias. " +
                                         "Inclui funcionalidades para criação, consulta, obtenção de resultados e " +
                                         "listagem de todas as pautas registradas." )
@RequestMapping( value = "/api/v1/agenda", produces = MediaType.APPLICATION_JSON_VALUE )
public interface AgendaApi
{
    @Operation(
            summary = "Criar nova pauta",
            description = "Cria uma nova pauta associada a um usuário fornecendo os dados necessários."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Pauta criada com sucesso.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema( implementation = AgendaResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a criação da pauta.",
                    content = @Content(schema = @Schema( implementation = ErrorResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor.",
                    content = @Content)
    })
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    AgendaResponseDTO create( @RequestBody @Valid AgendaRequestDTO body );
    
    @Operation(
            summary = "Obter todas as pautas",
            description = "Recupera a lista de todas as pautas cadastradas no sistema."
    )
    @ApiResponses(
            value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pautas recuperada com sucesso.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema( implementation = List.class ) ) ),
    })
    @GetMapping
    List<AgendaResponseDTO> findAll();
}
