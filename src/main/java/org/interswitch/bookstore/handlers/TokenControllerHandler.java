package org.interswitch.bookstore.handlers;


import lombok.RequiredArgsConstructor;
import org.interswitch.bookstore.enums.Role;
import org.interswitch.bookstore.exception.GenericException;
import org.interswitch.bookstore.exception.TokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class TokenControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleRefreshTokenException(TokenException ex, WebRequest request){
       final ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .error("Invalid Token")
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = GenericException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleGenericException(GenericException ex){
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .error(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .error(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
