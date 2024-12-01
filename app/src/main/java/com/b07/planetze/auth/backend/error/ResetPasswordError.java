package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public sealed interface ResetPasswordError extends AuthError
        permits OtherAuthError, ResetPasswordError.InvalidCode,
                EmptyFieldsError {
    record InvalidCode(@NonNull String message) implements ResetPasswordError {}
}
