package com.b07.planetze.form.definition;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;
import com.b07.planetze.form.exception.FormIdException;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.option.Option;

/**
 * Describes the fields of a {@link Form} (i.e., stores the name, initial value,
 * and validators of all fields). <br>
 * This is used for creating forms. <br>
 * Use {@link FormBuilder} instead of creating this manually. <br>
 * @param id the form's id
 * @param fields the form's fields
 */
public record FormDefinition(@NonNull FormId id,
                             @NonNull ImmutableList<FieldDefinition<?>> fields) {
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

    @NonNull
    private <T> FieldDefinition<T> fieldDefinition(@NonNull FieldId<T> field) {
        assertContainsField(field);

        @SuppressWarnings("unchecked")
        FieldDefinition<T> f = (FieldDefinition<T>) fields.get(field.index());

        return f;
    }

    @NonNull
    public <T> Field<T> field(@NonNull FieldId<T> field) {
        return fieldDefinition(field).field();
    }

    @NonNull
    public String name(@NonNull FieldId<?> field) {
        return fieldDefinition(field).name();
    }

    @NonNull
    public <T> Option<T> initialValue(@NonNull FieldId<T> field) {
        return fieldDefinition(field).initialValue();
    }

    /**
     * {@return a new form with <code>this</code> as its definition}
     */
    @NonNull
    public Form createForm() {
        return new Form(this);
    }
}
