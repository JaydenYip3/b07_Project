package com.b07.planetze.form.exception;

import com.b07.planetze.form.FormFactoryBuilder;

/**
 * Thrown when using a {@link com.b07.planetze.form.FormFactoryBuilder}
 * after {@link FormFactoryBuilder#build()} has been called.
 */
public class FormBuilderBuiltException extends RuntimeException {
    public FormBuilderBuiltException() {
        super("cannot use builder once built");
    }
}
