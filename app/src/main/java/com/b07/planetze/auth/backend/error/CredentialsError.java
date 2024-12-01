package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public final class CredentialsError implements LoginError {
    @NonNull
    public String message() {
        return "Invalid credentials";
    }
}
