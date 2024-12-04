package com.b07.planetze.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.EcoGauge.EcoGaugeActivity;
import com.b07.planetze.EcoGauge.EcoGaugeScreen;
import com.b07.planetze.EcoGauge.EcoGaugeScreenSwitch;
import com.b07.planetze.MainActivity;
import com.b07.planetze.R;
import com.b07.planetze.common.User;
import com.b07.planetze.ecotracker.EcoTrackerActivity;
import com.b07.planetze.ecotracker.habits.HabitActivity;
import com.b07.planetze.onboarding.OnboardingActivity;

public class HomeScreenFragment extends Fragment {

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeViewModel model = new ViewModelProvider(requireActivity())
                .get(HomeViewModel.class);

        Button signOutButton = view.findViewById(R.id.signOutButton);
        TextView username = view.findViewById(R.id.home_username);
        TextView emissions = view.findViewById(R.id.home_emissions);

        signOutButton.setOnClickListener(v -> {
            UserSessionManager.signOut(view.getContext());
        });

        model.getUser().observe(requireActivity(), maybeUser -> {
            maybeUser.map(User::name).apply(username::setText);
        });
        model.getDailies().observe(requireActivity(), list -> {
            emissions.setText(list.emissions().total().format() + " CO2e");
        });

        View back = view.findViewById(R.id.home_back);
        View toTracker = view.findViewById(R.id.home_to_tracker);
        View toHabits = view.findViewById(R.id.home_to_habits);
        View toGauge = view.findViewById(R.id.home_to_gauge);
        View toFootprint = view.findViewById(R.id.home_to_footprint);
        View toHub = view.findViewById(R.id.home_to_hub);

        back.setOnClickListener(v -> {
            MainActivity.start(requireActivity());
        });
        toTracker.setOnClickListener(v -> {
            EcoTrackerActivity.start(requireActivity());
        });
        toGauge.setOnClickListener(v -> {
            EcoGaugeActivity.start(requireActivity());
        });
        toFootprint.setOnClickListener(v -> {
            OnboardingActivity.start(requireActivity());
        });
        toHabits.setOnClickListener(v -> {
            HabitActivity.start(requireActivity());
        });
        toHub.setOnClickListener(v -> {
            loadFragment(new NotFinishedFragment());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void loadFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
