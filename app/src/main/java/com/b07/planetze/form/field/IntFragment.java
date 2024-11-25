package com.b07.planetze.form.field;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FieldId;

public final class IntFragment extends FieldFragment<IntField, Integer> {
    @NonNull private static final String TAG = "IntFragment";

    /**
     * Use {@link ChoiceFragment#newInstance} instead of calling this manually.
     */
    public IntFragment() {}

    @NonNull
    public static IntFragment newInstance(@NonNull FieldId<?> field) {
        IntFragment fragment = new IntFragment();
        Bundle args = new Bundle();
        args.putParcelable(FIELD_ID_KEY, field);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void displayMissingField(@NonNull View view) {
        TextView error = view.findViewById(R.id.form_int_error);
        if (error.getText().length() == 0) {
            error.setText(R.string.field_required);
        }
    }

    @Override
    public void initializeField(@NonNull View view,
                                @NonNull Form form,
                                @NonNull FieldId<Integer> id,
                                @NonNull IntField field,
                                @NonNull String fieldName) {
        TextView name = view.findViewById(R.id.form_int_name);
        name.setText(fieldName);

        TextView error = view.findViewById(R.id.form_int_error);

        EditText input = view.findViewById(R.id.form_int_input);
        form.get(id).map(Object::toString).apply(input::setText);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    @NonNull CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(
                    @NonNull CharSequence s, int start, int before, int count) {
                int value;
                try {
                    value = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    error.setText(R.string.int_error);
                    return;
                }
                form.set(id, value)
                        .match(ok -> error.setText(""), error::setText);
            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {}
        });
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_form_int, container, false);
    }
}
