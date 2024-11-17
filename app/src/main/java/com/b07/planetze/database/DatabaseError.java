package com.b07.planetze.database;

import androidx.annotation.NonNull;

/**
 * An error that occurred while calling a {@link Database}.
 */
public class DatabaseError {
    private String message;

    private DatabaseError() {}

    public DatabaseError(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    public String message() {
        return this.message;
    }
}
