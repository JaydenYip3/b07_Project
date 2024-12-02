package com.b07.planetze.util;

import androidx.annotation.NonNull;

/**
 * Indicates that the implementing object is of type <code>T</code>.
 * @param <T> the type of the object
 */
public interface Self<T> {
    /**
     * {@return <code>this</code>}
     */
    @NonNull
    T self();
}
