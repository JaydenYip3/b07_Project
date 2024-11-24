package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

import java.util.function.Function;

/**
 * Holds a positive integer.
 */
public final class IntField implements Field<Integer> {
    @NonNull public static final IntField POSITIVE = IntField.withValidator(v ->
        v > 0 ? ok() : error("Value must be positive")
    );

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

    @NonNull
    @Override
    public IntFragment createFragment(@NonNull FieldId<?> fieldId) {
        return IntFragment.newInstance(fieldId);
    }
}
