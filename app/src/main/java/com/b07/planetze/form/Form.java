package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.ImmutableArray;
import com.b07.planetze.util.Ok;
import com.b07.planetze.util.Option;
import com.b07.planetze.util.Result;
import com.b07.planetze.util.Some;
import com.b07.planetze.util.Unit;

import java.util.ArrayList;
import java.util.List;

public class Form {
    @NonNull private final ImmutableArray<FieldDefinition<?>> fields;
    @NonNull private final List<Option<Object>> values;
    private final int id;

    /**
     * Instantiates a form given an id and fields. <br>
     * Forms with different fields must have different ids. <br>
     * Use {@link FormBuilder} instead of calling this manually.
     * @param id the form's id
     * @param fields the form's fields
     */
    public Form(int id, @NonNull ImmutableArray<FieldDefinition<?>> fields) {
        this.id = id;
        this.fields = fields;
        this.values = new ArrayList<>(fields.size());

        for (FieldDefinition<?> field : fields) {
            values.add(field.initialValue().map(v -> v));
        }
    }

    public <V extends FieldValue> Result<Unit, String> set(
            @NonNull FieldId<V> field,
            @NonNull V value
    ) {
        if (field.formId() != this.id) {
            throw new FieldIdException();
        }
        @SuppressWarnings("unchecked")
        FieldDefinition<V> definition = (FieldDefinition<V>) fields.get(field.index());

        Result<Unit, String> r = definition.validate(value);
        if (r.isOk()) {
            values.set(field.index(), new Some<>(value));
        }
        return r;
    }
}
