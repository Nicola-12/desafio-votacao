package com.challenge.votation.infra.repositories;

import com.challenge.votation.application.dto.request.UserRequestDTO;
import com.challenge.votation.domain.mappers.UserMapper;
import com.challenge.votation.domain.model.User;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest
{
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    EntityManager entityManager;
    
    @Test
    @DisplayName( "Should return true when exists a user with the given cpf" )
    void findByCpfSuccess() {
        String cpf = "99999999999";
        
        UserRequestDTO userRequestDTO = new UserRequestDTO( "Nícolas", cpf );
        
        createUser( userRequestDTO );
        
        Optional<User> user = userRepository.findByCpf( cpf );
        
        Assertions.assertThat( user.isPresent() ).isTrue();
    }
    
    @Test
    @DisplayName( "Should return false when there is no user with the given cpf" )
    void findByCpfFail() {
        String cpf = "99999999999";
        
        Optional<User> user = userRepository.findByCpf( cpf );
        
        Assertions.assertThat( user.isEmpty() ).isTrue();
    }
    
    private void createUser( UserRequestDTO userRequestDTO )
    {
        User newUser = UserMapper.INSTANCE.dtoToEntity( userRequestDTO );
        entityManager.persist( newUser );
    }
}