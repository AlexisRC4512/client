package com.nttdata.client.model.exception;

import com.nttdata.client.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * Global exception handler for client-related exceptions.
 */

@RestControllerAdvice
public class ClientExceptionHandler {
    /**
     * Handles ClientNotFoundException.
     *
     * @param ex the exception thrown when a client is not found
     * @return a ResponseEntity containing an ErrorResponse and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Client Not Found");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    /**
     * Handles InvalidClientDataException.
     *
     * @param ex the exception thrown when client data is invalid
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */

    @ExceptionHandler(InvalidClientDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidClientDataException(InvalidClientDataException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Invalid Client Data");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handles generic exceptions.
     *
     * @param ex the exception thrown for any other errors
     * @return a ResponseEntity containing an ErrorResponse and HTTP status INTERNAL_SERVER_ERROR
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Internal Server Error");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
