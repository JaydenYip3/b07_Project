package com.b07.planetze.database.firebase;

import androidx.annotation.NonNull;

import com.b07.planetze.database.DatabaseError;

import java.util.Objects;

/**
 * Wraps a firebase exception.
 * @param exception the exception
 */
public record FirebaseError(@NonNull Exception exception)
        implements DatabaseError {
    @NonNull
    public String message() {
        return Objects.requireNonNullElse(exception.getMessage(), "(firebase error)");
    }
}
