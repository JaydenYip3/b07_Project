package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.result.Err;
import com.b07.planetze.util.ImmutableList;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.Util;

import java.util.ArrayList;
import java.util.List;

public final class Form {
    @NonNull private final FormId formId;
    @NonNull private final ImmutableList<FieldDefinition<?>> fields;
    @NonNull private final List<Option<FieldValue>> values;

    /**
     * Instantiates a form given an id and fields. <br>
     * Forms with different fields must have different ids. <br>
     * Use {@link FormBuilder} instead of calling this manually.
     * @param formId the form's id
     * @param fields the form's fields
     */
    public Form(@NonNull FormId formId, @NonNull ImmutableList<FieldDefinition<?>> fields) {
        this.formId = formId;
        this.fields = fields;
        this.values = new ArrayList<>(fields.size());

        for (FieldDefinition<?> field : fields) {
            values.add(field.initialValue().map(v -> v));
        }
    }

    @NonNull
    public <V extends FieldValue> Result<Unit, String> set(
            @NonNull FieldId<V> field,
            @NonNull V value
    ) {
        if (!formId.equals(field.formId())) {
            throw new FormIdException();
        }
        @SuppressWarnings("unchecked")
        FieldDefinition<V> definition = (FieldDefinition<V>) fields.get(field.index());

        return definition
                .validate(value)
                .apply(x -> values.set(field.index(), new Some<>(value)));
    }

    @NonNull
    public Result<FormSubmission, List<Integer>> submit() {
        List<Integer> invalid = new ArrayList<>();
        List<FieldValue> presentValues = new ArrayList<>();

        Util.enumerate(values).forEach((i, value) -> value
                .apply(presentValues::add)
                .applyNone(() -> invalid.add(i)));

        if (!invalid.isEmpty()) {
            return new Err<>(invalid);
        }
        return new Ok<>(new FormSubmission(formId, new ImmutableList<>(presentValues)));
    }
}
