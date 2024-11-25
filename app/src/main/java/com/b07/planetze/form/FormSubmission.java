package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormId;
import com.b07.planetze.util.immutability.ImmutableList;

/**
 * Stores the values of a submitted form. All fields are filled.
 */
public final class FormSubmission {
    @NonNull private final FormId formId;
    @NonNull private final ImmutableList<Object> values;

    public FormSubmission(@NonNull FormId formId, @NonNull ImmutableList<Object> values) {
        this.formId = formId;
        this.values = values;
    }

    public <T> T get(@NonNull FieldId<T> field) {
        formId.assertEquals(field.formId());

        @SuppressWarnings("unchecked")
        T value = (T) values.get(field.index());

        return value;
    }
}
