package com.b07.planetze.form.field;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.b07.planetze.R;
import com.b07.planetze.form.FieldFragment;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.Util;

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
    protected void displayMissingField(@NonNull View view) {
        TextView error = view.findViewById(R.id.form_choice_error);
        if (error.getText().length() == 0) {
            error.setText(R.string.field_required);
        }
    }

    @Override
    public void initializeField(@NonNull View view,
                                @NonNull Form form,
                                @NonNull FieldId<Integer> id,
                                @NonNull ChoiceField field,
                                @NonNull String fieldName) {
        TextView name = view.findViewById(R.id.form_choice_name);
        name.setText(fieldName);

        RadioGroup group = view.findViewById(R.id.form_choice_group);
        group.removeAllViews();

        ColorStateList colorStateList = new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_enabled},
                        new int[] {android.R.attr.state_enabled}
                },
                new int[] {
                        0xff1b1b1e,
                        0xff1b1b1e,
                }
        );

        Util.enumerate(field.choices()).forEach((i, choice) -> {
            RadioButton button = new RadioButton(group.getContext());
            button.setText(choice);
            button.setId(i);
            button.setTextSize(16);
            button.setTextColor(0xff1b1b1e);
            button.setButtonTintList(colorStateList);

            if (form.get(id).isSomeAnd(i::equals)) {
                button.toggle();
            }
            group.addView(button);
        });

        TextView error = view.findViewById(R.id.form_choice_error);
        group.setOnCheckedChangeListener(
                (radioGroup, checkedId) -> error.setText(
                        form.set(id, checkedId).resolve(ok -> "", e -> e)));
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
