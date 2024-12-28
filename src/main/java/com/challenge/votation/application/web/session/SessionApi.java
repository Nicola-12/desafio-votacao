package com.challenge.votation.application.web.session;

import com.challenge.votation.application.dto.request.SessionRequestDTO;
import com.challenge.votation.application.dto.response.SessionResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping( value = "/api/v1/session", produces = MediaType.APPLICATION_JSON_VALUE )
public interface SessionApi
{
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    SessionResponseDTO create( @RequestBody @Valid SessionRequestDTO body );
    
    @GetMapping
    List<SessionResponseDTO> findAll();
}
