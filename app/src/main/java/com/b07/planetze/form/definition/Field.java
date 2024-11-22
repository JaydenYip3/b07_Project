package com.b07.planetze.form.definition;

import androidx.annotation.NonNull;

import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

/**
 * An input field.
 * @param <T> the type of value accepted by this field
 */
public interface Field<T> {
    /**
     * {@return the initial value of the field}
     */
    @NonNull
    Option<T> initialValue();

    /**
     * Checks whether a value is valid, returning an error message if it isn't.
     * @param value the value to validate
     * @return <code>Ok</code> if <code>value</code> is valid;
     *         <code>Error</code> with an error message otherwise
     */
    @NonNull
    Result<Unit, String> validate(@NonNull T value);
}
