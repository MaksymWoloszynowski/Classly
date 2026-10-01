package org.classly.authservice.exception;

public class AccessCodeNotFoundException extends RuntimeException{
    public AccessCodeNotFoundException(String message) { super(message); }
}
