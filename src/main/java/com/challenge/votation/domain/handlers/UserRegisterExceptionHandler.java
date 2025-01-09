package com.challenge.votation.domain.handlers;

import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import com.challenge.votation.domain.exceptions.UserRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserRegisterExceptionHandler
{
    @ExceptionHandler( UserRegisterException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ErrorResponseDTO handle( UserRegisterException exception )
    {
        return new ErrorResponseDTO( exception.getMessage() );
    }
}
