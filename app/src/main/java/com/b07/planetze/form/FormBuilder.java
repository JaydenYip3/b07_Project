package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.ImmutableArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FormBuilder {
    @NonNull private final List<FieldDefinition<?>> fields;
    private boolean built;
    private final int formId;

    public FormBuilder() {
        fields = new ArrayList<>();
        formId = ThreadLocalRandom.current().nextInt();
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
        FieldDefinition<?>[] array = fields.toArray(new FieldDefinition<?>[0]);

        return new Form(formId, new ImmutableArray<>(array));
    }
}
