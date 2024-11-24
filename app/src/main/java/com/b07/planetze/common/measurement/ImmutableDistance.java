package com.b07.planetze.common.measurement;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Distance}. <br>
 * Consider instantiating this with {@link Distance#immutableCopy()}.
 */
public final class ImmutableDistance extends ImmutableCopy<Distance> {
    public ImmutableDistance(@NonNull Distance object) {
        super(object);
    }

    public double km() {
        return object.km();
    }

    public double m() {
        return object.m();
    }

    public double mi() {
        return object.mi();
    }
}
