package com.b07.planetze.auth;

import androidx.annotation.NonNull;

public interface RegisterCallback {
    void register(@NonNull String email, @NonNull String password, @NonNull String confirmPassword, @NonNull String username);

    void resendConfirmationEmail();

}
