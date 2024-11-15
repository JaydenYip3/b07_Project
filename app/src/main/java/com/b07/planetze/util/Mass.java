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
     * Adds another mass to this mass.
     * @param other the mass to add
     */
    public void add(Mass other) {
        kg += other.kg;
    }

    /**
     * Subtracts another mass from this mass.
     * @param other the mass to subtract
     */
    public void subtract(Mass other) {
        kg -= other.kg;
    }

    /**
     * Multiplies this mass by a scalar.
     * @param scalar the multiplication factor
     */
    public void scale(double scalar) {
        kg *= scalar;
    }

    /**
     * Flips the sign of this mass.
     */
    public void negate() {
        kg = -kg;
    }

    /**
     * Sets the value of <code>this</code> given a value in kilograms.
     * @param kg a value in kilograms
     */
    public void setKg(double kg) {
        this.kg = kg;
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
