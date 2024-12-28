package com.challenge.votation.domain.exceptions;

public class ActiveSessionException extends RuntimeException {
    
    public ActiveSessionException( String message ) {
        super( message );
    }
}
