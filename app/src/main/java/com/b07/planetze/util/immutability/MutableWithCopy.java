package com.b07.planetze.util.immutability;

import androidx.annotation.NonNull;

import com.b07.planetze.util.Self;

/**
 * An object that implements deep-copying of its mutable fields. <br>
 * Limitation: generic mutable fields that do not implement this interface
 * may be shallow-copied. <br>
 * @param <T> the type of the implementing object
 */
public interface MutableWithCopy<T extends MutableWithCopy<T>> extends Self<T> {
    /**
     * {@return a copy of <code>this</code> such that all mutable
     *         fields (with limitations) are deep-copied} <br>
     * Limitation: generic mutable fields that do not implement
     * {@link MutableWithCopy} may be shallow-copied.
     */
    @NonNull
    T copy();

    /**
     * {@return an immutable copy of <code>this</code>}
     */
    default ImmutableCopy<T> immutableCopy() {
        return new ImmutableCopy<>(self());
    }
}
