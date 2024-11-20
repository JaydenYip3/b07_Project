package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.form.exception.FormBuilderBuiltException;
import com.b07.planetze.util.immutability.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public final class FormFactoryBuilder {
    @NonNull private final FormId formId;
    @NonNull private final List<Field<?>> fields;
    private boolean built;

    public FormFactoryBuilder() {
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
    public FormFactory build() {
        if (built) {
            throw new FormBuilderBuiltException();
        }
        built = true;

        return new FormFactory(formId, new ImmutableList<>(fields));
    }
}
