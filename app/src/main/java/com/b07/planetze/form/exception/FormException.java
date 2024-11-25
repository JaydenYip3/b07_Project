package com.b07.planetze.form.exception;

/**
 * Thrown when a form fragment is in unrecoverable invalid state.
 */
public class FormException extends RuntimeException {
    public FormException(String message) {
        super(message);
    }
}
