package com.b07.planetze.form.field;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.util.measurement.Duration;
import com.b07.planetze.util.measurement.ImmutableDuration;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FieldId;

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

        TextView error = view.findViewById(R.id.form_duration_error);

        EditText input = view.findViewById(R.id.form_duration_input);
        form.get(id).map(Object::toString).apply(input::setText);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    @NonNull CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(
                    @NonNull CharSequence s, int start, int before, int count) {
                double value;
                try {
                    value = Double.parseDouble(s.toString());
                } catch (NumberFormatException e) {
                    error.setText(R.string.mass_error);
                    return;
                }
                form.set(id, Duration.h(value).immutableCopy())
                        .match(ok -> error.setText(""), error::setText);
            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {}
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