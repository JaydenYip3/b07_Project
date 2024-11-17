package com.b07.planetze.common;

import androidx.annotation.NonNull;

public class InvalidDateIntervalException extends RuntimeException {
    public InvalidDateIntervalException(@NonNull String message) {
        super(message);
    }
}
