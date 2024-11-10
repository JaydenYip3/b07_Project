package com.b07.planetze;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.b07.planetze.auth.RegisterCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A user registration screen. <br>
 * Activities using this fragment must implement RegisterCallback.
 */
public class Register extends Fragment {

    private Button btnCreate;
    private EditText editEmailRegister, editPasswordRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, container, false);

        editEmailRegister = view.findViewById(R.id.editTextTextEmailAddress);
        editPasswordRegister = view.findViewById(R.id.editTextTextPassword);
        btnCreate = view.findViewById(R.id.btnCreateAccount);

        btnCreate.setOnClickListener(v -> {
            String email = editEmailRegister.getText().toString();
            String password = editPasswordRegister.getText().toString();

            ((RegisterCallback) getActivity()).register(email, password);
        });

        return view;
    }

}