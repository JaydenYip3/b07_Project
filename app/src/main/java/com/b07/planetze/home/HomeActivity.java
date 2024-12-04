package com.b07.planetze.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import com.b07.planetze.R;
import com.b07.planetze.ecotracker.EcoTrackerViewModel;
import com.b07.planetze.onboarding.OnboardingActivity;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        var model = new ViewModelProvider(this).get(HomeViewModel.class);
        model.fetchAll();

        model.getHasCompletedOnboarding().observe(this, hasOnboarded -> {
            if (!hasOnboarded) {
                OnboardingActivity.start(this);
            }
        });

        loadFragment(new HomeScreenFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.commit();
    }
}
