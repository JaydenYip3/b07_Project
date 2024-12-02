package com.b07.planetze.ecotracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private EcoTrackerViewModel model;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity())
                .get(EcoTrackerViewModel.class);
        LocalDate date = model.getDateValue();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, date.getYear());
        c.set(Calendar.MONTH, date.getMonthValue() - 1);
        c.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());

        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        var d = new DatePickerDialog(
                requireContext(),
                R.style.Theme_Planetze_DatePicker,
                this::onDateSet,
                cYear,
                cMonth,
                cDay
        );

        d.getDatePicker().init(cYear, cMonth, cDay, this::onDateSet);
        return d;
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        LocalDate date = LocalDateTime.ofInstant(
                c.toInstant(), c.getTimeZone().toZoneId()).toLocalDate();

        model.setDate(date);

        dismiss();
    }
}
