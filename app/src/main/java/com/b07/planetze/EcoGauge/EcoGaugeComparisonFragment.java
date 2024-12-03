package com.b07.planetze.EcoGauge;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.onboarding.CountryProcessor;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.util.result.Ok;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EcoGaugeComparisonFragment extends Fragment {

    private TextView comparisonTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_gauge_comparison, container, false);

        // Reference TextView from layout
        comparisonTextView = view.findViewById(R.id.comparisonTextView);

        // Get comparison data
        CountryProcessor countryProcessor = new CountryProcessor(view.getContext(), "countries.json");
        FirebaseDb database = new FirebaseDb();
        String temp = "a";
        String timePeriod = ((EcoGaugeOverviewCallback) requireActivity()).getPeriod(temp);

        // Determine date interval
        DateInterval interval = getDateInterval(timePeriod);
        if (interval == null) {
            comparisonTextView.setText("Invalid time period.");
            return view;
        }

        // Fetch emissions data
        fetchEmissions(database, interval, timePeriod, countryProcessor);

        return view;
    }

    private DateInterval getDateInterval(String timePeriod) {
        switch (timePeriod) {
            case "Week":
                return new DateInterval(LocalDate.now().minusWeeks(1), LocalDate.now());
            case "Month":
                return new DateInterval(LocalDate.now().minusMonths(1), LocalDate.now());
            case "Year":
                return new DateInterval(LocalDate.now().minusYears(1), LocalDate.now());
            default:
                return null;
        }
    }

    private void fetchEmissions(FirebaseDb database, DateInterval interval, String timePeriod,
                                CountryProcessor countryProcessor) {
        database.fetchDailies(interval, result -> {
            if (result instanceof Ok<DailyFetchList, ?> r) {
                Emissions emissions = r.get().emissions();
                double userEmissions = emissions.total().kg();
                fetchUserData(userEmissions, timePeriod, database, countryProcessor);
            } else {
                Log.e(TAG, "Failed to fetch emissions data.");
                updateComparisonText("Error fetching emissions data.");
            }
        });
    }

    private void fetchUserData(double userEmissions, String timePeriod, FirebaseDb database,
                               CountryProcessor countryProcessor) {
        database.fetchUser(result -> {
            result.match(
                    userOption -> userOption.match(
                            user -> {
                                Log.d(TAG, "User found");
                                String country = ((HashMap<String, String>) user.toJson()).get("country");
                                updateComparison(userEmissions, timePeriod, country, countryProcessor);
                            },
                            () -> {
                                Log.e(TAG, "User data not found.");
                                updateComparisonText("User data not found.");
                            }
                    ),
                    dbError -> {
                        Log.e(TAG, "Error fetching user data: " + dbError);
                        updateComparisonText("Error fetching user data.");
                    }
            );
        });
    }

    private void updateComparison(double userEmissions, String timePeriod, String country,
                                  CountryProcessor countryProcessor) {
        double globalAverage = 4.658219;
        double nationalAverage = countryProcessor.getResult(country);

        switch (timePeriod) {
            case "Week":
                nationalAverage /= 56;
                globalAverage /= 56;
                break;
            case "Month":
                nationalAverage /= 12;
                globalAverage /= 12;
                break;
        }

        String comparisonText = String.format(
                "Your emissions: %.2f kg CO2e\nNational average: %.2f kg CO2e\nGlobal average: %.2f kg CO2e",
                userEmissions, nationalAverage, globalAverage
        );
        updateComparisonText(comparisonText);
    }

    private void updateComparisonText(String text) {
        requireActivity().runOnUiThread(() -> comparisonTextView.setText(text));
    }
}
