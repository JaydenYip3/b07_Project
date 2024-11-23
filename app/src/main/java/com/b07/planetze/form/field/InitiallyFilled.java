package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.ok;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.exception.FieldInitException;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

/**
 * Wraps a field, giving it an initial value.
 * @param field a field
 * @param initialValue an initial value
 * @param <T> the field value type
 */
public class InitiallyFilled<T> implements Field<T> {
    @NonNull private final Option<T> initialValue;
    @NonNull private final Field<T> field;

    private InitiallyFilled(
        @NonNull Field<T> field,
        @NonNull Option<T> initialValue
    ) {
        if (field instanceof InitiallyFilled) {
            throw new FieldInitException("Field is already initially filled");
        }
        this.field = field;
        this.initialValue = initialValue;
        initialValue.apply(v -> validate(v)
                .mapError(FieldInitException::new)
                .getOrThrowError());
    }

    public static <T> InitiallyFilled<T> create(
            @NonNull Field<T> field,
            @NonNull Option<T> initialValue
    ) {
        InitiallyFilled<T> f = new InitiallyFilled<>(field, initialValue);
        f.initialValue
                .apply(f::validate)
                .resolve(x -> x, Result::ok)

    }

    @NonNull
    @Override
    public Option<T> initialValue() {
        return initialValue;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull T value) {
        return field.validate(value);
    }

    @NonNull
    @Override
    public FieldFragment<? extends Field<T>, T> createFragment(
            @NonNull FieldId<?> fieldId
    ) {
        return field.createFragment(fieldId);
    }
}
