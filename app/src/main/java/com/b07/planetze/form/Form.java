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

    /**
     * Instantiates a form. <br>
     * Use {@link FormBuilder} instead of calling this manually.
     * @param fields the form's fields
     */
    public Form(@NonNull ImmutableArray<FieldDefinition<?>> fields) {
        this.fields = fields;
        this.values = new ArrayList<>(fields.size());

        for (FieldDefinition<?> field : fields) {
            values.add(field.initialValue().map(v -> v));
        }
    }

    public <V extends FieldValue> Result<Unit, String> set(
            @NonNull FieldId<V> id,
            @NonNull V value
    ) {
        @SuppressWarnings("unchecked")
        FieldDefinition<V> field = (FieldDefinition<V>) fields.get(id.get());

        Result<Unit, String> r = field.validate(value);
        if (r.isOk()) {
            values.set(id.get(), new Some<>(value));
        }
        return r;
    }
}
