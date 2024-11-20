package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.form.FieldDefinition;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Err;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

public record PositiveInt(@NonNull Option<Int> initialValue) implements FieldDefinition<Int> {

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Int value) {
        if (value.value() > 0) {
            return new Ok<>(Unit.UNIT);
        }
        return new Err<>("Value must be positive");
    }
}
