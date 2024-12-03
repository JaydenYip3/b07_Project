package com.b07.planetze.EcoGauge;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.util.result.Ok;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class EcoGaugeBreakdownFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_gauge_breakdown, container, false);

        // Reference PieChart from layout
        PieChart pieChart = view.findViewById(R.id.pieChart);

        String temp = "a";
        String timePeriod = ((EcoGaugeOverviewCallback) requireActivity()).getPeriod(temp);
        // Determine the date interval based on the time period
        DateInterval interval = null;
        switch (timePeriod) {
            case "Week":
                interval = new DateInterval(LocalDate.now().minusWeeks(1), LocalDate.now());
                break;
            case "Month":
                interval = new DateInterval(LocalDate.now().minusMonths(1), LocalDate.now());
                break;
            case "Year":
                interval = new DateInterval(LocalDate.now().minusYears(1), LocalDate.now());
                break;
        }

        // Fetch emissions data for the interval
        FirebaseDb database = new FirebaseDb();
        database.fetchDailies(interval, result -> {
            if (result instanceof Ok<DailyFetchList, ?> r) {
                DailyFetchList list = r.get();
                Emissions emissions = list.emissions();
                Log.d(TAG, "emissions: " + emissions);

                for (DailyFetch fetch : list) {
                    Log.d(TAG, "id: " + fetch.id());
                    Log.d(TAG, "date: " + fetch.date());
                    Log.d(TAG, "daily: " + fetch.daily());
                }
                Mass transportation = emissions.transport();
                Mass energyUsage = emissions.energy();
                Mass foodConsumption = emissions.food();
                Mass shoppingConsumption = emissions.shopping();
                Map<String, Double> categoryData = Map.of(
                        "Transportation", transportation.kg(),
                        "Energy use", energyUsage.kg(),
                        "Food consumption", foodConsumption.kg(),
                        "Shopping/consumption", shoppingConsumption.kg()
                );
                ArrayList<PieEntry> entries = new ArrayList<>();
                for (Map.Entry<String, Double> entry : categoryData.entrySet()) {
                    entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
                }

                // Create PieDataSet and PieData
                PieDataSet dataSet = new PieDataSet(entries, "Emissions Breakdown");
                dataSet.setColors(new int[] { R.color.colorTransport, R.color.colorEnergy, R.color.colorFood, R.color.colorShopping }, getContext());
                PieData data = new PieData(dataSet);

                // Set data to PieChart
                pieChart.setData(data);
                pieChart.invalidate(); // Refresh chart
            }
        });

        return view;
    }
}
