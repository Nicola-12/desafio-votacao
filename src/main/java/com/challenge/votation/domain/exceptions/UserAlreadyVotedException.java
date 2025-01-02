package com.challenge.votation.domain.exceptions;

public class UserAlreadyVotedException extends RuntimeException {
    
    public UserAlreadyVotedException( String message ) {
        super( message );
    }
}
