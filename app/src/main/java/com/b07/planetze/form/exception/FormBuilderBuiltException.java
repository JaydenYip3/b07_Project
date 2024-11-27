package com.b07.planetze.form.exception;

import com.b07.planetze.form.definition.FormBuilder;

/**
 * Thrown when using a {@link FormBuilder}
 * after {@link FormBuilder#build()} has been called.
 */
public class FormBuilderBuiltException extends RuntimeException {
    public FormBuilderBuiltException() {
        super("cannot use builder once built");
    }
}
