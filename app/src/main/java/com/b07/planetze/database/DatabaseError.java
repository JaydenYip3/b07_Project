package com.b07.planetze.database;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * An error that occurred while calling a {@link Database} function.
 */
public interface DatabaseError {
    /**
     * {@return the message associated with this error}
     */
    @NonNull
    String message();
}
