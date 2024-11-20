package com.b07.planetze.util.immutability;

import androidx.annotation.NonNull;

/**
 * An object that implements deep-copying of its mutable fields. <br>
 * Limitation: generic mutable fields that do not implement this interface
 * may be shallow-copied. <br>
 * <b><code>T</code> must be the same type as the implementing object.</b>
 * @param <T> the type of the implementing object
 */
public interface MutableWithCopy<T extends MutableWithCopy<T>> {
    /**
     * {@return a copy of <code>this</code> such that all mutable
     *         fields (with limitations) are deep-copied} <br>
     * Limitation: generic mutable fields that do not implement
     * {@link MutableWithCopy} may be shallow-copied.
     */
    @NonNull
    T copy();

    /**
     * Returns an {@link ImmutableCopy} of <code>this</code>.
     * @return an {@link ImmutableCopy} of <code>this</code>
     */
    default ImmutableCopy<T> immutableCopy() {
        @SuppressWarnings("unchecked")
        T object = (T) this;

        return new ImmutableCopy<>(object);
    }
}
