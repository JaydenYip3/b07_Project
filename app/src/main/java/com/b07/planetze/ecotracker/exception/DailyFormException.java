package com.b07.planetze.ecotracker.exception;

/**
 * Thrown when a {@link com.b07.planetze.ecotracker.daily.DailyForm} failed to
 * create a {@link com.b07.planetze.form.Form}. <br>
 * This indicates an error in the form's definition.
 */
public class DailyFormException extends RuntimeException {
    public DailyFormException() {
        super();
    }
}
