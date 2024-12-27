package com.nttdata.client.model.exception;
/**
 * Exception thrown when a client is not found.
 */
public class ClientNotFoundException extends RuntimeException {
    /**
     * Constructs a new ClientNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ClientNotFoundException(String message) {
        super(message);
    }
}
