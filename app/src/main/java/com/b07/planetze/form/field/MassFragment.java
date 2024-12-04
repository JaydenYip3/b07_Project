package com.b07.planetze.form.field;

import android.os.Bundle;
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

import androidx.annotation.NonNull;

import com.b07.planetze.R;
import com.b07.planetze.form.FieldFragment;
import com.b07.planetze.util.measurement.ImmutableMass;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FieldId;

public final class MassFragment
        extends FieldFragment<MassField, ImmutableMass> {
    @NonNull private static final String TAG = "MassFragment";

    /**
     * Use {@link MassFragment#newInstance} instead of calling this
     * manually.
     */
    public MassFragment() {}

    @NonNull
    public static MassFragment newInstance(@NonNull FieldId<?> field) {
        MassFragment fragment = new MassFragment();
        Bundle args = new Bundle();
        args.putParcelable(FIELD_ID_KEY, field);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void displayMissingField(@NonNull View view) {
        TextView error = view.findViewById(R.id.form_mass_error);
        if (error.getText().length() == 0) {
            error.setText(R.string.field_required);
        }
    }

    @Override
    public void initializeField(
            @NonNull View view,
            @NonNull Form form,
            @NonNull FieldId<ImmutableMass> id,
            @NonNull MassField field,
            @NonNull String fieldName
    ) {
        TextView name = view.findViewById(R.id.form_mass_name);
        name.setText(fieldName);

        TextView error = view.findViewById(R.id.form_mass_error);

        final Spinner[] unit = {view.findViewById(R.id.form_mass_unit)};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireActivity(),
                R.array.mass_units,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        unit[0].setAdapter(adapter);

        final Mass.Unit[] massUnit = new Mass.Unit[] {Mass.Unit.KG};

        unit[0].setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

                massUnit[0] = switch (item) {
                    case "kg" -> Mass.Unit.KG;
                    case "g" -> Mass.Unit.G;
                    case "lb" -> Mass.Unit.LB;
                    default -> {
                        Log.w(TAG, "Invalid unit received: " + item);
                        yield Mass.Unit.KG;
                    }
                };
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText input = view.findViewById(R.id.form_mass_input);
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
                form.set(id, Mass.withUnit(massUnit[0], value).immutableCopy())
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
        return inflater.inflate(R.layout.fragment_form_mass, container, false);
    }
}
