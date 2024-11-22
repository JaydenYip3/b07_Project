package com.b07.planetze.form.definition;

import androidx.annotation.NonNull;

import com.b07.planetze.form.exception.FormBuilderBuiltException;
import com.b07.planetze.form.field.Field;
import com.b07.planetze.util.immutability.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a {@link FormDefinition}.
 */
public final class FormBuilder {
    @NonNull private final FormId formId;
    @NonNull private final List<Field<?>> fields;
    private boolean built;

    public FormBuilder() {
        formId = new FormId();
        fields = new ArrayList<>();
        built = false;
    }

    /**
     * Adds a field. <br>
     * Fields cannot be added after calling {@link FormBuilder#build()}.
     * @param field the field to add
     * @return an id that refers to the added field
     * @param <T> the type of the field value
     */
    @NonNull
    public <T> FieldId<T> add(@NonNull Field<T> field) {
        if (built) {
            throw new FormBuilderBuiltException();
        }
        int index = fields.size();
        fields.add(field);

        return new FieldId<>(formId, index);
    }

    /**
     * {@return a new form definition} <br>
     * This method can only be called once.
     */
    @NonNull
    public FormDefinition build() {
        if (built) {
            throw new FormBuilderBuiltException();
        }
        built = true;

        return new FormDefinition(formId, new ImmutableList<>(fields));
    }
}
