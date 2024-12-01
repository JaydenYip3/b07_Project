package com.b07.planetze.auth.backend.error;

public sealed interface SendEmailVerificationError extends AuthError
        permits OtherAuthError {}
