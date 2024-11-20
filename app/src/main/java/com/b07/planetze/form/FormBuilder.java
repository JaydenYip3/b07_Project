package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public final class FormBuilder {
    @NonNull private final FormId formId;
    @NonNull private final List<Field<?>> fields;
    private boolean built;

    public FormBuilder() {
        formId = new FormId();
        fields = new ArrayList<>();
        built = false;
    }

    @NonNull
    public <T> FieldId<T> add(@NonNull Field<T> field) {
        if (built) {
            throw new FormBuilderBuiltException();
        }
        int index = fields.size();
        fields.add(field);

        return new FieldId<>(formId, index);
    }

    @NonNull
    public Form build() {
        built = true;
        return new Form(formId, new ImmutableList<>(fields));
    }
}
