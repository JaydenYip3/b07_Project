package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Field;
import com.b07.planetze.form.exception.FieldInitException;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Err;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

public final class ChoiceField implements Field<Integer> {
    @NonNull private final ImmutableList<String> choices;
    @NonNull private final Option<Integer> initialValue;

    public ChoiceField(
            @NonNull ImmutableList<String> choices,
            @NonNull Option<Integer> initialValue
    ) {
        this.choices = choices;
        this.initialValue = initialValue;

        if (initialValue.isSomeAnd(v -> !choices.containsIndex(v))) {
            throw new FieldInitException("initial selection index out of bounds");
        }
    }

    @NonNull
    @Override
    public Option<Integer> initialValue() {
        return initialValue;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Integer value) {
        if (choices.containsIndex(value)) {
            return new Ok<>(Unit.UNIT);
        }
        return new Err<>("Integer index out of bounds");
    }
}
