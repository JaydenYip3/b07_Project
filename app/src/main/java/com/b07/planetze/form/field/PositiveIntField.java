package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Field;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Err;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

public record PositiveIntField(@NonNull Option<Integer> initialValue) implements Field<Integer> {

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Integer value) {
        if (value > 0) {
            return new Ok<>(Unit.UNIT);
        }
        return new Err<>("Value must be positive");
    }
}
