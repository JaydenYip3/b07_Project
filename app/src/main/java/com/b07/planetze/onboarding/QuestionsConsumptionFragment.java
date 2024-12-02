package com.b07.planetze.onboarding;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.measurement.Mass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;

public class QuestionsConsumptionFragment extends Fragment{
    FirebaseDb db = new FirebaseDb();
    Emissions consumptionEmissions;
    private double consumptionEmissionsKgs = 0;
    private double[] emissions = new double[4];
    public LocalDate dateAdded= LocalDate.now();

    QuestionsConsumptionFragment(double[] allEmissions){
        emissions[0] = allEmissions[0];
        emissions[1] = allEmissions[1];
        emissions[2] = allEmissions[2];
        emissions[3] = allEmissions[3];
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_consumption, container, false);


        Log.d(TAG, "emissions: " + emissions[0] + emissions[1] + emissions[2] + emissions[3]);
        ImageButton buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsHousingFragment(emissions));
            }
        });


        Button buttonSubmit = view.findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(view);
            }
        });

        return view;
    }

    public void onSubmit(@NonNull View v) {
        RadioButton b181 = v.findViewById(R.id.radioButton181);
        RadioButton b182 = v.findViewById(R.id.radioButton182);
        RadioButton b183 = v.findViewById(R.id.radioButton183);
        RadioButton b184 = v.findViewById(R.id.radioButton184);
        RadioButton b191 = v.findViewById(R.id.radioButton191);
        RadioButton b192 = v.findViewById(R.id.radioButton192);
        RadioButton b193 = v.findViewById(R.id.radioButton193);
        RadioButton b201 = v.findViewById(R.id.radioButton201);
        RadioButton b202 = v.findViewById(R.id.radioButton202);
        RadioButton b203 = v.findViewById(R.id.radioButton203);
        RadioButton b204 = v.findViewById(R.id.radioButton204);
        RadioButton b205 = v.findViewById(R.id.radioButton205);
        RadioButton b211 = v.findViewById(R.id.radioButton211);
        RadioButton b212 = v.findViewById(R.id.radioButton212);
        RadioButton b213 = v.findViewById(R.id.radioButton213);
        RadioButton b214 = v.findViewById(R.id.radioButton214);
        if((b181.isChecked() || b182.isChecked() || b183.isChecked() || b184.isChecked())
                && (b191.isChecked() || b192.isChecked() || b193.isChecked())
                && (b201.isChecked() || b202.isChecked() || b203.isChecked()|| b204.isChecked() || b205.isChecked())
                && (b211.isChecked() || b212.isChecked() || b213.isChecked()|| b214.isChecked())) {

            if (b181.isChecked()) {
                consumptionEmissionsKgs += 360;
                if (b191.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.5; }
                if (b192.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.3; }
                if (b212.isChecked()) { consumptionEmissionsKgs -= 54; }
                if (b213.isChecked()) { consumptionEmissionsKgs -= 108; }
                if (b214.isChecked()) { consumptionEmissionsKgs -= 180; }
            }
            if (b182.isChecked()) {
                consumptionEmissionsKgs += 120;
                if (b191.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.5; }
                if (b192.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.3; }
                if (b212.isChecked()) { consumptionEmissionsKgs -= 18; } //based on annual (100) * __1.2__ = 120 (quarterly)
                if (b213.isChecked()) { consumptionEmissionsKgs -= 36; } //based on annual (100) * __1.2__ = 120 (quarterly)
                if (b214.isChecked()) { consumptionEmissionsKgs -= 60; } //based on annual (100) * __1.2__ = 120 (quarterly)
            }
            if (b183.isChecked()) {
                consumptionEmissionsKgs += 100;
                if (b191.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.5; }
                if (b192.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.3; }
                if (b212.isChecked()) { consumptionEmissionsKgs -= 15; }
                if (b213.isChecked()) { consumptionEmissionsKgs -= 30; }
                if (b214.isChecked()) { consumptionEmissionsKgs -= 50; }
            }
            if (b184.isChecked()) {
                consumptionEmissionsKgs += 5;
                if (b191.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.5; }
                if (b192.isChecked()) { consumptionEmissionsKgs = consumptionEmissionsKgs * 0.3; }
                if (b212.isChecked()) { consumptionEmissionsKgs -= 0.75; }
                if (b213.isChecked()) { consumptionEmissionsKgs -= 1.5; }
                if (b214.isChecked()) { consumptionEmissionsKgs -= 2.5; }
            }
            if (b202.isChecked()) {
                consumptionEmissionsKgs += 300;
                if (b212.isChecked()) { consumptionEmissionsKgs -= 45; }
                if (b213.isChecked()) { consumptionEmissionsKgs -= 60; }
                if (b214.isChecked()) { consumptionEmissionsKgs -= 90; }
            }
            if (b203.isChecked()) {
                consumptionEmissionsKgs += 600;
                if (b212.isChecked()) { consumptionEmissionsKgs -= 60; }
                if (b213.isChecked()) { consumptionEmissionsKgs -= 120; }
                if (b214.isChecked()) { consumptionEmissionsKgs -= 180; }
            }
            if (b204.isChecked()) {
                consumptionEmissionsKgs += 900;
                if (b212.isChecked()) { consumptionEmissionsKgs -= 90; }
                if (b213.isChecked()) { consumptionEmissionsKgs -= 180; }
                if (b214.isChecked()) { consumptionEmissionsKgs -= 270; }
            }
            if (b205.isChecked()) {
                consumptionEmissionsKgs += 1200;
                if (b212.isChecked()) { consumptionEmissionsKgs -= 120; }
                if (b213.isChecked()) { consumptionEmissionsKgs -= 240; }
                if (b214.isChecked()) { consumptionEmissionsKgs -= 360; }
            }

            emissions[3] = consumptionEmissionsKgs;
            Log.d(TAG, "emissions: " + emissions[0] + emissions[1] + emissions[2] + emissions[3]);
            loadFragment(new CalcDisplayFragment(emissions));
        }
        else{
            Toast.makeText(v.getContext(), "Please complete all the questions.", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
