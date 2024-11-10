package com.b07.planetze.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.b07.planetze.R;


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

        Button loginButton = view.findViewById(R.id.loginSubmitButton);
        Button resetPasswordButton = view.findViewById(R.id.resetPasswordButton);

        loginButton.setOnClickListener(v -> {
            EditText emailField = view.findViewById(R.id.loginEmail);
            EditText passwordField = view.findViewById(R.id.loginPassword);

            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (getActivity() instanceof LoginCallback) {
                ((LoginCallback)getActivity()).login(email, password);
            } else {
                Log.e(TAG, "attached activity does not implement LoginCallback");
            }
        });

        resetPasswordButton.setOnClickListener(v -> {
            if (getActivity() instanceof LoginCallback) {
                ((LoginCallback)getActivity()).toResetPassword();
            } else {
                Log.e(TAG, "attached activity does not implement LoginCallback");
            }
        });

        return view;
    }
}