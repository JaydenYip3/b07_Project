package com.b07.planetze.common;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.MutableWithCopy;

/**
 * A measurement of mass.
 */
public class Mass implements Comparable<Mass>, MutableWithCopy<Mass> {
    private double kg;

    /**
     * Creates a new {@link Mass} with mass 0.
     */
    public Mass() {
        kg = 0;
    }

    /**
     * Creates a new {@link Mass} given kilograms.
     * @param kg a value in kilograms
     * @return a new {@link Mass}
     */
    @NonNull
    public static Mass fromKg(double kg) {
        Mass mass = new Mass();
        mass.kg = kg;
        return mass;
    }

    @NonNull
    @Override
    public Mass copy() {
        return Mass.fromKg(kg);
    }

    /**
     * Sets the value of this mass to that of another.
     * @param other the mass to set this mass to
     * @return <code>this</code>
     */
    @NonNull
    public Mass set(@NonNull Mass other) {
        kg = other.kg;
        return this;
    }

    /**
     * Adds another mass to this mass.
     * @param other the mass to add
     * @return <code>this</code>
     */
    @NonNull
    public Mass add(@NonNull Mass other) {
        kg += other.kg;
        return this;
    }

    /**
     * Subtracts another mass from this mass.
     * @param other the mass to subtract
     * @return <code>this</code>
     */
    @NonNull
    public Mass subtract(@NonNull Mass other) {
        kg -= other.kg;
        return this;
    }

    /**
     * Multiplies this mass by a scalar.
     * @param scalar the scaling factor
     * @return <code>this</code>
     */
    @NonNull
    public Mass scale(double scalar) {
        kg *= scalar;
        return this;
    }

    /**
     * Flips the sign of this mass.
     * @return <code>this</code>
     */
    @NonNull
    public Mass negate() {
        kg = -kg;
        return this;
    }

    /**
     * Sets this mass to zero.
     * @return <code>this</code>
     */
    @NonNull
    public Mass zero() {
        kg = 0;
        return this;
    }

    /**
     * Sets the value of <code>this</code> given a value in kilograms.
     * @param kg a value in kilograms
     * @return <code>this</code>
     */
    @NonNull
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
        if (o == null) {
            return false;
        }
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
    public int compareTo(@NonNull Mass o) {
        return Double.compare(kg, o.kg);
    }

    @NonNull
    @Override
    public String toString() {
        return kg + "kg";
    }
}
