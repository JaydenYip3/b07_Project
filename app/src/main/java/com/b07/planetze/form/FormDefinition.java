package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.form.exception.FormIdException;
import com.b07.planetze.util.immutability.ImmutableList;

public record FormDefinition(@NonNull FormId id,
                             @NonNull ImmutableList<Field<?>> fields) {
    @NonNull
    public <T> Field<T> field(@NonNull FieldId<T> field) {
        id.assertEquals(field.formId());

        @SuppressWarnings("unchecked")
        Field<T> f = (Field<T>) fields.get(field.index());

        return f;
    }

    @NonNull
    public Form createForm() {
        return new Form(this);
    }
}
