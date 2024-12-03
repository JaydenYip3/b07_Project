package com.b07.planetze.EcoGauge;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import com.b07.planetze.R;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.ecotracker.EcoTrackerActivity;
import com.b07.planetze.home.HomeScreenFragment;
import com.b07.planetze.home.HomeViewModel;
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
import androidx.lifecycle.ViewModelProvider;
import com.b07.planetze.EcoGauge.EcoGaugeOverviewFragment;

public class EcoGaugeActivity extends AppCompatActivity implements EcoGaugeOverviewCallback, EcoGaugeScreenSwitch{

    public static void start(Context context) {
        Intent intent = new Intent(context, EcoGaugeActivity.class);
        context.startActivity(intent);
    }

    String timePeriod;
    boolean intialized = false;

    double generalEmissions;
    @Override
    public String getPeriod(String timePeriod) {
        if (!intialized){
            this.timePeriod = timePeriod;
            intialized = true;
        }
        return this.timePeriod;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecogauge);

        View container = findViewById(R.id.fragment_container);
        if (container == null) {
            throw new IllegalStateException("Fragment container not found in the layout!");
        } else {
            Log.d("DEBUG", "Fragment container found.");
        }

        if (savedInstanceState == null) {
            switchScreens(EcoGaugeScreen.OVERVIEW);
        }
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
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}