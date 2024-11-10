package com.b07.planetze.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.b07.planetze.R;

/**
 * A password reset screen. <br>
 * Activities using this fragment must implement ResetPasswordCallback.
 */
public class ResetPasswordFragment extends Fragment {
    private static final String TAG = "ResetPasswordFragment";
    private EditText textBoxCode, textBoxPass, textBoxConPass;
    private Button buttonResetPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        textBoxCode = view.findViewById(R.id.textBoxCode);
        textBoxPass = view.findViewById(R.id.textBoxPass);
        textBoxConPass = view.findViewById(R.id.textBoxConPass);
        buttonResetPassword = view.findViewById(R.id.button3);

        buttonResetPassword.setOnClickListener(v -> {
            String code = textBoxCode.getText().toString().trim();
            String newPassword = textBoxPass.getText().toString().trim();
            String confirmPassword = textBoxConPass.getText().toString().trim();

            ((ResetPasswordCallback)getActivity()).resetPassword(code, newPassword, confirmPassword);
        });

        return view;
    }
}
