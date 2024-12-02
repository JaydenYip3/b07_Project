package com.b07.planetze.EcoGauge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.b07.planetze.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EcoGaugeTrendsFragment extends Fragment{
    GraphView graphView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_eco_gauge_trends, container, false);

        // on below line we are initializing our graph view.
        graphView = view.findViewById(R.id.idGraphView);

        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = ((EcoGaugeTrendsCallback) requireActivity()).getDataPoints();

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        graphView.setTitle("Emissions Trend Graph");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(18);

        // on below line we are adding
        // data series to our graph view.
        graphView.addSeries(series);

        return view;
    }
}
