package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

/**
 * An input field.
 */
public interface Field<T> {
    @NonNull
    Option<T> initialValue();

    @NonNull
    Result<Unit, String> validate(@NonNull T value);
}
