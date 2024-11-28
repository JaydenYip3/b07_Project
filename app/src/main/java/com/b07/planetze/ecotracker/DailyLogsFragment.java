package com.b07.planetze.ecotracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.util.measurement.Mass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public final class DailyLogsFragment extends Fragment {
    @NonNull private static final String TAG = "DailyLogsFragment";

    private DailyLogsFragment() {}

    public static DailyLogsFragment newInstance() {
        return new DailyLogsFragment();
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EcoTrackerViewModel model = new ViewModelProvider(requireActivity())
                .get(EcoTrackerViewModel.class);

        FloatingActionButton button = view.findViewById(
                R.id.ecotracker_dailylogs_add);
        button.setClipToOutline(true);

        TextView totalEmissions = view.findViewById(
                R.id.ecotracker_dailylogs_totalemissions);

        List<Fragment> summaries = new ArrayList<>();

        model.getDailies().observe(getViewLifecycleOwner(), list -> {
            Mass total = list.emissions().total();
            totalEmissions.setText(total.format() + " CO2e");

            FragmentManager mgr = requireActivity().getSupportFragmentManager();
            FragmentTransaction ft = mgr.beginTransaction();

            summaries.forEach(ft::remove);
            summaries.clear();

            list.orderBy(DailyFetch.descendingEmissions).forEach(fetch -> {
                Mass emissions = fetch.emissions().total();
                double proportion = total.isZero()
                        ? 0 : emissions.kg() / total.kg();

                Fragment f = DailyFetchFragment.newInstance(
                        fetch, proportion);

                ft.add(R.id.ecotracker_dailylogs_dailysummaries, f);
                summaries.add(f);
            });

            ft.commit();
        });

        model.fetchDailies();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ecotracker_dailylogs,
                container, false);
    }
}