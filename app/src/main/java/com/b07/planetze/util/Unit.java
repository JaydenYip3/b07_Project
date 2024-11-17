package com.b07.planetze.util;

import androidx.annotation.NonNull;

/**
 * Represents a unit type (i.e., a type that allows only one value)
 * if null values are not permitted.
 */
public final class Unit {
    /**
     * The only instance of {@link Unit}.
     */
    @SuppressWarnings("all")
    @NonNull
    public static final Unit unit = new Unit();

    private Unit() {}
}
