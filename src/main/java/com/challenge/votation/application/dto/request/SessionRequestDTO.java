package com.challenge.votation.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SessionRequestDTO(
        @NotNull( message = "Necessário informar um nome para a sessão" ) String name,
        String description,
        @Min( value = 1, message = "Duração deve ser maior 1 minutos" ) Integer duration,
        @NotNull( message = "Necessário informar para qual pauta está sessão está sendo vinculada" ) Long agendaId ) {}
