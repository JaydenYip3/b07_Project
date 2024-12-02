package com.b07.planetze.auth.backend.error;

public sealed interface SendPasswordResetError extends AuthError
        permits OtherAuthError, EmptyEmailError, MalformedEmailError {}
