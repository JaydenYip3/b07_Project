package com.b07.planetze.ecogauge;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.util.result.Ok;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EcoGaugeTrendsFragment extends Fragment{
    private GraphView graphView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_eco_gauge_trends, container, false);

        EcoGaugeViewModel model = new ViewModelProvider(requireActivity()).get(EcoGaugeViewModel.class);

        // on below line we are initializing our graph view.
        graphView = view.findViewById(R.id.idGraphView);

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        graphView.setTitle("Emissions Trend Graph");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        graphView.getGridLabelRenderer().setVerticalAxisTitle("Emissions (kg CO2e)");

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(40);

        graphView.getGridLabelRenderer().setTextSize(20f);
        graphView.getGridLabelRenderer().reloadStyles();

        model.getInterval().observe(requireActivity(), timePeriod -> {
            timePeriod = timePeriod.toLowerCase();
            FirebaseDb database = new FirebaseDb();
            List<DataPoint> dataPoints = new ArrayList<>();

            // Define intervals based on the time period
            List<DateInterval> intervals = new ArrayList<>();
            switch (timePeriod) {
                case "week":
                    for (int i = 5; i > 0; i--) {
                        intervals.add(DateInterval.between(LocalDate.now().minusWeeks(i), LocalDate.now().minusWeeks(i - 1).plusDays(1)));
                    }
                    graphView.getGridLabelRenderer().setHorizontalAxisTitle("Weeks");
                    break;
                case "month":
                    for (int i = 5; i > 0; i--) {
                        intervals.add(DateInterval.between(LocalDate.now().minusMonths(i), LocalDate.now().minusMonths(i - 1).plusDays(1)));
                    }
                    graphView.getGridLabelRenderer().setHorizontalAxisTitle("Months");
                    break;
                case "year":
                    for (int i = 5; i > 0; i--) {
                        intervals.add(DateInterval.between(LocalDate.now().minusYears(i), LocalDate.now().minusYears(i - 1).plusDays(1)));
                    }
                    graphView.getGridLabelRenderer().setHorizontalAxisTitle("Years");
                    break;
            }

            ImageButton btnPrevious = view.findViewById(R.id.ecogauge_back);
            btnPrevious.setOnClickListener(v -> {
                ((EcoGaugeScreenSwitch) requireActivity()).switchScreens(EcoGaugeScreen.OVERVIEW);
            });


            // Fetch emissions for each interval
            for (int i = 0; i < intervals.size(); i++) {
                DateInterval interval = intervals.get(i);
                final int index = i + 1;
                database.fetchDailies(interval, result -> {
                    if (result instanceof Ok<DailyFetchList, ?> r) {
                        DailyFetchList list = r.get();
                        Emissions emissions = list.emissions();
                        Mass kilos = emissions.total();
                        dataPoints.add(new DataPoint(index, kilos.kg()));

                        // Check if all data points are ready
                        if (dataPoints.size() == intervals.size()) {
                            // Create series and pass it to the callback
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints.toArray(new DataPoint[0]));
                            // on below line we are adding
                            // data series to our graph view.
                            graphView.addSeries(series);
                        }
                    } else {
                        // Handle errors if needed
                        Log.e(TAG, "Error fetching data for interval: " + interval);
                    }
                });
            }

        });

        return view;
    }
}