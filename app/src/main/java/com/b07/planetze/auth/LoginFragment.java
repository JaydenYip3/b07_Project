package com.b07.planetze.auth;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.WelcomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An email + password login screen. <br>
 * Activities using this fragment must implement LoginCallback and AuthScreenSwitch
 */
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
        TextView resetPassword = view.findViewById(R.id.resetPassword);
        TextView signUp = view.findViewById(R.id.signUpTextView);
        ImageButton btnPrevious = view.findViewById(R.id.previousPage);


        ImageView imageView = view.findViewById(R.id.imageView3);

        // Create a floating animation
        ObjectAnimator floatingAnimator = ObjectAnimator.ofFloat(imageView, "translationY", -10f, 10f); // Slightly up and down
        floatingAnimator.setDuration(1500); // Set the duration of one cycle (1.5 seconds)
        floatingAnimator.setRepeatCount(ValueAnimator.INFINITE); // Loop indefinitely
        floatingAnimator.setRepeatMode(ValueAnimator.REVERSE); // Reverse direction each time
        floatingAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // Smooth motion
        floatingAnimator.start();

        btnPrevious.setOnClickListener(v -> {
            requireActivity().finish();
        });

        loginButton.setOnClickListener(v -> {
            EditText emailField = view.findViewById(R.id.loginEmail);
            EditText passwordField = view.findViewById(R.id.loginPassword);

            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString();

            ((LoginCallback) requireActivity()).login(email, password);
        });

        resetPassword.setOnClickListener(v -> {
            ((AuthScreenSwitch) requireActivity()).switchScreens(AuthScreen.SEND_PASSWORD_RESET);
        });

        signUp.setOnClickListener(v -> {
            AuthActivity.start(requireActivity(), AuthScreen.REGISTER);
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}