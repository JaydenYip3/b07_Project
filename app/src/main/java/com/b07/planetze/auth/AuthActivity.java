package com.b07.planetze.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

/**
 * An activity that deals with user authentication.
 */
public class AuthActivity extends AppCompatActivity implements LoginCallback, RegisterCallback, ResetPasswordCallback, SendResetCallback, AuthScreenSwitch {
    private static final String TAG = "AuthActivity";
    private static final String EXTRA_INITIAL_SCREEN = "com.b07.planetze.AUTH_INITIAL_SCREEN";

    private FirebaseAuth auth;

    /**
     * Starts an AuthActivity.
     * @param context the context from which to start the activity
     * @param initialScreen the screen to display upon starting
     */
    public static void start(Context context, AuthScreen initialScreen) {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(EXTRA_INITIAL_SCREEN, initialScreen);
        context.startActivity(intent);
    }

    @Override
    public void login(@NonNull String email, @NonNull String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email and password fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email address is malformed", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();

                        Toast.makeText(this, "Logged in as " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Exception e = task.getException();

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "login error: ", e);
                        }
                    }
                });
    }

    @Override
    public void register(@NonNull String email, @NonNull String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(this, verificationTask -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "User registered successfully; Please verify your email address", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, verificationTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void sendPasswordResetEmail(@NonNull String email) {
        Log.d(TAG, "sending password reset email to " + email);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        loadFragment(new ResetPasswordFragment());
                    } else {
                        Log.e(TAG, "failed to send reset email: ", task.getException());
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void resetPassword(@NonNull String code, @NonNull String newPassword, @NonNull String confirmPassword) {
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.verifyPasswordResetCode(code)
                .addOnCompleteListener(verifyTask -> {
                    if (verifyTask.isSuccessful()) {
                        auth.confirmPasswordReset(code, newPassword)
                                .addOnCompleteListener(resetTask -> {
                                    if (resetTask.isSuccessful()) {
                                        Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                                        switchScreens(AuthScreen.LOGIN);
                                    } else {
                                        Log.e(TAG, "Password reset failed", resetTask.getException());
                                        Toast.makeText(this, "Password reset failed: " + resetTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Log.e(TAG, "Invalid reset code", verifyTask.getException());
                        Toast.makeText(this, "Invalid code", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void switchScreens(AuthScreen screen) {
        switch(screen) {
            case LOGIN -> loadFragment(new LoginFragment());
            case REGISTER -> loadFragment(new RegisterFragment());
            case RESET_PASSWORD -> loadFragment(new ResetPasswordFragment());
            case SEND_PASSWORD_RESET -> loadFragment(new SendResetFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new AuthActivityInitException("EXTRA_INITIAL_SCREEN not specified in intent extras");
        }

        AuthScreen initialScreen = (AuthScreen) extras.getSerializable(EXTRA_INITIAL_SCREEN);
        if (initialScreen == null) {
            throw new AuthActivityInitException("invalid EXTRA_INITIAL_SCREEN specified in intent extras");
        }

        switchScreens(initialScreen);

        auth = FirebaseAuth.getInstance();
    }
}