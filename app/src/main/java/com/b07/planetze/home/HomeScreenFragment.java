package com.b07.planetze.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.common.User;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.ecotracker.EcoTrackerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreenFragment extends Fragment {

    User currentUser;
    FirebaseDb db = new FirebaseDb();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView username = view.findViewById(R.id.home_username);
        LinearLayout tracker = view.findViewById(R.id.tracker);
        LinearLayout balance = view.findViewById(R.id.balance);
        LinearLayout hub = view.findViewById(R.id.hub);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        databaseReference.child("users").child(currentUserId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


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
