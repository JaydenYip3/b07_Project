package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public sealed interface RegisterError extends AuthError
        permits OtherAuthError, EmptyFieldsError, MalformedEmailError,
                RegisterError.UserCollision {
    record UserCollision(@NonNull String message)
            implements RegisterError {}
}
