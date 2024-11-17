package com.b07.planetze.util;

import androidx.annotation.NonNull;

/**
 * A measurement of mass.
 */
public class Mass implements Comparable<Mass> {
    private double kg;

    /**
     * Creates a new <code>Mass</code> with mass 0.
     */
    public Mass() {
        kg = 0;
    }

    /**
     * Creates a new <code>Mass</code> given kilograms.
     * @param kg a value in kilograms
     * @return a new <code>Mass</code>
     */
    public static Mass fromKg(double kg) {
        Mass mass = new Mass();
        mass.kg = kg;
        return mass;
    }

    /**
     * Creates a deep copy of this mass.
     * @return a new <code>Mass</code> of the same value
     */
    public Mass copy() {
        return Mass.fromKg(kg);
    }

    /**
     * Sets the value of this mass to that of another.
     * @param other the mass to set this mass to
     * @return <code>this</code>
     */
    public Mass set(Mass other) {
        kg = other.kg;
        return this;
    }

    /**
     * Adds another mass to this mass.
     * @param other the mass to add
     * @return <code>this</code>
     */
    public Mass add(Mass other) {
        kg += other.kg;
        return this;
    }

    /**
     * Subtracts another mass from this mass.
     * @param other the mass to subtract
     * @return <code>this</code>
     */
    public Mass subtract(Mass other) {
        kg -= other.kg;
        return this;
    }

    /**
     * Multiplies this mass by a scalar.
     * @param scalar the multiplication factor
     * @return <code>this</code>
     */
    public Mass scale(double scalar) {
        kg *= scalar;
        return this;
    }

    /**
     * Flips the sign of this mass.
     * @return <code>this</code>
     */
    public Mass negate() {
        kg = -kg;
        return this;
    }

    /**
     * Sets this mass to zero.
     * @return <code>this</code>
     */
    public Mass zero() {
        kg = 0;
        return this;
    }

    /**
     * Sets the value of <code>this</code> given a value in kilograms.
     * @param kg a value in kilograms
     * @return <code>this</code>
     */
    public Mass setKg(double kg) {
        this.kg = kg;
        return this;
    }

    /**
     * Gets the value of <code>this</code> in kilograms.
     * @return the value of <code>this</code> in kilograms
     */
    public double getKg() {
        return kg;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Mass other) {
            return kg == other.kg;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(kg);
    }

    @Override
    public int compareTo(Mass o) {
        return Double.compare(kg, o.kg);
    }

    @NonNull
    @Override
    public String toString() {
        return kg + "kg";
    }
}
