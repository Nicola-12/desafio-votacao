package com.challenge.votation.domain.handlers;

import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.domain.exceptions.ActiveSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ActiveSessionExceptionHandler {
    @ExceptionHandler( ActiveSessionException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ErrorResponseDTO handle( ActiveSessionException exception ) {
        return new ErrorResponseDTO( exception.getMessage() );
    }
}
