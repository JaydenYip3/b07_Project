package com.b07.planetze.util.measurement;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJson;
import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Duration}. <br>
 * Consider instantiating this with {@link Duration#immutableCopy()}.
 */
public final class ImmutableDuration extends ImmutableCopy<Duration>
        implements ToJson {
    public ImmutableDuration(@NonNull Duration object) {
        super(object);
    }

    /**
     * {@return this duration in hours}
     */
    public double h() {
        return object.h();
    }

    /**
     * {@return this duration in seconds}
     */
    public double s() {
        return object.s();
    }

    @NonNull
    public static ImmutableDuration fromJson(@NonNull Object o) {
        return Duration.fromJson(o).immutableCopy();
    }

    @NonNull
    @Override
    public Object toJson() {
        return object.toJson();
    }
}
