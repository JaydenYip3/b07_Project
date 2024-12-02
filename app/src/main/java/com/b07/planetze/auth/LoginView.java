package com.b07.planetze.auth;

import androidx.annotation.NonNull;

public interface LoginView {
    void showToast(@NonNull String message);

    void switchScreens(@NonNull AuthScreen screen);

    void toHome();
}
