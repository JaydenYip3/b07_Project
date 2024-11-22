package com.b07.planetze.form.field;

import androidx.annotation.NonNull;

import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

/**
 * An input field.
 * @param <T> the type of value accepted by this field
 */
public interface Field<T> {
    /**
     * Checks whether a value is valid, returning an error message if it isn't.
     * @param value the value to validate
     * @return <code>Ok</code> if <code>value</code> is valid;
     *         <code>Error</code> with an error message otherwise
     */
    @NonNull
    Result<Unit, String> validate(@NonNull T value);

    /**
     * {@return the initial value of the field}
     */
    @NonNull
    default Option<T> initialValue() {
        return new None<>();
    }

    @NonNull
    default InitiallyFilled<T> initially(Option<T> value) {
        return new InitiallyFilled<>(this, value);
    }
}