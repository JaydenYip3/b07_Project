package com.b07.planetze.util.immutability;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * An immutable wrapper for objects that are {@link MutableWithCopy}. <br>
 * Consider instantiating this with {@link MutableWithCopy#immutableCopy()}.
 * @param <T> the type of the object
 */
public class ImmutableCopy<T extends MutableWithCopy<T>> {
    @NonNull protected final T object;

    public ImmutableCopy(@NonNull T object) {
        this.object = object.copy();
    }

    /**
     * {@return a copy of the wrapped object} <br>
     * This calls {@link MutableWithCopy#copy()}.
     */
    @NonNull
    public T copy() {
        return object.copy();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof ImmutableCopy<?> c) && object.equals(c.object);
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return object.toString();
    }
}
