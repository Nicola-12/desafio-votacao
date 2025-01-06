package com.challenge.votation.domain.exceptions;

public class SessionExpiredException extends RuntimeException
{
    public SessionExpiredException()
    {
        super( "Sessão expirada" );
    }
}
