package com.challenge.votation.domain.handlers;

import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundExceptionHandler {
    @ExceptionHandler( NotFoundException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public ErrorResponseDTO handle( NotFoundException exception ) {
        return new ErrorResponseDTO( exception.getMessage() );
    }
}
