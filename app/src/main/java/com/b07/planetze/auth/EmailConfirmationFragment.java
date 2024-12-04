package com.b07.planetze.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.b07.planetze.R;
import com.b07.planetze.database.firebase.FirebaseDb;

public class EmailConfirmationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_confirmation, container, false);

        TextView resendConfirmation = view.findViewById(R.id.resendConfirmation);
        ImageButton btnPrevious = view.findViewById(R.id.ecogauge_back);

        ((EmailConfirmationCallback) requireActivity()).confirmEmail();


        FirebaseDb firebaseDb = new FirebaseDb();


        TextView email_text = view.findViewById(R.id.textView6);

        firebaseDb.fetchUser(result -> {
            result.match(
                    userOption -> userOption.match(
                            user -> {
                                // Successfully retrieved the user, extract email
                                String email = user.email();
                                email_text.setText("An email has been sent to " + email);
                                Log.d("FirebaseEmail", "User email: " + email);
                                // Use the email as needed
                            },
                            () -> Log.e("FirebaseEmail", "User does not exist.")
                    ),
                    dbError -> Log.e("FirebaseEmail", "Error fetching user: " + dbError)
            );
        });

        resendConfirmation.setOnClickListener(v -> {
            ((RegisterCallback) requireActivity()).resendConfirmationEmail();
        });

        btnPrevious.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });


        return view;


    }
    }

