package com.javaabuser.restapi.exceptions;

public class NotDeletedException extends RuntimeException{
    public NotDeletedException(String message) {
        super(message);
    }
}
