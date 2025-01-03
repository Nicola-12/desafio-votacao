package com.challenge.votation.domain.handlers;

import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.domain.exceptions.UserAlreadyVotedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserAlreadyVotedExceptionHandler {
    
    @ExceptionHandler( UserAlreadyVotedException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.GONE )
    public ErrorResponseDTO handle( UserAlreadyVotedException exception ) {
        return new ErrorResponseDTO( exception.getMessage() );
    }
}
