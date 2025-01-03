package com.challenge.votation.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record VoteRequestDTO( @NotNull( message = "Necessário apresentar o id do usuário que irá votar" ) Long userId,
                              @NotNull( message = "Necessário informar para qual pauta sera o voto" ) Long agendaId,
                              @NotNull( message = "Necessário informar qual foi o voto" ) Boolean vote ) {}
