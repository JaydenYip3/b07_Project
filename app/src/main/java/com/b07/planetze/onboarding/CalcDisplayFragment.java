package com.b07.planetze.onboarding;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.home.HomeActivity;
import com.b07.planetze.util.measurement.Mass;

import java.util.HashMap;
import java.util.Map;

public class CalcDisplayFragment extends Fragment {
    public CalcDisplayFragment() {}

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        var model = new ViewModelProvider(requireActivity())
                .get(OnboardingViewModel.class);

        Emissions emissions = model.emissions();
        model.postOnboardingEmissions(emissions.immutableCopy());

        TextView total = view.findViewById(R.id.total);
        TextView transport = view.findViewById(R.id.transportAmount);
        TextView food = view.findViewById(R.id.foodAmount);
        TextView housing = view.findViewById(R.id.housingAmount);
        TextView consumption = view.findViewById(R.id.consumptionAmount);
        TextView nationalAvg = view.findViewById(R.id.textView8);
        TextView globalTarget = view.findViewById(R.id.textView9);

        Mass totalMass = emissions.total();
        Log.d(TAG, "emissions: " + emissions);
        Log.d(TAG, "mass total: " + totalMass);

        double totalAmount = totalMass.tons();
        String amountText = totalMass.formatTons();
        String globalTargetMet = "Your footprint has reached global targets for climate change reduction of 2 tons CO2e/year! Good job!";
        total.setText(amountText);
        if (totalAmount < 2) {
            globalTarget.setText(globalTargetMet);
        }

        String transportAmountText = emissions.transport().formatTons() + " of CO2e/year";
        String foodAmountText = emissions.food().formatTons() + " of CO2e/year";
        String housingAmountText = emissions.energy().formatTons() + " of CO2e/year";
        String consumptionAmountText = emissions.shopping().formatTons() + " of CO2e/year";

        transport.setText(transportAmountText);
        food.setText(foodAmountText);
        housing.setText(housingAmountText);
        consumption.setText(consumptionAmountText);

        model.fetchUser(user -> {
            String country = user.country();

            var countryProcessor = new CountryProcessor(view.getContext(), "countries.json");

            double regionAverage = countryProcessor.getResult(country);
            double percentage;
            String descriptor = "";
            if (totalAmount > regionAverage){
                percentage = Math.round(1000 * totalAmount / regionAverage)/10.0;
                descriptor = "above";
            } else {
                percentage = Math.round(1000 * (1 - totalAmount / regionAverage))/10.0;
                descriptor = "below";
            }
            String avgText = "Your annual carbon footprint is " + percentage + "% " + descriptor + " the national average for " + country + ": "  + regionAverage;
            if (totalAmount == regionAverage) {
                avgText = "Your annual carbon footprint is the same as national average for " + country + ": " + regionAverage;
            }
            nationalAvg.setText(avgText);
        });

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> model.setScreen(OnboardingScreen.CONSUMPTION));

        Button buttonNext = view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(v -> HomeActivity.start(requireActivity()));
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calc_display, container, false);
    }
}
