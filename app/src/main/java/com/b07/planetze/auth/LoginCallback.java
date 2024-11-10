package com.b07.planetze.auth;

import androidx.annotation.NonNull;

public interface LoginCallback {
    void login(@NonNull String email, @NonNull String password);

    void toSendReset();
}
