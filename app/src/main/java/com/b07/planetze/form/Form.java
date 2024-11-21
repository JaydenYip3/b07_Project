package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.form.exception.FormIdException;
import com.b07.planetze.util.result.Err;
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
     * Instantiates a form given an id and fields. <br>
     * Forms with different fields must have different ids. <br>
     * Use {@link FormBuilder} instead of calling this manually.
     * @param definition the form's definition
     */
    public Form(@NonNull FormDefinition definition) {
        this.definition = definition;
        this.values = new ArrayList<>(definition.fields().size());

        definition
                .fields()
                .forEach(f -> values.add(f.initialValue().map(v -> v)));
    }

    @NonNull
    public <T> Result<Unit, String> set(
            @NonNull FieldId<T> field,
            @NonNull T value
    ) {
        definition.id().assertEquals(field.formId());

        return definition
                .field(field)
                .validate(value)
                .apply(x -> values.set(field.index(), new Some<>(value)));
    }

    @NonNull
    public Result<FormSubmission, List<Integer>> submit() {
        List<Integer> invalid = new ArrayList<>();
        List<Object> presentValues = new ArrayList<>();

        Util.enumerate(values).forEach((i, value) -> value
                .apply(presentValues::add)
                .applyNone(() -> invalid.add(i)));

        if (!invalid.isEmpty()) {
            return new Err<>(invalid);
        }
        return new Ok<>(new FormSubmission(
                definition.id(),
                new ImmutableList<>(presentValues)
        ));
    }
}
