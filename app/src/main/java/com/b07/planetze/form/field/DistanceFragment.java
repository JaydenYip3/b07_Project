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
import com.b07.planetze.common.measurement.Distance;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.immutability.ImmutableCopy;

public class DistanceFragment
        extends FieldFragment<DistanceField, ImmutableCopy<Distance>> {
    /**
     * Use {@link DistanceFragment#newInstance} instead of calling this
     * manually.
     */
    public DistanceFragment() {}

    @NonNull
    public static DistanceFragment newInstance(@NonNull FieldId<?> field) {
        DistanceFragment fragment = new DistanceFragment();
        Bundle args = new Bundle();
        args.putParcelable(FIELD_ID_KEY, field);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void displayMissingField(@NonNull View view) {
        TextView error = view.findViewById(R.id.form_distance_error);
        if (error.getText().length() == 0) {
            error.setText(R.string.field_required);
        }
    }

    @Override
    public void initializeField(
            @NonNull View view,
            @NonNull Form form,
            @NonNull FieldId<ImmutableCopy<Distance>> id,
            @NonNull DistanceField field,
            @NonNull String fieldName
    ) {
        TextView name = view.findViewById(R.id.form_distance_name);
        name.setText(fieldName);

        TextView error = view.findViewById(R.id.form_distance_error);

        EditText input = view.findViewById(R.id.form_distance_input);
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
                    error.setText(R.string.distance_error);
                    return;
                }
                form.set(id, new ImmutableCopy<>(Distance.m(value)))
                        .match(ok -> error.setText(""), error::setText);
            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {}
        });
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_distance, container, false);
    }
}