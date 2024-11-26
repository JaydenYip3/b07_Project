package com.b07.planetze.database.firebase;

import androidx.annotation.NonNull;

import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.util.option.Option;

import java.util.Objects;

/**
 * Wraps a firebase exception / error message.
 */
public final class FirebaseError implements DatabaseError {
    @NonNull private final String message;
    public FirebaseError(@NonNull Exception exception) {
        message = Option.mapNull(exception.getMessage())
                .getOr("(firebase error without message)");
    }

    public FirebaseError(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    public String message() {
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("FirebaseError[message=%s]", message);
    }
}
