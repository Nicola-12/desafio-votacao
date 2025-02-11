package com.challenge.votation.domain.handlers;

import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.domain.exceptions.SessionExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionExpiredExceptionHandler
{
    @ExceptionHandler( SessionExpiredException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.GONE )
    public ErrorResponseDTO handle( SessionExpiredException exception )
    {
        return new ErrorResponseDTO( exception.getMessage() );
    }
}
