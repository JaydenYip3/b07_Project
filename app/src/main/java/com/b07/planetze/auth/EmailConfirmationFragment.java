package com.b07.planetze.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.WelcomeFragment;

public class EmailConfirmationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_confirmation, container, false);

        TextView resendConfirmation = view.findViewById(R.id.resendConfirmation);
        ImageButton btnPrevious = view.findViewById(R.id.previousPage);


//        ((EmailConfirmationCallback) requireActivity()).confirmEmail();


        resendConfirmation.setOnClickListener(v -> {
            ((RegisterCallback) requireActivity()).resendConfirmationEmail();
        });

        btnPrevious.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
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

