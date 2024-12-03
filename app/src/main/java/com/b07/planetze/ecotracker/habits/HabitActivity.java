package com.b07.planetze.ecotracker.habits;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import com.b07.planetze.EcoGauge.EcoGaugeScreen;
import com.b07.planetze.R;
import com.b07.planetze.ecotracker.EcoTrackerViewModel;
import com.b07.planetze.home.HomeScreenFragment;
import com.b07.planetze.home.HomeViewModel;
import com.google.firebase.database.FirebaseDatabase;

public class HabitActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, com.b07.planetze.ecotracker.habits.HabitActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        View container = findViewById(R.id.fragment_container);
        if (container == null) {
            throw new IllegalStateException("Fragment container not found in the layout!");
        } else {
            Log.d("DEBUG", "Fragment container found.");
        }

        if (savedInstanceState == null) {
            loadFragment(new HabitSuggestionsFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
