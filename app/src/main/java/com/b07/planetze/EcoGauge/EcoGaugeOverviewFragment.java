package com.b07.planetze.EcoGauge;

import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.b07.planetze.R;

public class EcoGaugeOverviewFragment extends Fragment {

    private TextView resultsTextView;
    private Spinner timeSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_gauge_overview, container, false);

        resultsTextView = view.findViewById(R.id.resultsTextView);
        timeSpinner = view.findViewById(R.id.timeSpinner);
        Button emissionsTrendButton = view.findViewById(R.id.emissionsTrendButton);
        Button breakdownButton = view.findViewById(R.id.graphByCategoryButon);
        Button comparisonButton = view.findViewById(R.id.comparisonButton);

        // Set up spinner to handle time period selection
        setupTimeSpinner();

        emissionsTrendButton.setOnClickListener(v -> {
            ((EcoGaugeScreenSwitch) requireActivity()).switchScreens(EcoGaugeScreen.TRENDS);
        });

        breakdownButton.setOnClickListener(v -> {
            ((EcoGaugeScreenSwitch) requireActivity()).switchScreens(EcoGaugeScreen.BREAKDOWN);
        });

        comparisonButton.setOnClickListener(v -> {
            ((EcoGaugeScreenSwitch) requireActivity()).switchScreens(EcoGaugeScreen.COMPARISON);
        });

        return view;
    }

    private void setupTimeSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_periods, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        timeSpinner.setAdapter(adapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTimePeriod = parent.getItemAtPosition(position).toString();
                updateEmissionsData(selectedTimePeriod);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optionally handle no selection
            }
        });
    }

    private void updateEmissionsData(String timePeriod) {
        double emissions = ((EcoGaugeOverviewCallback) requireActivity()).calculateEmissionsForPeriod(timePeriod);
        resultsTextView.setText("You've emitted " + emissions + " kg CO2e this " + timePeriod.toLowerCase() + ".");
    }
}