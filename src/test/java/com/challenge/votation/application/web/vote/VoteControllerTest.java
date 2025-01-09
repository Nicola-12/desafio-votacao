package com.challenge.votation.application.web.vote;

import com.challenge.votation.application.dto.request.VoteRequestDTO;
import com.challenge.votation.application.dto.response.AgendaResponseDTO;
import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
import com.challenge.votation.application.dto.response.VoteResponseDTO;
import com.challenge.votation.application.dto.response.VoteResultResponseDTO;
import com.challenge.votation.domain.enums.VoteStatus;
import com.challenge.votation.domain.exceptions.NotFoundException;
import com.challenge.votation.domain.exceptions.SessionExpiredException;
import com.challenge.votation.domain.exceptions.UserAlreadyVotedException;
import com.challenge.votation.domain.services.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( VoteController.class )
public class VoteControllerTest
{
    private static final String URI = "/api/v1/vote";
    
    @MockitoBean
    @Autowired
    private VoteService voteService;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName( "Tests successful vote" )
    public void testVote_Success() throws Exception
    {
        VoteRequestDTO voteRequestDTO = new VoteRequestDTO( 1L, 1L, true );
        VoteResponseDTO voteResponseDTO = new VoteResponseDTO( 1L, String.valueOf( true ), new UserResponseDTO( 1L, "user", "99999999999" ) );
        
        when( voteService.vote( any( VoteRequestDTO.class ) ) ).thenReturn( voteResponseDTO );
        
        mockMvc.perform( post( URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( voteRequestDTO ) ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( voteResponseDTO ) ) );
        
        verify( voteService, times( 1 ) ).vote( any( VoteRequestDTO.class ) );
    }
    
    @Test
    @DisplayName( "Tests vote when user is not found" )
    public void testVote_UserNotFound() throws Exception
    {
        VoteRequestDTO voteRequestDTO = new VoteRequestDTO( 1L, 1L, true );
        
        when( voteService.vote( any( VoteRequestDTO.class ) ) )
                .thenThrow( new NotFoundException( "Usuário não foi encontrado" ) );
        
        mockMvc.perform( post( URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( voteRequestDTO ) ) )
                .andExpect( status().isNotFound() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( new ErrorResponseDTO( "Usuário não foi encontrado" ) ) ) );
        
        verify( voteService, times( 1 ) ).vote( any( VoteRequestDTO.class ) );
    }
    
    @Test
    @DisplayName( "Tests vote with expired session" )
    public void testVote_SessionExpired() throws Exception
    {
        VoteRequestDTO voteRequestDTO = new VoteRequestDTO( 1L, 1L, true );
        
        when( voteService.vote( any( VoteRequestDTO.class ) ) ).thenThrow( new SessionExpiredException() );
        
        mockMvc.perform( post( URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( voteRequestDTO ) ) )
                .andExpect( status().isGone() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( new ErrorResponseDTO( "Sessão expirada" ) ) ) );
        
        verify( voteService, times( 1 ) ).vote( any( VoteRequestDTO.class ) );
    }
    
    @Test
    @DisplayName( "Tests when user has already voted" )
    public void testVote_UserAlreadyVoted() throws Exception
    {
        VoteRequestDTO voteRequestDTO = new VoteRequestDTO( 1L, 1L, true );
        
        when( voteService.vote( any( VoteRequestDTO.class ) ) )
                .thenThrow( new UserAlreadyVotedException( "Usuário já votou nessa pauta" ) );
        
        mockMvc.perform( post( URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( voteRequestDTO ) ) )
                .andExpect( status().isGone() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( new ErrorResponseDTO( "Usuário já votou nessa pauta" ) ) ) );
        
        verify( voteService, times( 1 ) ).vote( any( VoteRequestDTO.class ) );
    }
    
    @Test
    @DisplayName( "Tests successful vote result" )
    public void testResult_Success() throws Exception
    {
        Long agendaId = 1L;
        
        VoteResultResponseDTO voteResultResponseDTO = new VoteResultResponseDTO(
                VoteStatus.ACCEPTED,
                new AgendaResponseDTO( agendaId, "Agenda 1", "Agenda description" )
        );
        
        when( voteService.result( agendaId ) ).thenReturn( voteResultResponseDTO );
        
        mockMvc.perform( get( URI + "/result/{agendaId}", agendaId )
                                 .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( voteResultResponseDTO ) ) );
        
        verify( voteService, times( 1 ) ).result( agendaId );
    }
    
    @Test
    @DisplayName( "Tests when the agenda is not found in the result" )
    public void testResult_AgendaNotFound() throws Exception
    {
        Long agendaId = 1L;
        
        when( voteService.result( agendaId ) ).thenThrow( new NotFoundException( "Pauta não foi encontrada" ) );
        
        mockMvc.perform( get( URI + "/result/{agendaId}", agendaId )
                                 .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( new ErrorResponseDTO( "Pauta não foi encontrada" ) ) ) );
        
        verify( voteService, times( 1 ) ).result( agendaId );
    }
    
    @Test
    @DisplayName( "Tests when there are no votes for the agenda" )
    public void testResult_NoVotes() throws Exception
    {
        Long agendaId = 1L;
        
        when( voteService.result( agendaId ) ).thenThrow( new NotFoundException( "Nenhum voto encontrado para a pauta" ) );
        
        mockMvc.perform( get( URI + "/result/{agendaId}", agendaId )
                                 .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( new ErrorResponseDTO( "Nenhum voto encontrado para a pauta" ) ) ) );
        
        verify( voteService, times( 1 ) ).result( agendaId );
    }
}
