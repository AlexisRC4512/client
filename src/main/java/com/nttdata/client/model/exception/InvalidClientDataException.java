package com.nttdata.client.model.exception;

public class InvalidClientDataException extends RuntimeException {
    public InvalidClientDataException(String message) {
        super(message);
    }
}