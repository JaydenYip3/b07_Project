package com.b07.planetze.EcoGauge;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import com.b07.planetze.R;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.common.Emissions;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.b07.planetze.onboarding.CountryProcessor;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EcoGaugeActivity extends AppCompatActivity implements EcoGaugeOverviewCallback, EcoGaugeScreenSwitch, EcoGaugeTrendsCallback, EcoGaugeBreakdownCallback, EcoGaugeComparisonCallback{
    String timePeriod;
    @Override
    public double calculateEmissionsForPeriod(String timePeriod) {
        // Create a variable to store the emissions
        final double[] totalEmissions = {0};
        this.timePeriod = timePeriod;

        // Determine the date interval based on the time period
        DateInterval interval;
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
            default:
                // Invalid time period, return 0
                return 0;
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
                   Mass kilos = emissions.total();
                   totalEmissions[0] = kilos.kg();
               }
           });
        return totalEmissions[0];
    }
    @Override
    public LineGraphSeries<DataPoint> getDataPoints(){
        FirebaseDb database = new FirebaseDb();
        DataPoint d1 = null;
        DataPoint d2 = null;
        DataPoint d3 = null;
        DataPoint d4 = null;
        DataPoint d5 = null;
        switch (this.timePeriod){
            case "Week":
                d1 = new DataPoint(1, emissionsInterval(new DateInterval (LocalDate.now().minusWeeks(5), LocalDate.now().minusWeeks(4)), database));
                d2 = new DataPoint(2, emissionsInterval(new DateInterval (LocalDate.now().minusWeeks(4), LocalDate.now().minusWeeks(3)), database));
                d3 = new DataPoint(3, emissionsInterval(new DateInterval (LocalDate.now().minusWeeks(3), LocalDate.now().minusWeeks(2)), database));
                d4 = new DataPoint(4, emissionsInterval(new DateInterval (LocalDate.now().minusWeeks(2), LocalDate.now().minusWeeks(1)), database));
                d5 = new DataPoint(1, emissionsInterval(new DateInterval (LocalDate.now().minusWeeks(1), LocalDate.now()), database));
            case "Month":
                d1 = new DataPoint(1, emissionsInterval(new DateInterval (LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(4)), database));
                d2 = new DataPoint(2, emissionsInterval(new DateInterval (LocalDate.now().minusMonths(4), LocalDate.now().minusMonths(3)), database));
                d3 = new DataPoint(3, emissionsInterval(new DateInterval (LocalDate.now().minusMonths(3), LocalDate.now().minusMonths(2)), database));
                d4 = new DataPoint(4, emissionsInterval(new DateInterval (LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1)), database));
                d5 = new DataPoint(1, emissionsInterval(new DateInterval (LocalDate.now().minusMonths(1), LocalDate.now()), database));
            case "Year":
                d1 = new DataPoint(1, emissionsInterval(new DateInterval (LocalDate.now().minusYears(5), LocalDate.now().minusYears(4)), database));
                d2 = new DataPoint(2, emissionsInterval(new DateInterval (LocalDate.now().minusYears(4), LocalDate.now().minusYears(3)), database));
                d3 = new DataPoint(3, emissionsInterval(new DateInterval (LocalDate.now().minusYears(3), LocalDate.now().minusYears(2)), database));
                d4 = new DataPoint(4, emissionsInterval(new DateInterval (LocalDate.now().minusYears(2), LocalDate.now().minusYears(1)), database));
                d5 = new DataPoint(1, emissionsInterval(new DateInterval (LocalDate.now().minusYears(1), LocalDate.now()), database));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding
                // each point on our x and y axis.
                d1,
                d2,
                d3,
                d4,
                d5
        });
        return series;
    }
    public double emissionsInterval (DateInterval interval, FirebaseDb database){
        final double[] totalEmissions = {0};
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
                Mass kilos = emissions.total();
                totalEmissions[0] = kilos.kg();
            }
        });
        return totalEmissions[0];
    }
    @Override
    public Map<String, Double> getEmissionsByCategory() {
        // Dummy data, replace with real calculation
        return Map.of(
                "Transportation", 120.0,
                "Energy use", 80.0,
                "Food consumption", 60.0,
                "Shopping/consumption", 40.0
        );
    }
    @Override
    public String getComparisonText(CountryProcessor countryProcessor, FirebaseDb database) {
        double userEmissions = calculateEmissionsForPeriod(timePeriod);
        AtomicReference<Double> nationalAverage = new AtomicReference<>(300.0); // Example value, replace with real data
        double globalAverage = 500.0;  // Example value, replace with real data
        database.fetchUser(result2 -> {
            result2.match(userOption2 -> { // if this operation was successful:
                userOption2.match(// if the user has user info set:
                        user2 -> {
                            Log.d(TAG, "User found");
                            Map<String, String> userJson = (HashMap<String, String>) user2.toJson();
                            String country = userJson.get("country");
                            nationalAverage.set(countryProcessor.getResult(country));
                        },
                        () -> Log.d(TAG, "user nonexistent"));
            }, dbError -> { // if this operation failed:
                Log.d(TAG, "error: " + dbError);
            });
        });
        globalAverage = 4.658219; //global average from the excel file

        if (Objects.equals(timePeriod, "Week")){
            nationalAverage.set(nationalAverage.get()/56);
        }

        if (Objects.equals(timePeriod, "Month")){
            nationalAverage.set(nationalAverage.get()/12);
        }

        return String.format(
                "Your emissions: %.1f kg CO2e\nNational average: %.1f kg CO2e\nGlobal average: %.1f kg CO2e",
                userEmissions, nationalAverage, globalAverage
        );
    }

    @Override
    public void switchScreens(EcoGaugeScreen screen){
        switch (screen){
            case OVERVIEW -> loadFragment(new EcoGaugeOverviewFragment());
            case TRENDS -> loadFragment(new EcoGaugeTrendsFragment());
            case BREAKDOWN -> loadFragment (new EcoGaugeBreakdownFragment());
            case COMPARISON -> loadFragment (new EcoGaugeComparisonFragment());
        }

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}


