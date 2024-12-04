package com.b07.planetze.ecogauge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.planetze.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class EcoGaugeActivity extends AppCompatActivity implements EcoGaugeScreenSwitch {

    private EcoGaugeViewModel model;

    public static void start(Context context) {
        Intent intent = new Intent(context, EcoGaugeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecogauge);

        model = new ViewModelProvider(this).get(EcoGaugeViewModel.class);

        View container = findViewById(R.id.ecogauge_fragment_container);
        if (container == null) {
            throw new IllegalStateException("Fragment container not found in the layout!");
        } else {
            Log.d("DEBUG", "Fragment container found.");
        }

        if (savedInstanceState == null) {
            switchScreens(EcoGaugeScreen.OVERVIEW);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ecogauge_fragment_container, new EcoGaugeOverviewFragment())
                    .commit();
        }
    }

    @Override
    public void switchScreens(EcoGaugeScreen screen) {
        switch (screen) {
            case OVERVIEW -> loadFragment(new EcoGaugeOverviewFragment());
            case TRENDS -> loadFragment(new EcoGaugeTrendsFragment());
            case BREAKDOWN -> loadFragment (new EcoGaugeBreakdownFragment());
        }

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ecogauge_fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}