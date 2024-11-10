package com.b07.planetze.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity implements LoginCallback, ResetPasswordCallback, SendResetCallback {
    private static final String TAG = "AuthActivity";

    private FirebaseAuth auth;

    @Override
    public void login(@NonNull String email, @NonNull String password) {
        Log.d(TAG, "logging in " + email);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();

                        Log.d(TAG, "login success: email=" + user.getEmail());
                    } else {
                        Log.e(TAG, "login fail: ", task.getException());
                    }
                });
    }

    @Override
    public void toResetPassword() {
        loadFragment(new SendResetFragment());
    }

    @Override
    public void sendPasswordResetEmail(@NonNull String email) {
        Log.d(TAG, "sending password reset email to " + email);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(AuthActivity.this, "Enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AuthActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        loadFragment(new ResetPasswordFragment());
                    } else {
                        Log.e(TAG, "failed to send reset email: ", task.getException());
                        Toast.makeText(AuthActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void resetPassword(@NonNull String code, @NonNull String newPassword, @NonNull String confirmPassword) {
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(AuthActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(AuthActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.verifyPasswordResetCode(code)
                .addOnCompleteListener(verifyTask -> {
                    if (verifyTask.isSuccessful()) {
                        auth.confirmPasswordReset(code, newPassword)
                                .addOnCompleteListener(resetTask -> {
                                    if (resetTask.isSuccessful()) {
                                        Toast.makeText(AuthActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                                        // finish();  // Optionally, redirect to login screen
                                        loadFragment(new LoginFragment());
                                    } else {
                                        Log.e(TAG, "Password reset failed", resetTask.getException());
                                        Toast.makeText(AuthActivity.this, "Password reset failed: " + resetTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Log.e(TAG, "Invalid reset code", verifyTask.getException());
                        Toast.makeText(AuthActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                    }
                });
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

        auth = FirebaseAuth.getInstance();
        loadFragment(new LoginFragment());
    }
}