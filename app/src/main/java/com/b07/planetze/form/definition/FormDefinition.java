package com.b07.planetze.form.definition;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;
import com.b07.planetze.form.exception.FormIdException;
import com.b07.planetze.form.field.Field;
import com.b07.planetze.util.immutability.ImmutableList;

/**
 * Defines the fields of a {@link Form}. <br>
 * Use {@link FormBuilder} instead of creating this manually.
 * @param id the form's id
 * @param fields the form's fields
 */
public record FormDefinition(@NonNull FormId id,
                             @NonNull ImmutableList<NamedField<?>> fields) {
    public boolean containsField(@NonNull FieldId<?> field) {
        return id.equals(field.formId());
    }

    /**
     * Throws a {@link FormIdException} if this definition does not contain a
     * given field
     * @param field the given field
     */
    public void assertContainsField(@NonNull FieldId<?> field) {
        if (!containsField(field)) {
            throw new FormIdException();
        }
    }

    /**
     * {@return the field associated with a field id}
     * @param field the field's id
     * @param <T> the type of value held by the field
     */
    @NonNull
    public <T> NamedField<T> field(@NonNull FieldId<T> field) {
        assertContainsField(field);

        @SuppressWarnings("unchecked")
        NamedField<T> f = (NamedField<T>) fields.get(field.index());

        return f;
    }

    /**
     * {@return a new form with <code>this</code> as its definition}
     */
    @NonNull
    public Form createForm() {
        return new Form(this);
    }
}
