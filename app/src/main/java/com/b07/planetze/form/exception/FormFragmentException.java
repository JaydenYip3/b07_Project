package com.b07.planetze.form.exception;

/**
 * Thrown when a form fragment is in unrecoverable invalid state.
 */
public class FormFragmentException extends RuntimeException {
    public FormFragmentException(String message) {
        super(message);
    }
}
