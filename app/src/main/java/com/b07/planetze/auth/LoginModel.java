package com.b07.planetze.auth;

import androidx.annotation.NonNull;

import com.b07.planetze.auth.backend.AuthBackend;
import com.b07.planetze.auth.backend.FirebaseAuthBackend;
import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.SendPasswordResetError;
import com.b07.planetze.common.User;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;

public final class LoginModel {
    @NonNull private final AuthBackend auth;
    @NonNull private final Database db;

    public LoginModel() {
        auth = new FirebaseAuthBackend();
        db = new FirebaseDb();
    }

    public void login(
            @NonNull String email,
            @NonNull String password,
            @NonNull Consumer<Result<AuthUser, LoginError>> callback
    ) {
        auth.login(email, password, callback);
    }

    public void sendPasswordResetEmail(
            @NonNull String email,
            @NonNull Consumer<Result<Unit, SendPasswordResetError>> callback
    ) {
        auth.sendPasswordResetEmail(email, callback);
    }

    void postUser(
            @NonNull User user,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        db.postUser(user, callback);
    }
}
