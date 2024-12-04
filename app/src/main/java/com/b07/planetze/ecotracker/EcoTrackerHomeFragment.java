package com.b07.planetze.ecotracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.ecotracker.habits.HabitSuggestionsFragment;

public final class EcoTrackerHomeFragment extends Fragment {
    private EcoTrackerHomeFragment() {}

    @NonNull
    public static EcoTrackerHomeFragment newInstance() {
        return new EcoTrackerHomeFragment();
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EcoTrackerViewModel model = new ViewModelProvider(requireActivity())
                .get(EcoTrackerViewModel.class);

        View toActivityLog = view.findViewById(
                R.id.ecotracker_home_activitylog);
        toActivityLog.setOnClickListener(v -> {
            model.toActivityLog();
        });

        View toHabitSuggestions = view.findViewById(
                R.id.ecotracker_home_habit_suggestions);
        toHabitSuggestions.setOnClickListener(v -> {
            loadFragment(new HabitSuggestionsFragment());
        });

        ImageButton previousPage = view.findViewById(R.id.ecogauge_back);
        TextView daily_emission = view.findViewById(R.id.daily_emissions);

        //TODO daily_emission.setText(db.);
        previousPage.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_ecotracker_home, container, false);
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.ecotracker_fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}
