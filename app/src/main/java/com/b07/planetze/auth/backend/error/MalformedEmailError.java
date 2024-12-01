package com.b07.planetze.auth.backend.error;

import androidx.annotation.NonNull;

public final class MalformedEmailError
        implements LoginError, RegisterError, SendPasswordResetError {
    @NonNull
    public String message() {
        return "Malformed email";
    }
}
