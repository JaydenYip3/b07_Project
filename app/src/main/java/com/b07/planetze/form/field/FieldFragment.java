package com.b07.planetze.form.field;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormViewModel;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.exception.FormException;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

/**
 * The fragment associated with a {@link Field}.
 * @param <F> the type of the field
 * @param <V> the type of the field's value
 */
public abstract class FieldFragment<F extends Field<V>, V> extends Fragment {
    @NonNull protected static final String FIELD_ID_KEY = "field";
    @NonNull private static final String TAG = "FieldFragment";

    @Nullable private FieldId<V> fieldId;

    public abstract void initializeField(@NonNull View view,
                                         @NonNull Form form,
                                         @NonNull FieldId<V> id,
                                         @NonNull F field,
                                         @NonNull String fieldName);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = Option.mapNull(getArguments())
                .getOrThrow(new FormException("No arguments provided"));

        @SuppressWarnings("unchecked")
        FieldId<V> f = Option
                .mapNull(args.getParcelable(FIELD_ID_KEY, FieldId.class))
                .getOrThrow(new FormException("Invalid arguments"));

        fieldId = f;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (fieldId == null) {
            throw new FormException("Field id improperly initialized");
        }

        FormViewModel model = new ViewModelProvider(requireActivity())
                .get(FormViewModel.class);

        model.getForm().observe(getViewLifecycleOwner(), formOption -> {
            if (!(formOption instanceof Some<Form> some)) {
                return;
            }
            Form form = some.get();
            FormDefinition def = form.definition();

            if (!def.containsField(fieldId)) {
                Log.w(TAG, "field-form mismatch");
                return;
            }

            @SuppressWarnings("unchecked")
            F field = (F) def.field(fieldId).get();

            String name = def.field(fieldId).name();

            initializeField(view, form, fieldId, field, name);
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
