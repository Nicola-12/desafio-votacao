package com.challenge.votation.application.dto.response;

public record VoteResponseDTO( Long id,
                               String vote,
                               UserResponseDTO user ) {}
