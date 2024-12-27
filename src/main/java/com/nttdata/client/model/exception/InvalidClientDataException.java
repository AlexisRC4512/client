package com.nttdata.client.model.exception;

/**
 * Exception thrown when client data is invalid.
 */
public class InvalidClientDataException extends RuntimeException {
    /**
     * Constructs a new InvalidClientDataException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidClientDataException(String message) {
        super(message);
    }
}