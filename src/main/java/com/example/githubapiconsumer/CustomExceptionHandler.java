package com.example.githubapiconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice 
public class CustomExceptionHandler { 

    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class) 
    public ResponseEntity<ErrorData> handleUserNotFoundException(UserNotFoundException e) {
   
        ErrorData errorData = new ErrorData(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorData> handleGenericException(Exception e) {

        log.error("Unexpected error!", e);

        ErrorData errorData = new ErrorData(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred!");

        return new ResponseEntity<>(errorData, HttpStatus.INTERNAL_SERVER_ERROR); 

    }

    @ExceptionHandler({WebClientResponseException.class, ResponseStatusException.class})
    public ResponseEntity<ErrorData> handleWebClientResponseException(WebClientResponseException e) {

        log.error("Error with known status code!", e);

        ErrorData errorData = new ErrorData(e.getStatusCode().value(), e.getMessage());

        return new ResponseEntity<>(errorData, e.getStatusCode()); 
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorData> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("No url found for request", e);
        ErrorData errorData = new ErrorData(HttpStatus.NOT_FOUND.value(), "URL not found");
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }
    

}
