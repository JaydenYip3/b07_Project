package com.b07.planetze.onboarding;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.util.measurement.Mass;

public class QuestionsConsumptionFragment extends Fragment {
    @NonNull private static final String TAG = "QuestionsConsumptionFragment";

    public QuestionsConsumptionFragment() {}

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        var model = new ViewModelProvider(requireActivity())
                .get(OnboardingViewModel.class);

        Log.d(TAG, "emissions: " + model.emissions().total());
        ImageButton buttonBack = view.findViewById(R.id.onboarding_consumption_back);

        buttonBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        Button buttonSubmit = view.findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(v -> onSubmit(view, model));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_questions_consumption, container, false);
    }

    public void onSubmit(@NonNull View v, @NonNull OnboardingViewModel model) {
        double consumptionEmissionsKgs = 0;
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

            model.emissions().shopping().set(Mass.kg(consumptionEmissionsKgs));
            Log.d(TAG, "emissions: " + model.emissions());
            model.setScreen(OnboardingScreen.CALC_DISPLAY);
        } else {
            Toast.makeText(v.getContext(), "Please complete all the questions.", Toast.LENGTH_SHORT).show();
        }
    }
}
