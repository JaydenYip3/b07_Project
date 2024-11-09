package com.b07.planetze;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class forgotPasswordTwo extends AppCompatActivity{
    private EditText textBoxCode, textBoxPass, textBoxConPass;
    private Button buttonResetPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_two_forgot_password);

        auth = FirebaseAuth.getInstance();
        textBoxCode = findViewById(R.id.textBoxCode);
        textBoxPass = findViewById(R.id.textBoxPass);
        textBoxConPass = findViewById(R.id.textBoxConPass);
        buttonResetPassword = findViewById(R.id.button3);

        buttonResetPassword.setOnClickListener(v -> {
            String code = textBoxCode.getText().toString().trim();
            String newPassword = textBoxPass.getText().toString().trim();
            String confirmPassword = textBoxConPass.getText().toString().trim();

            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(forgotPasswordTwo.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(forgotPasswordTwo.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.verifyPasswordResetCode(code)
                    .addOnCompleteListener(verifyTask -> {
                        if (verifyTask.isSuccessful()) {
                            auth.confirmPasswordReset(code, newPassword)
                                    .addOnCompleteListener(resetTask -> {
                                        if (resetTask.isSuccessful()) {
                                            Toast.makeText(forgotPasswordTwo.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                                            finish();  // Optionally, redirect to login screen
                                        } else {
                                            Log.e("ResetPassword", "Password reset failed", resetTask.getException());
                                            Toast.makeText(forgotPasswordTwo.this, "Password reset failed: " + resetTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Log.e("ResetPassword", "Invalid reset code", verifyTask.getException());
                            Toast.makeText(forgotPasswordTwo.this, "Invalid code", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
