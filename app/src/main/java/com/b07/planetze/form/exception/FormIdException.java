package com.b07.planetze.form.exception;

import com.b07.planetze.form.FieldId;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormSubmission;

/**
 * Thrown when passing a {@link FieldId} into a non-associated {@link Form}
 * or {@link FormSubmission}.
 */
public class FormIdException extends RuntimeException {
    public FormIdException() {
        super("field id must be associated with form");
    }
}
