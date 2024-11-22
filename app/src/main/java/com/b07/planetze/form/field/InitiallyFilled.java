package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.form.exception.FieldInitException;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;

/**
 * Wraps a field, giving it an initial value.
 * @param field a field
 * @param initialValue an initial value
 * @param <T> the field value type
 */
public record InitiallyFilled<T>(
        @NonNull Field<T> field,
        @NonNull Option<T> initialValue
) implements Field<T> {
    public InitiallyFilled {
        if (field instanceof InitiallyFilled) {
            throw new FieldInitException("Field is already initially filled");
        }
        initialValue.apply(v -> validate(v)
                .mapError(FieldInitException::new)
                .expect());
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull T value) {
        return field.validate(value);
    }
}
