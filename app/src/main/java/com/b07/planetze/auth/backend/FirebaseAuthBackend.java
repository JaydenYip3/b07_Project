package com.b07.planetze.auth.backend;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import android.util.Patterns;

import androidx.annotation.NonNull;

import com.b07.planetze.auth.AuthUser;
import com.b07.planetze.auth.backend.error.CredentialsError;
import com.b07.planetze.auth.backend.error.EmptyEmailError;
import com.b07.planetze.auth.backend.error.EmptyFieldsError;
import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.MalformedEmailError;
import com.b07.planetze.auth.backend.error.OtherAuthError;
import com.b07.planetze.auth.backend.error.RegisterError;
import com.b07.planetze.auth.backend.error.ResetPasswordError;
import com.b07.planetze.auth.backend.error.SendEmailVerificationError;
import com.b07.planetze.auth.backend.error.SendPasswordResetError;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.result.Error;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.function.Consumer;

public final class FirebaseAuthBackend implements AuthBackend {
    @NonNull private final FirebaseAuth auth;

    public FirebaseAuthBackend() {
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    private static <T> Error<T, String> getError(@NonNull Task<?> task) {
        String message = Option
                .mapNull(task.getException())
                .resolve(Exception::getMessage, () -> "");

        return error(message);
    }

    @NonNull
    private Result<FirebaseUser, String> getFirebaseUser() {
        return Option
                .mapNull(auth.getCurrentUser())
                .okOr("User is not logged in");
    }

    @NonNull
    private Result<AuthUser, String> getAuthUser() {
        var r = getFirebaseUser();
        if (r instanceof Error<?, String> e) {
            return e.map();
        }
        var fbUser = ((Ok<FirebaseUser, ?>) r).get();

        String fbEmail = fbUser.getEmail();
        if (fbEmail == null) {
            return error("User was improperly initialized");
        }

        return ok(new AuthUser(fbEmail, fbUser.isEmailVerified()));
    }

    @Override
    public void login(
            @NonNull String email,
            @NonNull String password,
            @NonNull Consumer<Result<AuthUser, LoginError>> callback
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            callback.accept(error(new EmptyFieldsError()));
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            callback.accept(error(new MalformedEmailError()));
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                callback.accept(getAuthUser().mapError(OtherAuthError::new));
            } else {
                Exception e = task.getException();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    callback.accept(error(new CredentialsError()));
                } else {
                    Error<AuthUser, String> error = getError(task);
                    callback.accept(error.mapError(OtherAuthError::new));
                }
            }
        });
    }

    @Override
    public void register(
            @NonNull String email,
            @NonNull String password,
            @NonNull String username,
            @NonNull Consumer<Result<AuthUser, RegisterError>> callback
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            callback.accept(error(new EmptyFieldsError()));
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            callback.accept(error(new MalformedEmailError()));
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                callback.accept(getAuthUser().mapError(OtherAuthError::new));
                return;
            }

            Exception e = task.getException();
            Error<AuthUser, String> error = getError(task);

            if (e instanceof FirebaseAuthUserCollisionException) {
                callback.accept(error
                        .mapError(RegisterError.UserCollision::new));
            } else {
                callback.accept(error.mapError(OtherAuthError::new));
            }
        });
    }

    @Override
    public void sendEmailVerification(
            @NonNull Consumer<Result<Unit, SendEmailVerificationError>> callback
    ) {
        var r = getFirebaseUser();
        if (r instanceof Error<?, String> e) {
            callback.accept(e.<Unit>map().mapError(OtherAuthError::new));
            return;
        }
        var fbUser = ((Ok<FirebaseUser, ?>) r).get();

        fbUser.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.accept(ok());
            } else {
                Error<Unit, String> e = getError(task);
                callback.accept(e.mapError(OtherAuthError::new));
            }
        });
    }

    @Override
    public void sendPasswordResetEmail(
            @NonNull String email,
            @NonNull Consumer<Result<Unit, SendPasswordResetError>> callback
    ) {
        if (email.isEmpty()) {
            callback.accept(error(new EmptyEmailError()));
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            callback.accept(error(new MalformedEmailError()));
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.accept(ok());
            } else {
                Error<Unit, String> e = getError(task);
                callback.accept(e.mapError(OtherAuthError::new));
            }
        });
    }

    private void confirmPasswordReset(
            @NonNull String code,
            @NonNull String password,
            @NonNull Consumer<Result<Unit, ResetPasswordError>> callback
    ) {
        auth.confirmPasswordReset(code, password)
                .addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                callback.accept(ok());
            } else {
                Error<Unit, String> e = getError(task);
                callback.accept(e.mapError(OtherAuthError::new));
            }
        });
    }

    @Override
    public void resetPassword(
            @NonNull String code,
            @NonNull String password,
            @NonNull Consumer<Result<Unit, ResetPasswordError>> callback
    ) {
        auth.verifyPasswordResetCode(code).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                confirmPasswordReset(code, password, callback);
            } else {
                Error<Unit, String> e = getError(task);
                callback.accept(
                        e.mapError(ResetPasswordError.InvalidCode::new));
            }
        });
    }
}
