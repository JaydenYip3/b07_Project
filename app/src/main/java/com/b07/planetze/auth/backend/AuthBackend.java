package com.b07.planetze.auth.backend;

import androidx.annotation.NonNull;

import com.b07.planetze.auth.AuthUser;
import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.RegisterError;
import com.b07.planetze.auth.backend.error.ResetPasswordError;
import com.b07.planetze.auth.backend.error.SendEmailVerificationError;
import com.b07.planetze.auth.backend.error.SendPasswordResetError;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;

public interface AuthBackend {
    void login(
            @NonNull String email,
            @NonNull String password,
            @NonNull Consumer<Result<AuthUser, LoginError>> callback
    );

    void register(
            @NonNull String email,
            @NonNull String password,
            @NonNull String username,
            @NonNull Consumer<Result<AuthUser, RegisterError>> callback
    );

    void sendEmailVerification(
            @NonNull Consumer<Result<Unit, SendEmailVerificationError>> callback
    );

    void sendPasswordResetEmail(
            @NonNull String email,
            @NonNull Consumer<Result<Unit, SendPasswordResetError>> callback
    );
}
