package com.javaabuser.restapi.exceptions.measurement;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
