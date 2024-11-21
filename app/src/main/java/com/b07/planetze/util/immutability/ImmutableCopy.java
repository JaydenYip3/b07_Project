package com.b07.planetze.util.immutability;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * An immutable wrapper for objects that are {@link MutableWithCopy}.
 * @param <T>
 */
public class ImmutableCopy<T extends MutableWithCopy<T>> {
    @NonNull private final T object;

    /**
     * Immutably stores a copy of a given object.
     * @param object the object
     */
    public ImmutableCopy(@NonNull T object) {
        this.object = object.copy();
    }

    /**
     * {@return a copy of the wrapped object} <br>
     * This calls {@link MutableWithCopy#copy()}.
     */
    @NonNull
    public T mutableCopy() {
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
}
