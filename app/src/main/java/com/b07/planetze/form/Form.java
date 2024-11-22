package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.Util;

import java.util.ArrayList;
import java.util.List;

public final class Form {
    @NonNull private final FormDefinition definition;
    @NonNull private final List<Option<Object>> values;

    /**
     * Instantiates a form given a definition.
     * @param definition the form's definition
     * @see FormBuilder
     */
    public Form(@NonNull FormDefinition definition) {
        this.definition = definition;
        this.values = new ArrayList<>(definition.fields().size());

        definition
                .fields()
                .forEach(f -> values.add(f.initialValue().map(v -> v)));
    }

    public FormDefinition definition() {
        return definition;
    }

    /**
     * Sets the value of a field. <br>
     * Sets the field to {@link None} If the provided value is invalid. <br>
     * Throws {@link com.b07.planetze.form.exception.FormIdException}.
     * @param field the field's id
     * @param value the value to set
     * @return an error message (if the provided value is invalid)
     * @param <T> the type of the field's value
     */
    @NonNull
    public <T> Result<Unit, String> set(
            @NonNull FieldId<T> field,
            @NonNull T value
    ) {
        // this calls assertContainsField
        Result<Unit, String> r = definition.field(field).validate(value);

        values.set(field.index(), r.ok().map(x -> value));
        return r;
    }

    /**
     * {@return the value of a field} <br>
     * Throws {@link com.b07.planetze.form.exception.FormIdException}.
     * @param field the field to get
     * @param <T> the type of the field's value
     */
    @NonNull
    public <T> Option<T> get(@NonNull FieldId<T> field) {
        definition.assertContainsField(field);

        @SuppressWarnings("unchecked")
        Option<T> value = values.get(field.index()).map(v -> (T) v);

        return value;
    }

    /**
     * {@return a form submission or a list of missing field indices}
     */
    @NonNull
    public Result<FormSubmission, List<Integer>> submit() {
        List<Integer> invalid = new ArrayList<>();
        List<Object> presentValues = new ArrayList<>();

        Util.enumerate(values).forEach((i, value) -> value
                .apply(presentValues::add)
                .applyNone(() -> invalid.add(i)));

        if (!invalid.isEmpty()) {
            return new Error<>(invalid);
        }
        return new Ok<>(new FormSubmission(
                definition.id(),
                new ImmutableList<>(presentValues)
        ));
    }
}
