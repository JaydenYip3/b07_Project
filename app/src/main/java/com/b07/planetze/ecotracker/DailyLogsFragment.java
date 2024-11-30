package com.b07.planetze.ecotracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.measurement.Mass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DailyLogsFragment extends Fragment {
    @NonNull private static final String TAG = "DailyLogsFragment";

    @NonNull private final List<Fragment> summaries;

    private DailyLogsFragment() {
        summaries = new ArrayList<>();
    }

    public static DailyLogsFragment newInstance() {
        return new DailyLogsFragment();
    }

    private void openTypeSelector(@NonNull View view) {
        FrameLayout typeLayout = view.findViewById(
                R.id.ecotracker_dailylogs_type_layout);
        View typeExit = view.findViewById(R.id.ecotracker_dailylogs_type_exit);

        typeLayout.post(() -> {
            typeLayout.setTranslationY(typeLayout.getHeight());

            typeExit.setVisibility(View.VISIBLE);
            typeLayout.setVisibility(View.VISIBLE);

            ObjectAnimator animation = ObjectAnimator.ofFloat(
                    typeLayout, "translationY", 0f);
            animation.setDuration(200);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        });
    }

    private void closeTypeSelector(@NonNull View view) {
        View typeExit = view.findViewById(R.id.ecotracker_dailylogs_type_exit);
        FrameLayout typeLayout = view.findViewById(
                R.id.ecotracker_dailylogs_type_layout);

        typeExit.setVisibility(View.INVISIBLE);

        typeLayout.post(() -> {
            typeLayout.setTranslationY(0);

            ObjectAnimator animation = ObjectAnimator.ofFloat(
                    typeLayout, "translationY", typeLayout.getHeight());
            animation.setDuration(200);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
            animation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    typeLayout.setVisibility(View.INVISIBLE);
                }
            });
        });
    }

    private void updateSummaries(@NonNull View view,
                                 @NonNull DailyFetchList list) {
        TextView totalEmissions = view.findViewById(
                R.id.ecotracker_dailylogs_totalemissions);

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
                    fetch, proportion, list.size() > 1);

            ft.add(R.id.ecotracker_dailylogs_dailysummaries, f);
            summaries.add(f);
        });

        ft.commit();
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EcoTrackerViewModel model = new ViewModelProvider(requireActivity())
                .get(EcoTrackerViewModel.class);

        View typeExit = view.findViewById(R.id.ecotracker_dailylogs_type_exit);
        Button typeClose = view.findViewById(
                R.id.ecotracker_dailylogs_type_close);
        FrameLayout typeLayout = view.findViewById(
                R.id.ecotracker_dailylogs_type_layout);
        FloatingActionButton add = view.findViewById(
                R.id.ecotracker_dailylogs_add);

        Button date = view.findViewById(R.id.ecotracker_dailylogs_date);
        date.setOnClickListener(v -> {
            new DatePickerFragment().show(
                    requireActivity().getSupportFragmentManager(), "dateFrag");
        });

        typeExit.setOnClickListener(v -> closeTypeSelector(view));
        typeClose.setOnClickListener(v -> closeTypeSelector(view));

        add.setOnClickListener(v -> openTypeSelector(view));

        model.getDate().observe(getViewLifecycleOwner(), d -> {
            var pattern = DateTimeFormatter.ofPattern("EEE LLL dd, uuuu");
            date.setText(d.format(pattern));
        });

        model.getDailies().observe(getViewLifecycleOwner(), list -> {
            updateSummaries(view, list);
        });

        List<Integer> ids = Arrays.asList(
                R.id.ecotracker_dailylogs_type_driving,
                R.id.ecotracker_dailylogs_type_publictransit,
                R.id.ecotracker_dailylogs_type_cyclingorwalking,
                R.id.ecotracker_dailylogs_type_flight,
                R.id.ecotracker_dailylogs_type_buyclothes,
                R.id.ecotracker_dailylogs_type_buyelectronics,
                R.id.ecotracker_dailylogs_type_buyother,
                R.id.ecotracker_dailylogs_type_meal,
                R.id.ecotracker_dailylogs_type_energybills
        );
        List<DailyType> types = Arrays.asList(
                DailyType.DRIVING,
                DailyType.PUBLIC_TRANSIT,
                DailyType.CYCLING_OR_WALKING,
                DailyType.FLIGHT,
                DailyType.BUY_CLOTHES,
                DailyType.BUY_ELECTRONICS,
                DailyType.BUY_OTHER,
                DailyType.MEAL,
                DailyType.ENERGY_BILLS
        );

        Util.zip(ids, types).forEach((id, type) -> {
            TextView text = view.findViewById(id);
            text.setOnClickListener(v -> {
                closeTypeSelector(view);
                model.newForm(new FormAction.New(type));
            });
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