package com.b07.planetze.type;

/**
 * Represents a unit type if null values are not permitted.
 */
public final class Unit {
    /**
     * The only instance of <code>Unit</code>
     */
    @SuppressWarnings("all")
    public static final Unit unit = new Unit();

    private Unit() {}
}
