package com.challenge.votation.domain.exceptions;

public class UserRegisterException extends RuntimeException {
    
    public UserRegisterException( String message ) {
        super( message );
    }
}
