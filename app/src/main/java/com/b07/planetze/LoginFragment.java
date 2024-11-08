package com.b07.planetze;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            EditText usernameField = view.findViewById(R.id.loginUsername);
            EditText passwordField = view.findViewById(R.id.loginPassword);

            Log.d(TAG, "user: " + usernameField.getText().toString());
            Log.d(TAG, "pass: " + passwordField.getText().toString());
        });

        return view;
    }
}