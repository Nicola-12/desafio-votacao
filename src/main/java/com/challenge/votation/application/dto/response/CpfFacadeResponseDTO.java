package com.challenge.votation.application.dto.response;

public record CpfFacadeResponseDTO( Status status )
{
    public enum Status
    {
        ABLE_TO_VOTE,
        UNABLE_TO_VOTE
    }
}
