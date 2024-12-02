package com.b07.planetze.EcoGauge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.b07.planetze.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Map;

public class EcoGaugeBreakdownFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_gauge_breakdown, container, false);

        // Reference PieChart from layout
        PieChart pieChart = view.findViewById(R.id.pieChart);

        // Get breakdown data
        Map<String, Double> categoryData = ((EcoGaugeBreakdownCallback) requireActivity()).getEmissionsByCategory();

        // Convert data to PieEntries
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categoryData.entrySet()) {
            entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }

        // Create PieDataSet and PieData
        PieDataSet dataSet = new PieDataSet(entries, "Emissions Breakdown");
        dataSet.setColors(new int[] { R.color.colorTransport, R.color.colorEnergy, R.color.colorFood, R.color.colorShopping }, getContext());
        PieData data = new PieData(dataSet);

        // Set data to PieChart
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh chart

        return view;
    }
}
