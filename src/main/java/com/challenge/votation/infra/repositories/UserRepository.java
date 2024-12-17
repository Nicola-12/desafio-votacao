package com.challenge.votation.infra.repositories;

import com.challenge.votation.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
