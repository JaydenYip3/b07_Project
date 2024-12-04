package com.b07.planetze.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import com.b07.planetze.R;
import com.b07.planetze.auth.AuthActivityInitException;
import com.b07.planetze.auth.AuthScreen;
import com.b07.planetze.ecotracker.EcoTrackerActivity;
import com.b07.planetze.ecotracker.EcoTrackerViewModel;
import com.b07.planetze.onboarding.OnboardingActivity;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private static final String EXTRA_GO_TO_TRACKER = "com.b07.planetze.HOME_GO_TO_TRACKER";

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean toTracker) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(EXTRA_GO_TO_TRACKER, toTracker);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        boolean toTracker = false;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            toTracker = extras.getBoolean(EXTRA_GO_TO_TRACKER);
        }

        var model = new ViewModelProvider(this).get(HomeViewModel.class);
        model.fetchAll();

        model.getHasCompletedOnboarding().observe(this, hasOnboarded -> {
            if (!hasOnboarded) {
                OnboardingActivity.start(this);
            }
        });

        loadFragment(new HomeScreenFragment());

        if (toTracker) {
            EcoTrackerActivity.start(this);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.commit();
    }
}
