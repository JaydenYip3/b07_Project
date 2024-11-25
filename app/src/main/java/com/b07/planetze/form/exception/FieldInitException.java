package com.b07.planetze.form.exception;

import com.b07.planetze.form.definition.Field;

/**
 * Thrown when a {@link Field} is improperly
 * initialized.
 */
public class FieldInitException extends RuntimeException {
  public FieldInitException(String message) {
    super(message);
  }
}
