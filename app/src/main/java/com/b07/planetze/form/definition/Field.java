package com.b07.planetze.form.definition;

import static com.b07.planetze.util.option.Option.some;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.FieldFragment;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

/**
 * An input field.
 * @param <T> the type of value accepted by this field
 */
public interface Field<T> extends Parcelable {
    /**
     * Checks whether a value is valid, returning an error message if it isn't.
     * @param value the value to validate
     * @return <code>Ok</code> if <code>value</code> is valid;
     *         <code>Error</code> with an error message otherwise
     */
    @NonNull
    Result<Unit, String> validate(@NonNull T value);

    /**
     * {@return a new instance of the fragment associated with this field}
     * @param fieldId this field's id
     */
    @NonNull
    FieldFragment<? extends Field<T>, T> createFragment(
            @NonNull FieldId<?> fieldId
    );

    @NonNull
    FieldDeparcelizer deparcelizer();

    /**
     * {@return a field with an added initial value}
     * @param value the initial value
     */
    @NonNull
    default InitiallyFilled<T> initially(@NonNull T value) {
        return InitiallyFilled.create(this, some(value));
    }
}
