package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

/**
 * An input field.
 */
public interface FieldDefinition<V extends FieldValue> {
    @NonNull
    Option<V> initialValue();

    @NonNull
    Result<Unit, String> validate(@NonNull V value);
}
