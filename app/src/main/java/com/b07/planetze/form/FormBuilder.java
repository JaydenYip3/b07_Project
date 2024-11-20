package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class FormBuilder {
    @NonNull private final FormId formId;
    @NonNull private final List<FieldDefinition<?>> fields;
    private boolean built;

    public FormBuilder() {
        formId = new FormId();
        fields = new ArrayList<>();
        built = false;
    }

    @NonNull
    public <V extends FieldValue> FieldId<V> addField(@NonNull FieldDefinition<V> field) {
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
