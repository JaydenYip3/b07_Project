package com.b07.planetze.auth;

import androidx.annotation.NonNull;

import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.OtherAuthError;
import com.b07.planetze.util.result.Result;

public final class LoginPresenter {
    @NonNull private final LoginModel model;
    @NonNull private final LoginView view;

    public LoginPresenter(@NonNull LoginModel model, @NonNull LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void login(@NonNull String email, @NonNull String password) {
        model.login(email, password, r -> {
            r.match(user -> {
                if (!user.isEmailVerified()) {
                    view.switchScreens(AuthScreen.EMAIL_CONFIRMATION);
                    return;
                }

                view.showToast("Logged in as " + user.email());
                view.toHome();
            }, e -> {
                view.showToast(e.message());
            });
        });
    }

    public void forgotPassword() {
        view.switchScreens(AuthScreen.SEND_PASSWORD_RESET);
    }

    public void sendPasswordResetEmail(@NonNull String email) {
        model.sendPasswordResetEmail(email, r -> {
            r.match(ok -> {
                view.showToast("Password reset email sent");
            }, e -> {
                view.showToast(e.message());
            });
        });
    }
}
