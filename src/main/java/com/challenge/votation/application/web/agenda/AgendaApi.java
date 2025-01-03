package com.challenge.votation.application.web.agenda;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping( value = "/api/v1/agenda", produces = MediaType.APPLICATION_JSON_VALUE )
public interface AgendaApi
{
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    AgendaResponseDTO create( @RequestBody @Valid AgendaRequestDTO body );
    
    @GetMapping
    List<AgendaResponseDTO> findAll();
}
