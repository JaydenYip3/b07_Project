package com.b07.planetze.EcoGauge;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.onboarding.CountryProcessor;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.util.result.Ok;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;

public class EcoGaugeOverviewFragment extends Fragment {

    private TextView resultsTextView;
    private TextView comparison;
    private Spinner timeSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_gauge_overview, container, false);

        resultsTextView = view.findViewById(R.id.ecogauge_emissions);
        timeSpinner = view.findViewById(R.id.ecogauge_interval);
        View emissionsTrendButton = view.findViewById(R.id.ecogauge_to_trends);
        View breakdownButton = view.findViewById(R.id.ecogauge_to_breakdown);
        comparison = view.findViewById(R.id.ecogauge_comparison);

        Database db = new FirebaseDb();
        var countryProcessor = new CountryProcessor(view.getContext(), "countries.json");

        // Set up spinner to handle time period selection
        setupTimeSpinner(db, countryProcessor);

        emissionsTrendButton.setOnClickListener(v -> {
            ((EcoGaugeScreenSwitch) requireActivity()).switchScreens(EcoGaugeScreen.TRENDS);
        });

        breakdownButton.setOnClickListener(v -> {
            ((EcoGaugeScreenSwitch) requireActivity()).switchScreens(EcoGaugeScreen.BREAKDOWN);
        });

        ImageButton btnPrevious = view.findViewById(R.id.ecogauge_back);
        btnPrevious.setOnClickListener(v -> {
            requireActivity().finish();
        });

        return view;
    }

    private void setupTimeSpinner(Database db, CountryProcessor countryProcessor) {
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
                fetchEmissions(db, getDateInterval(selectedTimePeriod), selectedTimePeriod, countryProcessor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optionally handle no selection
            }
        });
    }

    private DateInterval getDateInterval(String timePeriod) {
        return switch (timePeriod) {
            case "Week" ->
                    DateInterval.between(LocalDate.now().minusWeeks(1), LocalDate.now().plusDays(1));
            case "Month" ->
                    DateInterval.between(LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1));
            case "Year" ->
                    DateInterval.between(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1));
            default -> null;
        };
    }

    private void fetchEmissions(Database database, DateInterval interval, String timePeriod,
                                CountryProcessor countryProcessor) {
        database.fetchDailies(interval, result -> {
            if (result instanceof Ok<DailyFetchList, ?> r) {
                Emissions emissions = r.get().emissions();
                Mass userEmissions = emissions.total();
                fetchUserData(userEmissions, timePeriod, database, countryProcessor);
            } else {
                Log.e(TAG, "Failed to fetch emissions data.");
                comparison.setText("Error fetching emissions data.");
            }
        });
    }

    private void fetchUserData(Mass userEmissions, String timePeriod, Database database,
                               CountryProcessor countryProcessor) {
        database.fetchUser(result -> {
            result.match(
                    userOption -> userOption.match(
                            user -> {
                                Log.d(TAG, "User found");
                                String country = user.country();
                                updateComparison(userEmissions, timePeriod, country, countryProcessor);
                            },
                            () -> {
                                Log.e(TAG, "User data not found.");
                                comparison.setText("User data not found.");
                            }
                    ),
                    dbError -> {
                        Log.e(TAG, "Error fetching user data: " + dbError);
                        comparison.setText("Error fetching user data.");
                    }
            );
        });
    }

    private void updateComparison(Mass userEmissions, String timePeriod, String country,
                                  CountryProcessor countryProcessor) {
        Mass globalAverage = Mass.tons(4.658219);
        Mass nationalAverage = Mass.tons(countryProcessor.getResult(country));

        switch (timePeriod) {
            case "Week":
                nationalAverage.scale(1.0/52);
                globalAverage.scale(1.0/52);
                break;
            case "Month":
                nationalAverage.scale(1.0/12);
                globalAverage.scale(1.0/12);
                break;
        }

        String comparisonText = String.format(
                Locale.US,
                "That's %.0f%% of the national average (%s CO2e)\nor %.0f%% of the global average (%s CO2e).",
                100 * userEmissions.kg() / nationalAverage.kg(),
                nationalAverage.format(),
                100 * userEmissions.kg() / globalAverage.kg(),
                globalAverage.format()
        );

        comparison.setText(comparisonText);
        resultsTextView.setText(userEmissions.format());
    }
}