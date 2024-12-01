package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public final class EmptyEmailError
        implements SendPasswordResetError {
    @NonNull
    public String message() {
        return "Enter an email";
    }
}
