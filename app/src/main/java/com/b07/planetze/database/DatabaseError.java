package com.b07.planetze.database;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * An error that occurred while calling a {@link Database}.
 */
public interface DatabaseError {
    @NonNull
    String message();
}
