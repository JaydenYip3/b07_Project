package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.form.exception.FormIdException;
import com.b07.planetze.util.ImmutableList;

public final class FormSubmission {
    @NonNull private final FormId formId;
    @NonNull private final ImmutableList<Object> values;

    public FormSubmission(@NonNull FormId formId, @NonNull ImmutableList<Object> values) {
        this.formId = formId;
        this.values = values;
    }

    public <T> T get(@NonNull FieldId<T> field) {
        if (!formId.equals(field.formId())) {
            throw new FormIdException();
        }
        @SuppressWarnings("unchecked")
        T value = (T) values.get(field.index());

        return value;
    }
}
