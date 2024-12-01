package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public record OtherAuthError(@NonNull String message)
        implements LoginError, RegisterError, ResetPasswordError,
                SendEmailVerificationError {}
