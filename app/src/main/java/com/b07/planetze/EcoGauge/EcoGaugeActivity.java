package com.b07.planetze.EcoGauge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.planetze.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EcoGaugeActivity extends AppCompatActivity implements EcoGaugeOverviewCallback, EcoGaugeScreenSwitch{

    public static void start(Context context) {
        Intent intent = new Intent(context, EcoGaugeActivity.class);
        context.startActivity(intent);
    }

    String timePeriod;
    boolean intialized = false;

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