package com.b07.planetze.form;

/**
 * Thrown when attempting to add fields to a {@link FormBuilder} after
 * calling {@link FormBuilder#build()}.
 */
public class FormBuilderBuiltException extends RuntimeException {
    public FormBuilderBuiltException() {
        super("cannot add fields once built");
    }
}
