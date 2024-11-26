package com.b07.planetze.util;

import androidx.annotation.NonNull;

/**
 * Is thrown upon improper initialization of a {@link DateInterval}.
 */
public class DateIntervalException extends RuntimeException {
    public DateIntervalException(@NonNull String message) {
        super(message);
    }
}
