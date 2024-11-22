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

public class ChoiceFragment extends Fragment {
    @NonNull private static final String FIELD_ID_KEY = "field";
    @NonNull private static final String TAG = "ChoiceFragment";

    private FieldId<Integer> fieldId;

    /**
     * Use {@link ChoiceFragment#newInstance} instead of calling this manually.
     */
    public ChoiceFragment() {}

    public static ChoiceFragment newInstance(@NonNull FieldId<?> field) {
        ChoiceFragment fragment = new ChoiceFragment();
        Bundle args = new Bundle();
        args.putParcelable(FIELD_ID_KEY, field);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = Option.mapNull(getArguments())
                .getOrThrow(new FormException("No arguments provided"));

        @SuppressWarnings("unchecked")
        FieldId<Integer> f = Option
                .mapNull(args.getParcelable(FIELD_ID_KEY, FieldId.class))
                .getOrThrow(new FormException("Invalid arguments"));

        fieldId = f;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FormViewModel model = new ViewModelProvider(requireActivity())
                .get(FormViewModel.class);

        model.getForm().observe(getViewLifecycleOwner(), formOption -> {
            if (!(formOption instanceof Some<Form> some)) {
                return;
            }
            Form form = some.get();
            FormDefinition def = form.definition();

            if (!def.containsField(fieldId)) {
                Log.w(TAG, "field and form mismatch");
                return;
            }
            ChoiceField field = (ChoiceField) def.field(fieldId).get();
            String fieldName = def.field(fieldId).name();

            TextView name = view.findViewById(R.id.choiceFieldName);
            name.setText(fieldName);

            RadioGroup group = view.findViewById(R.id.choiceFieldGroup);
            group.removeAllViews();
            Util.enumerate(field.choices()).forEach((i, choice) -> {
                RadioButton button = new RadioButton(group.getContext());
                button.setText(choice);
                button.setId(i);
                group.addView(button);
            });

            TextView error = view.findViewById(R.id.choiceFieldError);
            group.setOnCheckedChangeListener((radioGroup, checkedId) ->
                    form.set(fieldId, checkedId).applyError(error::setText));
        });
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_choice, container, false);
    }
}
