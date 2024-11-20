package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.ImmutableList;

public final class FormSubmission {
    @NonNull private final FormId formId;
    @NonNull private final ImmutableList<FieldValue> values;

    public FormSubmission(@NonNull FormId formId, @NonNull ImmutableList<FieldValue> values) {
        this.formId = formId;
        this.values = values;
    }

    public <V extends FieldValue> V get(@NonNull FieldId<V> field) {
        if (!formId.equals(field.formId())) {
            throw new FormIdException();
        }
        @SuppressWarnings("unchecked")
        V value = (V) values.get(field.index());

        return value;
    }
}
