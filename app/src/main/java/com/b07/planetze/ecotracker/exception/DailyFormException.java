package com.b07.planetze.ecotracker.exception;

import com.b07.planetze.common.daily.DailyForm;

/**
 * Thrown when a {@link DailyForm} failed to
 * create a {@link com.b07.planetze.form.Form}. <br>
 * This indicates an error in the form's definition.
 */
public class DailyFormException extends RuntimeException {
    public DailyFormException() {
        super();
    }
}
