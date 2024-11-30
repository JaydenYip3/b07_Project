package com.b07.planetze.ecotracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;

import java.time.Year;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static EcoTrackerViewModel model;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        model = new ViewModelProvider(requireActivity())
                .get(EcoTrackerViewModel.class);

        return new DatePickerDialog(requireContext(),
                R.style.Theme_Planetze_DatePicker, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        model.setDate(Year.of(year).atMonth(month).atDay(day));
    }
}
