package com.challenge.votation.application.web.facade;

import com.challenge.votation.application.dto.response.CpfFacadeResponseDTO;
import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag( name = "CPF API", description = "Facade que randomicamente irá retornar se o cpf é valido ou não para votação" )
@RequestMapping( "/api/v1/cpf-validator" )
public interface CpfFacadeApi
{
    @Operation(
            summary = "Validar CPF",
            description = "Valida um CPF randomicamente."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "CPF validado com sucesso.",
                    content = @Content( schema = @Schema( implementation = CpfFacadeResponseDTO.class ) ) ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CPF inválido.",
                    content = @Content( schema = @Schema( implementation = ErrorResponseDTO.class ) ) ),
    } )
    @GetMapping( "/{cpf}" )
    @ResponseStatus( HttpStatus.OK )
    CpfFacadeResponseDTO validate( @PathVariable String cpf );
}
