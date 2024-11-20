package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.form.FieldDefinition;
import com.b07.planetze.form.FieldInitException;
import com.b07.planetze.util.result.Err;
import com.b07.planetze.util.ImmutableList;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

public final class ChoiceDefinition implements FieldDefinition<Choice> {
    @NonNull private final ImmutableList<String> choices;
    @NonNull private final Option<Choice> initialSelection;

    public ChoiceDefinition(
            @NonNull ImmutableList<String> choices,
            @NonNull Option<Choice> initialSelection
    ) {
        this.choices = choices;
        this.initialSelection = initialSelection;

        if (initialSelection.isSomeAnd(c -> !choices.containsIndex(c.index()))) {
            throw new FieldInitException("initial selection index out of bounds");
        }
    }

    @NonNull
    @Override
    public Option<Choice> initialValue() {
        return initialSelection;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Choice value) {
        if (choices.containsIndex(value.index())) {
            return new Ok<>(Unit.UNIT);
        }
        return new Err<>("Choice index out of bounds");
    }
}
