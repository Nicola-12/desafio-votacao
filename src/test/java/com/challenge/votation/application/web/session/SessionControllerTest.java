package com.challenge.votation.application.web.session;

import com.challenge.votation.application.dto.request.SessionRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import com.challenge.votation.application.dto.response.SessionResponseDTO;
import com.challenge.votation.domain.exceptions.ActiveSessionException;
import com.challenge.votation.domain.exceptions.NotFoundException;
import com.challenge.votation.domain.services.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest( SessionController.class )
class SessionControllerTest
{
    private static final String SESSION_URI = "/api/v1/session";
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp()
    {
        objectMapper = new ObjectMapper().registerModule( new JavaTimeModule() ).configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
        
        Mockito.reset( sessionService );
    }
    
    @Test
    void testCreateSession_Success() throws Exception
    {
        LocalDateTime fixedStart = LocalDateTime.of( 2025, 1, 1, 10, 0 );
        
        SessionRequestDTO request = new SessionRequestDTO( "Pauta", "descrição pauta", 4, 1L );
        SessionResponseDTO response = new SessionResponseDTO( 1L, "Pauta", "descrição pauta", 4, fixedStart, new AgendaResponseDTO( 1L, "Agenda 1", "descrição agenda" ) );
        
        when( sessionService.create( any( SessionRequestDTO.class ) ) ).thenReturn( response );
        
        mockMvc.perform( post( SESSION_URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( request ) ) )
                .andExpect( status().isCreated() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.id" ).value( 1L ) )
                .andExpect( jsonPath( "$.agenda.id" ).value( 1L ) )
                .andExpect( jsonPath( "$.agenda.name" ).value( "Agenda 1" ) );
    }
    
    @Test
    void testCreateSession_NotFoundException() throws Exception
    {
        SessionRequestDTO request = new SessionRequestDTO( "Pauta", "descrição pauta", 2, 1L );
        
        when( sessionService.create( any( SessionRequestDTO.class ) ) ).thenThrow( new NotFoundException( "Pauta não foi encontrada" ) );
        
        mockMvc.perform( post( SESSION_URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( request ) ) )
                .andExpect( status().isNotFound() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.message" ).value( "Pauta não foi encontrada" ) );
    }
    
    @Test
    void testCreateSession_ActiveSessionException() throws Exception
    {
        SessionRequestDTO request = new SessionRequestDTO( "Pauta", "descrição pauta", 2, 1L );
        
        when( sessionService.create( any( SessionRequestDTO.class ) ) ).thenThrow( new ActiveSessionException( "Já existe uma sessão ativa para esta pauta" ) );
        
        mockMvc.perform( post( SESSION_URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( request ) ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.message" ).value( "Já existe uma sessão ativa para esta pauta" ) );
    }
    
    @Test
    void testFindAllSessions_Success() throws Exception
    {
        SessionResponseDTO session1 = new SessionResponseDTO( 1L, "Pauta 1", "descrição pauta 1", 2, LocalDateTime.now().minusMinutes( 15 ), new AgendaResponseDTO( 1L, "Agenda 1", "Agenda description 1" ) );
        SessionResponseDTO session2 = new SessionResponseDTO( 2L, "Pauta 2", "descrição pauta 2", 6, LocalDateTime.now().minusHours( 15 ), new AgendaResponseDTO( 2L, "Agenda 2", "Agenda description 2" ) );
        
        List<SessionResponseDTO> sessions = Arrays.asList( session1, session2 );
        
        when( sessionService.findAll() ).thenReturn( sessions );
        
        mockMvc.perform( get( SESSION_URI )
                                 .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.length()" ).value( 2 ) )
                .andExpect( jsonPath( "$[0].id" ).value( 1L ) )
                .andExpect( jsonPath( "$[1].id" ).value( 2L ) );
    }
    
    @Test
    void testFindAllSessions_EmptyList() throws Exception
    {
        when( sessionService.findAll() ).thenReturn( Collections.emptyList() );
        
        mockMvc.perform( get( SESSION_URI )
                                 .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.length()" ).value( 0 ) );
    }
}