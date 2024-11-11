package com.b07.planetze.auth;

/**
 * Represents an error in the initialization of AuthActivity.
 */
public class AuthActivityInitException extends RuntimeException {
    public AuthActivityInitException(String message) {
        super(message);
    }
}
