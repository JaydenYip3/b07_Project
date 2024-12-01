package com.b07.planetze.auth;

import androidx.annotation.NonNull;

public record AuthUser(
        @NonNull String name,
        @NonNull String email,
        boolean emailVerified
) {}
