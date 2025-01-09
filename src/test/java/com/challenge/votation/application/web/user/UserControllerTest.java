package com.challenge.votation.application.web.user;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.application.dto.response.UserResponseDTO;
import com.challenge.votation.domain.exceptions.UserRegisterException;
import com.challenge.votation.domain.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
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

@WebMvcTest( UserController.class )
public class UserControllerTest
{
    private static final String URI = "/api/v1/user";
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    @Autowired
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testCreateUser() throws Exception
    {
        UserRequestDTO userRequestDTO = new UserRequestDTO( "Nícolas", "12345678901" );
        
        UserResponseDTO userResponseDTO = new UserResponseDTO( 1L, "Nícolas", "12345678901" );
        
        Mockito.when( userService.create( any( UserRequestDTO.class ) ) ).thenReturn( userResponseDTO );
        
        mockMvc.perform( post( URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( userRequestDTO ) ) )
                .andExpect( status().isCreated() )
                .andExpect( content().json( objectMapper.writeValueAsString( userResponseDTO ) ) );
    }
    
    @Test
    public void testCreateUser_Fail_UserAlreadyExists() throws Exception
    {
        UserRequestDTO userRequestDTO = new UserRequestDTO( "Nícolas", "12345678901" );
        
        when( userService.create( any( UserRequestDTO.class ) ) ).thenThrow( new UserRegisterException( "Usuário já está cadastrado" ) );
        
        mockMvc.perform( post( URI )
                                 .contentType( MediaType.APPLICATION_JSON )
                                 .content( objectMapper.writeValueAsString( userRequestDTO ) ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content().json( objectMapper.writeValueAsString( new ErrorResponseDTO( "Usuário já está cadastrado" ) ) ) );
    }
    
    @Test
    public void testFindAllUsers() throws Exception
    {
        UserResponseDTO user1 = new UserResponseDTO( 1L, "Nícolas 1", "12345678901" );
        UserResponseDTO user2 = new UserResponseDTO( 2L, "Nícolas 2", "01010101010" );
        
        List<UserResponseDTO> users = Arrays.asList( user1, user2 );
        
        when( userService.findAll() ).thenReturn( users );
        
        mockMvc.perform( get( URI ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.length()" ).value( 2 ) )
                .andExpect( content().json( objectMapper.writeValueAsString( users ) ) );
    }
}
