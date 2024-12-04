package com.b07.planetze.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.b07.planetze.R;
import com.b07.planetze.auth.backend.FirebaseAuthBackend;

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
        ImageButton btnPrevious = view.findViewById(R.id.ecogauge_back);

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
