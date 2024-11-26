package com.b07.planetze.util.measurement;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJsonSerializable;
import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Distance}. <br>
 * Consider instantiating this with {@link Distance#immutableCopy()}.
 */
public final class ImmutableDistance extends ImmutableCopy<Distance>
        implements ToJsonSerializable {
    public ImmutableDistance(@NonNull Distance object) {
        super(object);
    }

    /**
     * {@return this distance in kilometres}
     */
    public double km() {
        return object.km();
    }

    /**
     * {@return this distance in metres}
     */
    public double m() {
        return object.m();
    }

    /**
     * {@return this distance in miles}
     */
    public double mi() {
        return object.mi();
    }

    @NonNull
    public static ImmutableDistance fromJson(Object o) {
        return Distance.fromJson(o).immutableCopy();
    }

    @NonNull
    @Override
    public Object toJson() {
        return object.toJson();
    }
}
