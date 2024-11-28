package com.b07.planetze.EcoGauge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.b07.planetze.R;

public class EcoGaugeComparisonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_gauge_comparison, container, false);

        // Reference TextView from layout
        TextView comparisonTextView = view.findViewById(R.id.comparisonTextView);

        // Get comparison data
        String comparisonText = ((EcoGaugeComparisonCallback) requireActivity()).getComparisonText();

        // Update the TextView with comparison data
        comparisonTextView.setText(comparisonText);

        return view;
    }
}
