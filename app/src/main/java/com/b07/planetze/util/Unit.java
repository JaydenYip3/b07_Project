package com.b07.planetze.util;

/**
 * Represents a unit type (i.e., a type that allows only one value
 * and thus holds no information) if null values are not permitted. <br>
 * This is used to indicate that a generic type is useless; e.g., the type
 * <code>Result&lt;Unit, T&gt;</code> indicates that its <code>Ok</code>
 * variant does not hold anything.
 */
public enum Unit {
    /**
     * The only instance of {@link Unit}.
     */
    UNIT
}
