package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.Field;
import com.b07.planetze.form.exception.FieldInitException;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

/**
 * Holds a positive integer.
 * @param initialValue an initial value
 */
public record PositiveIntField(@NonNull Option<Integer> initialValue)
        implements Field<Integer> {
    public PositiveIntField {
        initialValue.apply(v -> validate(v)
                .mapError(FieldInitException::new)
                .expect());
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Integer value) {
        if (value > 0) {
            return new Ok<>(Unit.UNIT);
        }
        return new Error<>("Value must be positive");
    }
}
