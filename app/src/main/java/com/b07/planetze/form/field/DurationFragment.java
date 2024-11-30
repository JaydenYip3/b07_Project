package com.b07.planetze.form.field;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.form.FieldFragment;
import com.b07.planetze.util.measurement.Duration;
import com.b07.planetze.util.measurement.ImmutableDuration;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FieldId;

import java.util.Locale;

public final class DurationFragment
        extends FieldFragment<DurationField, ImmutableDuration> {
    /**
     * Use {@link DurationFragment#newInstance} instead of calling this
     * manually.
     */
    public DurationFragment() {}

    @NonNull
    public static DurationFragment newInstance(@NonNull FieldId<?> field) {
        DurationFragment fragment = new DurationFragment();
        Bundle args = new Bundle();
        args.putParcelable(FIELD_ID_KEY, field);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void displayMissingField(@NonNull View view) {
        TextView error = view.findViewById(R.id.form_duration_error);
        if (error.getText().length() == 0) {
            error.setText(R.string.field_required);
        }
    }

    @NonNull
    private void set(@NonNull FieldId<ImmutableDuration> id,
                     @NonNull Form form,
                     @NonNull View view,
                     @NonNull String hours,
                     @NonNull String minutes) {
        TextView errorText = view.findViewById(R.id.form_duration_error);
        if (hours.isEmpty()) {
            hours = "0";
        }
        if (minutes.isEmpty()) {
            minutes = "0";
        }

        Duration duration;
        int m;
        try {
            duration = Duration.h(Double.parseDouble(hours));
            m = Integer.parseInt(minutes);
        } catch (NumberFormatException e) {
            errorText.setText("Invalid duration");
            return;
        }
        if (m >= 60) {
            errorText.setText("Invalid minutes");
            return;
        }
        duration.add(Duration.mins(m));

        form.set(id, duration.immutableCopy())
                .match(ok -> errorText.setText(""), errorText::setText);
    }

    @Override
    public void initializeField(
            @NonNull View view,
            @NonNull Form form,
            @NonNull FieldId<ImmutableDuration> id,
            @NonNull DurationField field,
            @NonNull String fieldName
    ) {
        TextView name = view.findViewById(R.id.form_duration_name);
        name.setText(fieldName);

        EditText hours = view.findViewById(R.id.form_duration_hours);
        EditText minutes = view.findViewById(R.id.form_duration_minutes);
        form.get(id).apply(d -> {
            long s = (long)d.s();
            long m = (s / 60) % 60;
            long h = s / 3600;
            hours.setText(String.valueOf(h));
            minutes.setText(String.valueOf(m));
        });

        final String[] components = {
                hours.getText().toString(),
                minutes.getText().toString()
        };

        hours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    @NonNull CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(
                    @NonNull CharSequence s, int start, int before, int count) {
                components[0] = s.toString();
                set(id, form, view, components[0], components[1]);
            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {}
        });

        minutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    @NonNull CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(
                    @NonNull CharSequence s, int start, int before, int count) {
                components[1] = s.toString();
                set(id, form, view, components[0], components[1]);
            }

            @Override
            public void afterTextChanged(Editable s) {
                
            }
        });
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_duration, container, false);
    }
}