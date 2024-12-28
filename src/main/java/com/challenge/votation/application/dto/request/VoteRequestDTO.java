package com.challenge.votation.application.dto.request;

public record VoteRequestDTO( Long userId,
                              Long agendaId,
                              Boolean vote ) {}
