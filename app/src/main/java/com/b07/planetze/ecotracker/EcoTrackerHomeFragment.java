package com.b07.planetze.ecotracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.database.firebase.FirebaseDb;

public class EcoTrackerHomeFragment extends Fragment {

    FirebaseDb db = new FirebaseDb();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecotracker_home, container, false);

        ImageButton previousPage = view.findViewById(R.id.previousPage);
        TextView daily_emission = view.findViewById(R.id.daily_emissions);


        //TODO daily_emission.setText(db.);
        previousPage.setOnClickListener(v -> {
            requireActivity().finish();
        });


        return view;
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}
