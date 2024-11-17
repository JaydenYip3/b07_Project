package com.b07.planetze.database;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * An exception "thrown" by Firebase.
 * @param exception the exception
 */
public record FirebaseError(@NonNull Exception exception) implements DatabaseError {
    @NonNull
    public String message() {
        return Objects.requireNonNullElse(exception.getMessage(), "(firebase error)");
    }
}
