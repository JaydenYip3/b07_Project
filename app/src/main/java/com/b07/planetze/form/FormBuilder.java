package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.ImmutableArray;

import java.util.ArrayList;
import java.util.List;

public class FormBuilder {
    @NonNull private final List<FieldDefinition<?>> fields;

    public FormBuilder() {
        fields = new ArrayList<>();
    }

    @NonNull
    public <V extends FieldValue> FieldId<V> addField(@NonNull FieldDefinition<V> field) {
        int index = fields.size();
        fields.add(field);

        return new FieldId<>(index);
    }

    @NonNull
    public Form build() {
        FieldDefinition<?>[] array = fields.toArray(new FieldDefinition<?>[0]);
        return new Form(new ImmutableArray<>(array));
    }
}
