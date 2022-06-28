package com.javaabuser.restapi.exceptions.sensor;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
