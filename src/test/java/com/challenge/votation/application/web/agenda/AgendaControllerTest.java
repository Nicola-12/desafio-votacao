package com.challenge.votation.application.web.agenda;

import com.challenge.votation.application.dto.request.AgendaRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import com.challenge.votation.domain.services.AgendaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest( AgendaController.class )
class AgendaControllerMockMvcTest
{
    private static final String URI = "/api/v1/agenda";
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    @Autowired
    private AgendaService service;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp()
    {
        Mockito.reset( service );
    }
    
    @Test
    void testCreate() throws Exception
    {
        String agendaName = "New Agenda";
        String agendaDescription = "New Agenda description";
        
        AgendaRequestDTO request = new AgendaRequestDTO( agendaName, agendaDescription );
        AgendaResponseDTO response = new AgendaResponseDTO( 1L, agendaName, agendaDescription );
        
        when( service.create( Mockito.any( AgendaRequestDTO.class ) ) ).thenReturn( response );
        
        mockMvc.perform( post( URI )
                                .contentType( MediaType.APPLICATION_JSON )
                                .content( objectMapper.writeValueAsString( request ) ) )
                .andExpect( status().isCreated() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.id" ).value( 1L ) )
                .andExpect( jsonPath( "$.name" ).value( "New Agenda" ) )
                .andExpect( jsonPath( "$.description" ).value( "New Agenda description" ) );
    }
    
    @Test
    void testFindAll() throws Exception
    {
        AgendaResponseDTO agenda1 = new AgendaResponseDTO( 1L, "Agenda 1", "Description 1" );
        AgendaResponseDTO agenda2 = new AgendaResponseDTO( 2L, "Agenda 2", "Description 2" );
        
        List<AgendaResponseDTO> responseList = Arrays.asList( agenda1, agenda2 );
        
        when( service.findAll() ).thenReturn( responseList );
        
        mockMvc.perform( get( URI ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$[0].id" ).value( 1L ) )
                .andExpect( jsonPath( "$[0].name" ).value( "Agenda 1") )
                .andExpect( jsonPath( "$[0].description" ).value( "Description 1") )
                .andExpect( jsonPath( "$[1].id" ).value( 2L ) )
                .andExpect( jsonPath( "$[1].name" ).value( "Agenda 2" ) )
                .andExpect( jsonPath( "$[1].description" ).value( "Description 2" ) );
    }
}
