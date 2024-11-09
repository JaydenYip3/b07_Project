package com.b07.planetze.login;

import androidx.annotation.NonNull;

public interface LoginCallback {
    void login(@NonNull String email, @NonNull String password);
}
