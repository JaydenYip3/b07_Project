package com.b07.planetze.type;

import androidx.annotation.NonNull;

/**
 * Represents a unit type (i.e., type that allows only one value)
 * iff null values are not permitted.
 */
public final class Unit {
    /**
     * The only instance of {@link Unit}
     */
    @SuppressWarnings("all")
    @NonNull
    public static final Unit unit = new Unit();

    private Unit() {}
}
