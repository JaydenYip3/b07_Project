package com.b07.planetze.auth;

import androidx.annotation.NonNull;

import com.b07.planetze.auth.backend.AuthBackend;
import com.b07.planetze.auth.backend.FirebaseAuthBackend;
import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.RegisterError;
import com.b07.planetze.auth.backend.error.ResetPasswordError;
import com.b07.planetze.auth.backend.error.SendEmailVerificationError;
import com.b07.planetze.auth.backend.error.SendPasswordResetError;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;

public final class AuthPresenter {
    AuthBackend auth;

    public AuthPresenter() {
        auth = new FirebaseAuthBackend();
    }

    public void login(
            @NonNull String email,
            @NonNull String password,
            @NonNull Consumer<Result<AuthUser, LoginError>> callback
    ) {
        auth.login(email, password, r -> {
            if (!(r instanceof Ok<AuthUser, ?>)) {
                callback.accept(r);
                return;
            }


        });
    }

    void register(
            @NonNull String email,
            @NonNull String password,
            @NonNull String username,
            @NonNull Consumer<Result<AuthUser, RegisterError>> callback
    ) {

    }

    void sendEmailVerification(
            @NonNull Consumer<Result<Unit, SendEmailVerificationError>> callback
    ) {

    }

    void sendPasswordResetEmail(
            @NonNull String email,
            @NonNull Consumer<Result<Unit, SendPasswordResetError>> callback
    ) {

    }

    void resetPassword(
            @NonNull String code,
            @NonNull String password,
            @NonNull Consumer<Result<Unit, ResetPasswordError>> callback
    ) {

    }
}
