package com.challenge.votation.domain.handlers;

import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RuntimeExceptionHandler {
    
    @ExceptionHandler( RuntimeException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponseDTO handle( RuntimeException exception ) {
        return new ErrorResponseDTO( exception.getMessage() );
    }
}
