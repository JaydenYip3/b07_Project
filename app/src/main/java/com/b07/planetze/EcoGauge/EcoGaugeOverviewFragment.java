package com.b07.planetze.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.DatedEmissions;
import com.b07.planetze.database.FakeDatabase;
import com.b07.planetze.database.User;
import com.b07.planetze.database.UserId;
import com.b07.planetze.databinding.FragmentEcoGaugeOverviewBinding;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.Emissions;
import com.b07.planetze.util.Mass;
import com.b07.planetze.util.Result;
import java.time.LocalDate;
import java.util.List;

public class EcoGaugeOverviewFragment extends Fragment {
    private FragmentEcoGaugeOverviewBinding binding;
    private static final String TAG = "EcoGaugeOverviewFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEcoGaugeOverviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Mock database and user setup
        Database db = new FakeDatabase();
        User user = new User(new UserId("123"));
        LocalDate start = LocalDate.of(2023, 11, 1);
        LocalDate end = LocalDate.of(2023, 11, 10);

        // Example to populate emissions in the FakeDatabase
        populateFakeDatabase(db, user.id());

        // Fetch emissions over interval
        db.fetchEmissionsOverInterval(user.id(), new DateInterval(start, end), result -> {
            result.match(
                    emissions -> displayTotalEmissions(emissions),
                    error -> Log.e(TAG, error.message())
            );
        });

        return root;
    }

    /**
     * Displays the total emissions over the interval in the UI.
     * @param emissionsList The list of dated emissions.
     */
    private void displayTotalEmissions(List<DatedEmissions> emissionsList) {
        Mass total = new Mass();
        for (DatedEmissions datedEmission : emissionsList) {
            total.add(datedEmission.emissions().total());
        }

        // Update UI
        String message = String.format("You've emitted %.2f kg CO2e during the interval.", total.getKg());
        requireActivity().runOnUiThread(() -> binding.textViewTotalEmissions.setText(message));
    }

    /**
     * Populates the FakeDatabase with example data.
     * @param db The database to populate.
     * @param userId The user ID for whom data will be populated.
     */
    private void populateFakeDatabase(Database db, UserId userId) {
        LocalDate today = LocalDate.now();

        // Add emissions for each day
        for (int i = 0; i < 10; i++) {
            LocalDate date = today.minusDays(i);
            Emissions emissions = new Emissions();
            emissions.transportation().setKg(5.0 * i);
            emissions.energy().setKg(3.0 * i);
            emissions.food().setKg(2.0 * i);
            emissions.shopping().setKg(1.0 * i);
            db.updateDailyEmissions(userId, date, emissions);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
