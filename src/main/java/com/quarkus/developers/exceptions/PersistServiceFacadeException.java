package com.quarkus.developers.exceptions;

public class PersistServiceFacadeException extends RuntimeException {
    public PersistServiceFacadeException(String message, Throwable cause) {
        super(message, cause);
    }
}
