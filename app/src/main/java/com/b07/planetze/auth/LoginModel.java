package com.b07.planetze.auth;

import androidx.annotation.NonNull;

import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.SendPasswordResetError;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;

public interface LoginModel {
    void login(
            @NonNull String email,
            @NonNull String password,
            @NonNull Consumer<Result<AuthUser, LoginError>> callback
    );

    void sendPasswordResetEmail(
            @NonNull String email,
            @NonNull Consumer<Result<Unit, SendPasswordResetError>> callback
    );
}
