package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.none;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.database.data.DailySummary;
import com.b07.planetze.ecotracker.exception.EcoTrackerException;
import com.b07.planetze.util.option.Option;

public class DailySummaryFragment extends Fragment {
    @NonNull private static final String TAG = "DailySummaryFragment";
    @NonNull private static final String ARG_DAILY_SUMMARY = "daily_summary";
    @NonNull private static final String ARG_PROPORTION = "proportion";

    @NonNull private Option<DailySummary> dailySummary;
    private double emissionsPercentage;

    private DailySummaryFragment() {
        dailySummary = none();
        emissionsPercentage = 0;
    }

    public static DailySummaryFragment newInstance(DailySummary summary,
                                                   double emissionsProportion) {
        DailySummaryFragment fragment = new DailySummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DAILY_SUMMARY, summary);
        args.putDouble(ARG_PROPORTION, emissionsProportion);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DailySummary summary = dailySummary.getOrThrow(
                new EcoTrackerException("Daily summary not provided"));

        EcoTrackerViewModel model = new ViewModelProvider(requireActivity())
                .get(EcoTrackerViewModel.class);

        TextView name = view.findViewById(R.id.ecotracker_dailysummary_name);
        name.setText(summary.type().displayName());

        TextView category = view.findViewById(
                R.id.ecotracker_dailysummary_category);
        category.setText(summary.type().category().displayName());

        TextView emissions = view.findViewById(
                R.id.ecotracker_dailysummary_emissions);
        emissions.setText(summary.emissions().total().format());

        ConstraintLayout layout = view.findViewById(
                R.id.ecotracker_dailysummary_layout);
        layout.setClipToOutline(true);

        layout.setOnClickListener(v -> {
            model.editDaily(summary.id());
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = Option.mapNull(getArguments())
                .getOrThrow(new EcoTrackerException("No arguments provided"));

        dailySummary = Option.mapNull(
                args.getParcelable(ARG_DAILY_SUMMARY, DailySummary.class));

        emissionsPercentage = args.getDouble(ARG_PROPORTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.fragment_ecotracker_dailysummary, container, false);
    }
}