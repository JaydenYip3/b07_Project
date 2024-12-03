package com.b07.planetze.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.auth.AuthActivity;
import com.b07.planetze.ecotracker.EcoTrackerViewModel;

public class OnboardingActivity extends AppCompatActivity {
    @NonNull private static final String TAG = "OnboardingActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, OnboardingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        var model = new ViewModelProvider(this).get(OnboardingViewModel.class);

        model.getScreen().observe(this, maybeScreen -> {
            Log.d(TAG, "switch screens: " + maybeScreen);
            maybeScreen.match(screen -> {
                loadFragment(switch (screen) {
                    case TRANSPORTATION -> new QuestionsTransportationFragment();
                    case HOUSING -> new QuestionsHousingFragment();
                    case FOOD -> new QuestionsFoodFragment();
                    case CONSUMPTION -> new QuestionsConsumptionFragment();
                    case CALC_DISPLAY -> new CalcDisplayFragment();
                });
            }, () -> {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.onboarding_fragment_container, new QuestionsTransportationFragment())
                        .commit();
            });
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.onboarding_fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}
