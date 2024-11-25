package com.b07.planetze.form.definition;

import androidx.annotation.NonNull;

import com.b07.planetze.util.option.Option;

/**
 * Stores the name, initial value, and validator of a field.
 * @param <T> the value held by the field
 */
public final class FieldDefinition<T> {
    @NonNull private final String name;
    @NonNull private final InitiallyFilled<T> field;

    public FieldDefinition(@NonNull String name,
                           @NonNull InitiallyFilled<T> field) {
        this.name = name;
        this.field = field;
    }

    @NonNull
    public String name() {
        return name;
    }
    @NonNull
    public Field<T> field() {
        return field.field();
    }

    @NonNull
    public Option<T> initialValue() {
        return field.initialValue();
    }
}