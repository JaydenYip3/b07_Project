package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public final class EmptyFieldsError implements LoginError, RegisterError {
    @NonNull
    public String message() {
        return "All fields are required";
    }
}
