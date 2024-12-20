package com.nttdata.client.model.exception;

import com.nttdata.client.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Client Not Found");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidClientDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidClientDataException(InvalidClientDataException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Invalid Client Data");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Internal Server Error");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
