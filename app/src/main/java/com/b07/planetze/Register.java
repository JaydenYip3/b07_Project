package com.b07.planetze;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

        private Button btnCreate;
        private EditText editEmailRegister, editPasswordRegister;
        private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editEmailRegister = findViewById(R.id.editTextTextEmailAddress);
        editPasswordRegister = findViewById(R.id.editTextTextPassword);
        btnCreate = findViewById(R.id.btnCreateAccount);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmailRegister.getText().toString();
                String password = editPasswordRegister.getText().toString();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()){
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Register.this, "User registered successfully, Please verify your email address.", Toast.LENGTH_LONG).show();
                                    editEmailRegister.setText("");
                                    editPasswordRegister.setText("");
                                }
                                else{
                                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
        };
    }
