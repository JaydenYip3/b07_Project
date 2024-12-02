package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public record OtherAuthError(@NonNull String message)
        implements LoginError, RegisterError, SendEmailVerificationError,
        SendPasswordResetError {
    public OtherAuthError(@NonNull String message) {
        this.message = "Error: " + message;
    }
}
