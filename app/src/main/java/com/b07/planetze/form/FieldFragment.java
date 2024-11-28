package com.b07.planetze.form;

import static com.b07.planetze.util.option.Option.none;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.form.definition.Field;
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

    @NonNull private Option<FieldId<V>> fieldId;

    protected FieldFragment() {
        fieldId = none();
    }

    protected abstract void displayMissingField(@NonNull View view);

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
        Option<FieldId<V>> f = Option.mapNull(
                args.getParcelable(FIELD_ID_KEY, FieldId.class));

        Log.d(TAG, "created " + f);

        fieldId = f;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FieldId<V> id = fieldId.getOrThrow(
                new FormException("Field id argument not provided"));

        FormViewModel model = new ViewModelProvider(requireActivity())
                .get(FormViewModel.class);

        model.getMissingFields().observe(getViewLifecycleOwner(), missing -> {
            if (missing.contains(id.index())) {
                displayMissingField(view);
            }
        });

        model.getForm().observe(getViewLifecycleOwner(), maybeForm -> {
            if (!(maybeForm instanceof Some<Form> some)) {
                return;
            }
            Form form = some.get();
            FormDefinition def = form.definition();

            if (!def.containsField(id)) {
                Log.w(TAG, "field-form mismatch: " + id);
                return;
            }

            @SuppressWarnings("unchecked")
            F field = (F) def.field(id);

            String name = def.name(id);
            initializeField(view, form, id, field, name);
        });
    }

    @Override
    @NonNull
    public abstract View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "destroyed " + fieldId);
    }
}
