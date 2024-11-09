package com.b07.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    private EditText textBoxEmail;
    private Button buttonReset, buttonSignIn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forgot_password);

        auth = FirebaseAuth.getInstance();
        textBoxEmail = findViewById(R.id.textBoxEmail);
        buttonReset = findViewById(R.id.buttonReset);
        buttonSignIn = findViewById(R.id.buttonSignIn);

        buttonReset.setOnClickListener(v -> {
            String email = textBoxEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(forgotPassword.this, "Enter your email address", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotPassword.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgotPassword.this, forgotPasswordTwo.class));
                        } else {
                            Log.e("ForgotPassword", "Failed to send reset email", task.getException());
                            Toast.makeText(forgotPassword.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        buttonSignIn.setOnClickListener(v -> {
            // Redirect to the sign-in screen
            Intent intent = new Intent(forgotPassword.this, SignInActivity.class);
            startActivity(intent);
        });
    }
}
}
