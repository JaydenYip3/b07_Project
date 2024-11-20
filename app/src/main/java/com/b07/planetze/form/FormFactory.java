package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.ImmutableList;

public class FormFactory {
    @NonNull private final FormId formId;
    @NonNull private final ImmutableList<Field<?>> fields;

    public FormFactory(@NonNull FormId formId, @NonNull ImmutableList<Field<?>> fields) {
        this.formId = formId;
        this.fields = fields;
    }

    @NonNull
    public Form createForm() {
        return new Form(formId, fields);
    }
}
