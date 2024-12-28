package com.challenge.votation.domain.handlers;

import com.challenge.votation.application.dto.response.ErrorResponseDTO;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MethodArgumentNotValidHandler {
    
    @ExceptionHandler( MethodArgumentNotValidException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponseDTO handle( MethodArgumentNotValidException exception ) {
        List<String> errors =
                exception.getAllErrors().stream().map( DefaultMessageSourceResolvable::getDefaultMessage).toList();
        
        return new ErrorResponseDTO( String.join( ", ", errors ) );
    }
}
