package com.b07.planetze.auth;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.WelcomeFragment;

/**
 * A user registration screen. <br>
 * Activities using this fragment must implement RegisterCallback.
 */
public class RegisterFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        EditText editEmailRegister = view.findViewById(R.id.editTextTextEmailAddress);
        EditText editPasswordRegister = view.findViewById(R.id.editTextTextPassword);
        EditText editConfirmPasswordRegister = view.findViewById(R.id.editTextTextConfirmPassword);
        Button btnCreate = view.findViewById(R.id.btnCreateAccount);
        ImageButton btnPrevious = view.findViewById(R.id.previousPage);
        TextView signIn = view.findViewById(R.id.signInTextView);

        ImageView imageView = view.findViewById(R.id.imageView3);

        // Create a floating animation
        ObjectAnimator floatingAnimator = ObjectAnimator.ofFloat(imageView, "translationY", -10f, 10f); // Slightly up and down
        floatingAnimator.setDuration(1500); // Set the duration of one cycle (1.5 seconds)
        floatingAnimator.setRepeatCount(ValueAnimator.INFINITE); // Loop indefinitely
        floatingAnimator.setRepeatMode(ValueAnimator.REVERSE); // Reverse direction each time
        floatingAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // Smooth motion
        floatingAnimator.start();


        btnCreate.setOnClickListener(v -> {
            String email = editEmailRegister.getText().toString();
            String password = editPasswordRegister.getText().toString();
            String confirmPassword = editConfirmPasswordRegister.getText().toString();

            ((RegisterCallback) requireActivity()).register(email, password, confirmPassword);
        });

        btnPrevious.setOnClickListener(v -> {
            loadFragment(new WelcomeFragment());
        });

        signIn.setOnClickListener(v -> {
            AuthActivity.start(requireActivity(), AuthScreen.LOGIN);
        });
        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}