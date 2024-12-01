package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public record SendPasswordResetError(@NonNull String message)
        implements AuthError {}
