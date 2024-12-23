package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.none;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.ecotracker.exception.EcoTrackerException;
import com.b07.planetze.util.option.Option;

public final class DailyFetchFragment extends Fragment {
    @NonNull private static final String TAG = "DailyFetchFragment";
    @NonNull private static final String ARG_DAILY_FETCH = "daily_fetch";
    @NonNull private static final String ARG_PROPORTION = "proportion";
    @NonNull private static final String ARG_SHOW_PROPORTION_BAR = "show_bar";

    @NonNull private Option<DailyFetch> dailyFetch;
    private double emissionsProportion;
    private boolean showProportionBar;

    private DailyFetchFragment() {
        dailyFetch = none();
        emissionsProportion = 0;
        showProportionBar = false;
    }

    @NonNull
    public static DailyFetchFragment newInstance(
            @NonNull DailyFetch fetch,
            double emissionsProportion,
            boolean showProportionBar
    ) {
        DailyFetchFragment fragment = new DailyFetchFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DAILY_FETCH, fetch);
        args.putDouble(ARG_PROPORTION, emissionsProportion);
        args.putBoolean(ARG_SHOW_PROPORTION_BAR, showProportionBar);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DailyFetch fetch = dailyFetch.getOrThrow(
                new EcoTrackerException(ARG_DAILY_FETCH + " not provided"));

        EcoTrackerViewModel model = new ViewModelProvider(requireActivity())
                .get(EcoTrackerViewModel.class);

        TextView name = view.findViewById(R.id.ecotracker_dailyfetch_name);
        name.setText(fetch.type().displayName());

        TextView category = view.findViewById(
                R.id.ecotracker_dailyfetch_summary);
        category.setText(fetch.summary());

        TextView emissions = view.findViewById(
                R.id.ecotracker_dailyfetch_emissions);
        emissions.setText(fetch.emissions().total().format());

        ConstraintLayout emissionsBg = view.findViewById(
                R.id.ecotracker_dailyfetch_emissions_bg);
        ConstraintSet set = new ConstraintSet();
        set.clone(emissionsBg);
        set.constrainPercentWidth(
                R.id.ecotracker_dailyfetch_emissions_proportion,
                (float) (showProportionBar ? emissionsProportion : 0)
        );
        set.applyTo(emissionsBg);

        ConstraintLayout layout = view.findViewById(
                R.id.ecotracker_dailyfetch_layout);
        layout.setClipToOutline(true);


        layout.setOnClickListener(v -> {
            model.newForm(new FormAction.Edit(fetch));
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = Option.mapNull(getArguments())
                .getOrThrow(new EcoTrackerException("No arguments provided"));

        dailyFetch = Option.mapNull(
                args.getParcelable(ARG_DAILY_FETCH, DailyFetch.class));

        emissionsProportion = args.getDouble(ARG_PROPORTION);

        showProportionBar = args.getBoolean(ARG_SHOW_PROPORTION_BAR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.fragment_ecotracker_dailyfetch, container, false);
    }
}