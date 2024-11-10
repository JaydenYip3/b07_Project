package com.b07.planetze.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.b07.planetze.R;

/**
 * A user registration screen. <br>
 * Activities using this fragment must implement RegisterCallback.
 */
public class RegisterFragment extends Fragment {

    private Button btnCreate;
    private EditText editEmailRegister, editPasswordRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editEmailRegister = view.findViewById(R.id.editTextTextEmailAddress);
        editPasswordRegister = view.findViewById(R.id.editTextTextPassword);
        btnCreate = view.findViewById(R.id.btnCreateAccount);

        btnCreate.setOnClickListener(v -> {
            String email = editEmailRegister.getText().toString();
            String password = editPasswordRegister.getText().toString();

            ((RegisterCallback) requireActivity()).register(email, password);
        });

        return view;
    }
}