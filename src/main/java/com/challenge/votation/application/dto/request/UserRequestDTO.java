package com.challenge.votation.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserRequestDTO( String name,
                              @NotNull( message = "Necessário informar um cpf para o usuário" ) String cpf ) {}
