package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public sealed interface LoginError extends AuthError
        permits EmptyFieldsError, MalformedEmailError, OtherAuthError {
}
