package com.challenge.votation.application.web.vote;

import com.challenge.votation.application.dto.request.VoteRequestDTO;
import com.challenge.votation.application.dto.response.VoteResponseDTO;
import com.challenge.votation.application.dto.response.VoteResultResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping( "/api/v1/vote" )
public interface VoteApi
{
    @PostMapping
    VoteResponseDTO vote( @RequestBody @Valid VoteRequestDTO body );
    
    @GetMapping
    List<VoteResponseDTO> findAll();
    
    @GetMapping( "/result/{agendaId}" )
    VoteResultResponseDTO result( @PathVariable Long agendaId );
}
