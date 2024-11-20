package com.b07.planetze.util;

import androidx.annotation.NonNull;

/**
 * An object that implements deep copying.
 * @param <T> the type of the object
 */
public interface Copy<T> {
    /**
     * Creates a deep copy of <code>this</code>.
     * @return a deep copy of <code>this</code>
     */
    @NonNull
    T copy();
}
