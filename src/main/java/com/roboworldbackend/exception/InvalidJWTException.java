package com.roboworldbackend.exception;

/**
 * Invalid JWT Exception class
 *
 * @author Blajan George
 */
public class InvalidJWTException extends RuntimeException {
    public InvalidJWTException(String message) {
        super(message);
    }
}
