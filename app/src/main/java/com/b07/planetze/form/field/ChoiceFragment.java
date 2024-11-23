package com.b07.planetze.form.field;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormViewModel;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.exception.FormException;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

public final class ChoiceFragment extends FieldFragment<ChoiceField, Integer> {
    @NonNull private static final String TAG = "ChoiceFragment";

    /**
     * Use {@link ChoiceFragment#newInstance} instead of calling this manually.
     */
    public ChoiceFragment() {}

    @NonNull
    public static ChoiceFragment newInstance(@NonNull FieldId<?> field) {
        ChoiceFragment fragment = new ChoiceFragment();
        Bundle args = new Bundle();
        args.putParcelable(FIELD_ID_KEY, field);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initializeField(@NonNull View view,
                                @NonNull Form form,
                                @NonNull FieldId<Integer> id,
                                @NonNull ChoiceField field,
                                @NonNull String fieldName) {
        TextView name = view.findViewById(R.id.choiceFieldName);
        name.setText(fieldName);

        RadioGroup group = view.findViewById(R.id.choiceFieldGroup);
        group.removeAllViews();

        Util.enumerate(field.choices()).forEach((i, choice) -> {
            RadioButton button = new RadioButton(group.getContext());
            button.setText(choice);
            button.setId(i);

            if (form.get(id).isSomeAnd(i::equals)) {
                button.toggle();
            }
            group.addView(button);
        });

        TextView error = view.findViewById(R.id.choiceFieldError);
        group.setOnCheckedChangeListener((radioGroup, checkedId) ->
                form.set(id, checkedId).applyError(error::setText));
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_form_choice, container, false);
    }
}
