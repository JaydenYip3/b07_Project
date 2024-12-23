package com.b07.planetze.form.definition;

import static com.b07.planetze.util.result.Result.ok;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.exception.FieldInitException;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.option.Option;

/**
 * Wraps a field, giving it an initial value. <br>
 * This validates the initial value.
 * @param <T> the field value type
 */
public final class InitiallyFilled<T> {
    @NonNull private final Field<T> field;
    @NonNull private final Option<T> initialValue;

    private InitiallyFilled(
        @NonNull Field<T> field,
        @NonNull Option<T> initialValue
    ) {
        this.field = field;
        this.initialValue = initialValue;
    }

    public static <T> InitiallyFilled<T> create(
            @NonNull Field<T> field,
            @NonNull Option<T> initialValue
    ) {
        InitiallyFilled<T> f = new InitiallyFilled<>(field, initialValue);
        f.initialValue
                .map(f.field::validate)
                .apply(r -> r
                        .mapError(FieldInitException::new)
                        .getOrThrowError());

        return f;
    }

    @NonNull
    public Field<T> field() {
        return field;
    }

    @NonNull
    public Option<T> initialValue() {
        return initialValue;
    }
}
