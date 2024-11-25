package com.b07.planetze.common;

import androidx.annotation.NonNull;

import com.b07.planetze.util.DateInterval;

/**
 * Is thrown upon improper initialization of a {@link DateInterval}.
 */
public class InvalidDateIntervalException extends RuntimeException {
    public InvalidDateIntervalException(@NonNull String message) {
        super(message);
    }
}
