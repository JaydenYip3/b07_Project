package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

import java.util.function.Function;

/**
 * Holds a positive integer.
 */
public final class IntField implements Field<Integer> {
    public static final IntField POSITIVE = IntField.withValidator(v -> {
        if (v > 0) {
            return new Ok<>(Unit.UNIT);
        }
        return new Error<>("Value must be positive");
    });

    @NonNull private final Function<Integer, Result<Unit, String>> validator;

    public static IntField withValidator(
            @NonNull Function<Integer, Result<Unit, String>> validator
    ) {
        return new IntField(validator);
    }

    private IntField(
            @NonNull Function<Integer, Result<Unit, String>> validator
    ) {
        this.validator = validator;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Integer value) {
        return validator.apply(value);
    }
}