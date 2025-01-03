package com.challenge.votation.application.dto.response;

import com.challenge.votation.domain.enums.VoteStatus;

public record VoteResultResponseDTO( VoteStatus status,
                                     AgendaResponseDTO agenda ) {}
