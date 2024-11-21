package com.b07.planetze.common;

import androidx.annotation.NonNull;

import com.b07.planetze.util.Measurement;
import com.b07.planetze.util.immutability.MutableWithCopy;

/**
 * A measurement of mass.
 */
public final class Mass extends Measurement<Mass> {
    private double kg;

    /**
     * Creates a new mass with mass 0.
     */
    public Mass() {
        kg = 0;
    }

    /**
     * {@return a new mass given a value in kilograms}
     * @param kg a value in kilograms
     */
    @NonNull
    public static Mass kg(double kg) {
        Mass mass = new Mass();
        mass.kg = kg;
        return mass;
    }

    /**
     * Gets the value of <code>this</code> in kilograms.
     * @return the value of <code>this</code> in kilograms
     */
    public double kg() {
        return kg;
    }

    @Override
    protected double getValue() {
        return kg;
    }

    @Override
    protected void setValue(double value) {
        kg = value;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Mass m) && (kg == m.kg);
    }

    @NonNull
    @Override
    public String toString() {
        return kg + "kg";
    }

    @NonNull
    @Override
    public Mass copy() {
        return Mass.kg(kg);
    }

    @NonNull
    @Override
    public Mass self() {
        return this;
    }
}
