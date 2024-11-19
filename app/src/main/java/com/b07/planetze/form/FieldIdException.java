package com.b07.planetze.form;

/**
 * Thrown when passing a {@link FieldId} into a non-associated {@link Form}
 * or {@link FormSubmission}.
 */
public class FieldIdException extends RuntimeException {
    public FieldIdException() {
        super("field id must be associated with form");
    }
}
