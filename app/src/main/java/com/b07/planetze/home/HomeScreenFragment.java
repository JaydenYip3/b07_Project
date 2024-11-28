package com.b07.planetze.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.auth.RegisterCallback;
import com.b07.planetze.ecotracker.EcoTrackerActivity;

public class HomeScreenFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView username = view.findViewById(R.id.username);
        LinearLayout tracker = view.findViewById(R.id.tracker);
        LinearLayout balance = view.findViewById(R.id.balance);
        LinearLayout hub = view.findViewById(R.id.hub);


        username.setText("Jayden");

        tracker.setOnClickListener(v -> EcoTrackerActivity.start(requireActivity()));

//        ((EmailConfirmationCallback) requireActivity()).confirmEmail();
        balance.setOnClickListener(v -> {
            // Create a new instance of NotFinishedFragment
            NotFinishedFragment fragment = new NotFinishedFragment();

            // Replace the existing fragment with the new one
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment); // Replace with your container ID
            transaction.addToBackStack(null); // Add to backstack if you want to allow "back" navigation
            transaction.commit(); // Commit the transaction
        });

        hub.setOnClickListener(v -> {
            // Create a new instance of NotFinishedFragment
            NotFinishedFragment fragment = new NotFinishedFragment();

            // Replace the existing fragment with the new one
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment); // Replace with your container ID
            transaction.addToBackStack(null); // Add to backstack if you want to allow "back" navigation
            transaction.commit(); // Commit the transaction
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
