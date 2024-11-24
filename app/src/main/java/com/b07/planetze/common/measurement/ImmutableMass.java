package com.b07.planetze.common.measurement;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Mass}. <br>
 * Consider instantiating this with {@link Mass#immutableCopy()}.
 */
public final class ImmutableMass extends ImmutableCopy<Mass> {
    public ImmutableMass(@NonNull Mass object) {
        super(object);
    }

    public double kg() {
        return object.kg();
    }

    public double g() {
        return object.g();
    }
}
