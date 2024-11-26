package com.b07.planetze.util.measurement;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJsonSerializable;
import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Mass}. <br>
 * Consider instantiating this with {@link Mass#immutableCopy()}.
 */
public final class ImmutableMass extends ImmutableCopy<Mass>
        implements ToJsonSerializable {
    public ImmutableMass(@NonNull Mass object) {
        super(object);
    }

    /**
     * {@return this mass in kilograms}
     */
    public double kg() {
        return object.kg();
    }

    /**
     * {@return this mass in grams}
     */
    public double g() {
        return object.g();
    }

    /**
     * {@return this mass in pounds}
     */
    public double lb() {
        return object.lb();
    }

    @NonNull
    public static ImmutableMass fromJson(Object o) {
        return Mass.fromJson(o).immutableCopy();
    }

    @NonNull
    @Override
    public Object toJson() {
        return object.toJson();
    }
}
