package com.femcoders.authentication.exceptions;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }
}
