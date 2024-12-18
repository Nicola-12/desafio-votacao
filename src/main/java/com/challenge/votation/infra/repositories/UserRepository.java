package com.challenge.votation.infra.repositories;

import com.challenge.votation.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCpf(String cpf);
}
