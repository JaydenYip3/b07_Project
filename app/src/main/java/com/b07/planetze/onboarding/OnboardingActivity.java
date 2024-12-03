package com.b07.planetze.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

        model.getScreen().observe(this, this::setScreen);
    }

    private void setScreen(OnboardingScreen screen) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = switch (screen) {
            case EXPLANATION -> new OnboardingExplanationFragment();
            case TRANSPORTATION -> new QuestionsTransportationFragment();
            case HOUSING -> new QuestionsHousingFragment();
            case FOOD -> new QuestionsFoodFragment();
            case CONSUMPTION -> new QuestionsConsumptionFragment();
            case CALC_DISPLAY -> new CalcDisplayFragment();
        };
        transaction.replace(R.id.onboarding_fragment_container, fragment);
        if (screen != OnboardingScreen.EXPLANATION) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }
}
