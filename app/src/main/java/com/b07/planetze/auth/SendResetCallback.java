package com.b07.planetze.auth;

import androidx.annotation.NonNull;

public interface SendResetCallback {
    void sendPasswordResetEmail(@NonNull String email);
}
