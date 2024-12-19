package com.challenge.votation.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record AgendaRequestDTO(
        @NotNull( message = "Necessário informar o nome da pauta" ) String name,
        String description ) {}
