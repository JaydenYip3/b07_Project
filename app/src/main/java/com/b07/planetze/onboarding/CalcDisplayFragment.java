package com.b07.planetze.onboarding;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.Mass;
import com.b07.planetze.database.FakeDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;

public class CalcDisplayFragment extends Fragment {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userUid = user.getUid();
    FakeDatabase db = new FakeDatabase();
    Emissions consumptionEmissions = new Emissions();
    Mass consumptionMass = new Mass();
    Mass operandMass = Mass.fromKg(0);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calc_display, container, false);

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsConsumptionFragment());
            }
        });

        Button buttonNext = view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsHousingFragment());   //change to ecotracker!!-----------------------
            }
        });

        return view;
    }

    public void onSubmit(@NonNull View v) {
        //loadFragment(new QuestionsFoodFragment());

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
