package com.b07.planetze.auth.backend;

import androidx.annotation.NonNull;

import com.b07.planetze.auth.AuthUser;
import com.b07.planetze.auth.LoginModel;
import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.RegisterError;
import com.b07.planetze.auth.backend.error.SendEmailVerificationError;
import com.b07.planetze.auth.backend.error.SendPasswordResetError;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;

public interface AuthBackend extends LoginModel {
    void register(
            @NonNull String email,
            @NonNull String password,
            @NonNull String username,
            @NonNull Consumer<Result<AuthUser, RegisterError>> callback
    );

    void sendEmailVerification(
            @NonNull Consumer<Result<Unit, SendEmailVerificationError>> callback
    );
}
