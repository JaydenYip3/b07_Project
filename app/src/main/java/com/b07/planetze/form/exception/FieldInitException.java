package com.b07.planetze.form.exception;

/**
 * Thrown when a {@link com.b07.planetze.form.field.Field} is improperly
 * initialized.
 */
public class FieldInitException extends RuntimeException {
  public FieldInitException(String message) {
    super(message);
  }
}
