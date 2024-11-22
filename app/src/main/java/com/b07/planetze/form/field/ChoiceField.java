package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.Field;
import com.b07.planetze.form.exception.FieldInitException;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

/**
 * Holds the index (starting from 0) of a single selection of choices.
 * @param choices the choice names
 * @param initialValue an initial choice selection index
 */
public record ChoiceField(
        @NonNull ImmutableList<String> choices,
        @NonNull Option<Integer> initialValue
) implements Field<Integer> {
    public ChoiceField {
        initialValue.apply(v -> validate(v)
                .mapError(FieldInitException::new)
                .expect());
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Integer value) {
        if (choices.containsIndex(value)) {
            return new Ok<>(Unit.UNIT);
        }
        return new Error<>("Integer index out of bounds");
    }
}
