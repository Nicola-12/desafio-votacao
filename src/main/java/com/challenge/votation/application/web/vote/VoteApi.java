package com.challenge.votation.application.web.vote;

import com.challenge.votation.application.dto.request.VoteRequestDTO;
import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.application.dto.response.VoteResponseDTO;
import com.challenge.votation.application.dto.response.VoteResultResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag( name = "Votação API", description = "Gerenciamento das operações de votação em pautas." )
@RequestMapping( "/api/v1/vote" )
public interface VoteApi
{
    @Operation(
            summary = "Registrar um voto",
            description = "Permite que um usuário registre seu voto em uma pauta específica."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Voto registrado com sucesso.",
                    content = @Content( schema = @Schema( implementation = VoteResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou pauta não encontrados",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário já votou nessa pauta",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao registrar o voto.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) )
    })
    @PostMapping
    VoteResponseDTO vote( @RequestBody @Valid VoteRequestDTO body );
    
    @Operation(
            summary = "Listar todos os votos",
            description = "Recupera todos os votos registrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de votos recuperada com sucesso.",
                    content = @Content( schema = @Schema( implementation = List.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao buscar os votos.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) )
    })
    @GetMapping
    List<VoteResponseDTO> findAll();
    
    @Operation(
            summary = "Resultado da votação por pauta",
            description = "Obtém o resultado da votação de uma pauta específica com base no ID da pauta."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resultado da votação obtido com sucesso.",
                    content = @Content( schema = @Schema( implementation = VoteResultResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pauta não encontrada ou não existem votos para essa pauta",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao buscar o resultado.",
                    content = @Content)
    })
    @GetMapping( "/result/{agendaId}" )
    VoteResultResponseDTO result( @PathVariable Long agendaId );
}
