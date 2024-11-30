package com.b07.planetze.form.field;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.form.FieldFragment;
import com.b07.planetze.util.measurement.Distance;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.measurement.ImmutableDuration;
import com.b07.planetze.util.measurement.Mass;

public final class DistanceFragment
        extends FieldFragment<DistanceField, ImmutableDistance> {
    @NonNull private static final String TAG = "DistanceFragment";

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

    private void set(@NonNull FieldId<ImmutableDistance> id,
                     @NonNull Form form,
                     @NonNull View view,
                     @NonNull String s,
                     @NonNull Distance.Unit unit) {

        TextView errorText = view.findViewById(R.id.form_distance_error);

        double value;
        try {
            value = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            errorText.setText(R.string.distance_error);
            return;
        }
        form.set(id, Distance.withUnit(unit, value).immutableCopy())
                .match(ok -> errorText.setText(""), errorText::setText);
    }

    @Override
    public void initializeField(
            @NonNull View view,
            @NonNull Form form,
            @NonNull FieldId<ImmutableDistance> id,
            @NonNull DistanceField field,
            @NonNull String fieldName
    ) {
        TextView name = view.findViewById(R.id.form_distance_name);
        name.setText(fieldName);

        EditText input = view.findViewById(R.id.form_distance_input);
        form.get(id)
                .map(ImmutableDistance::km)
                .map(Object::toString)
                .apply(input::setText);

        final Spinner[] unit = {view.findViewById(R.id.form_distance_unit)};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireActivity(),
                R.array.distance_units,
                R.layout.spinner_form_distance
        );
        adapter.setDropDownViewResource(R.layout.spinner_form_distance);
        unit[0].setAdapter(adapter);

        final Distance.Unit[] distanceUnit
                = new Distance.Unit[] {Distance.Unit.KM};

        unit[0].setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent, View v, int position, long vid) {
                String item = (String) parent.getItemAtPosition(position);

                distanceUnit[0] = switch (position) {
                    case 0 -> Distance.Unit.KM;
                    case 1 -> Distance.Unit.M;
                    case 2 -> Distance.Unit.MI;
                    default -> {
                        Log.w(TAG, "Invalid unit received: " + item);
                        yield Distance.Unit.KM;
                    }
                };

                set(id, form, view, input.getText().toString(), distanceUnit[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    @NonNull CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(
                    @NonNull CharSequence s, int start, int before, int count) {
                set(id, form, view, s.toString(), distanceUnit[0]);
            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {}
        });
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.fragment_form_distance, container, false);
    }
}