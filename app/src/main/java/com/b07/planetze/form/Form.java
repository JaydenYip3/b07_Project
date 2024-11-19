package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.ImmutableList;
import com.b07.planetze.util.Option;
import com.b07.planetze.util.Result;
import com.b07.planetze.util.Some;
import com.b07.planetze.util.Unit;

import java.util.ArrayList;
import java.util.List;

public class Form {
    @NonNull private final ImmutableList<FieldDefinition<?>> fields;
    @NonNull private final List<Option<FieldValue>> values;
    private final int formId;

    /**
     * Instantiates a form given an id and fields. <br>
     * Forms with different fields must have different ids. <br>
     * Use {@link FormBuilder} instead of calling this manually.
     * @param formId the form's id
     * @param fields the form's fields
     */
    public Form(int formId, @NonNull ImmutableList<FieldDefinition<?>> fields) {
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
        if (field.formId() != formId) {
            throw new FieldIdException();
        }
        @SuppressWarnings("unchecked")
        FieldDefinition<V> definition = (FieldDefinition<V>) fields.get(field.index());

        return definition
                .validate(value)
                .apply(x -> values.set(field.index(), new Some<>(value)));
    }


}
