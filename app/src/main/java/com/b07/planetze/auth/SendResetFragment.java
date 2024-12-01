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
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.b07.planetze.R;
import com.b07.planetze.auth.backend.FirebaseAuthBackend;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A "send password instructions via email" screen. <br>
 * Activities using this fragment must implement SendResetCallback and AuthScreenSwitch.
 */
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

        var presenter = new LoginPresenter(
                new FirebaseAuthBackend(), (LoginView) requireActivity());

        textBoxEmail = view.findViewById(R.id.textBoxEmail);
        buttonReset = view.findViewById(R.id.buttonReset);
        ImageButton btnPrevious = view.findViewById(R.id.previousPage);

        buttonReset.setOnClickListener(v -> {
            String email = textBoxEmail.getText().toString().trim();

            presenter.sendPasswordResetEmail(email);
        });

        btnPrevious.setOnClickListener(v -> {
            ((AuthScreenSwitch) requireActivity()).switchScreens(AuthScreen.LOGIN);
        });

        return view;
    }
}
