package com.example.githubapiconsumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.example.githubapiconsumer.exception.UserNotFoundException;
import com.example.githubapiconsumer.response.ErrorResponse;

@ControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {

        ErrorResponse errorData = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {

        log.error("Unexpected error!", e);

        ErrorResponse errorData = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred!");

        return new ResponseEntity<>(errorData, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler({ WebClientResponseException.class, ResponseStatusException.class })
    public ResponseEntity<ErrorResponse> handleWebClientResponseException(WebClientResponseException e) {

        log.error("Error with known status code!", e);

        ErrorResponse errorData = new ErrorResponse(e.getStatusCode().value(), e.getMessage());

        return new ResponseEntity<>(errorData, e.getStatusCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("No url found for request", e);
        ErrorResponse errorData = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "URL not found");
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

}
