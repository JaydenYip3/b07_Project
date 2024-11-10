package com.b07.planetze.auth;

import androidx.annotation.NonNull;

public interface ResetPasswordCallback {
    void resetPassword(@NonNull String code, @NonNull String newPassword, @NonNull String confirmPassword);
}
