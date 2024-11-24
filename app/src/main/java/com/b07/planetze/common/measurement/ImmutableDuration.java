package com.b07.planetze.common.measurement;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Duration}. <br>
 * Consider instantiating this with {@link Duration#immutableCopy()}.
 */
public class ImmutableDuration extends ImmutableCopy<Duration> {
    public ImmutableDuration(@NonNull Duration object) {
        super(object);
    }

    public double h() {
        return object.h();
    }

    public double s() {
        return object.s();
    }
}
