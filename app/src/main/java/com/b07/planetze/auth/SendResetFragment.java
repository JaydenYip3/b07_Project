package com.b07.planetze.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.b07.planetze.R;
import com.google.firebase.auth.FirebaseAuth;

public class SendResetFragment extends Fragment {
    private static final String TAG = "SendResetFragment";
    private EditText textBoxEmail;
    private Button buttonReset, buttonSignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_reset, container, false);

        textBoxEmail = view.findViewById(R.id.textBoxEmail);
        buttonReset = view.findViewById(R.id.buttonReset);
        buttonSignIn = view.findViewById(R.id.buttonSignIn);

        buttonReset.setOnClickListener(v -> {
            String email = textBoxEmail.getText().toString().trim();

            if (getActivity() instanceof SendResetCallback) {
                ((SendResetCallback) getActivity()).sendPasswordResetEmail(email);
            } else {
                Log.e(TAG, "attached activity does not implement SendResetCallback");
            }
        });

//        buttonSignIn.setOnClickListener(v -> {
//            // Redirect to the sign-in screen
//            Intent intent = new Intent(ResetPassword.this, SignInActivity.class);
//            startActivity(intent);
//        });

        return view;
    }
}
