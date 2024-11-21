package com.b07.planetze.common;

import androidx.annotation.NonNull;

import com.b07.planetze.util.Measurement;

/**
 * A measurement of mass.
 */
public final class Mass extends Measurement<Mass> {
    private double kg;

    /**
     * Instantiates a zero-mass.
     */
    public Mass() {
        this(0);
    }

    private Mass(double kg) {
        this.kg = kg;
    }

    /**
     * {@return a new mass given kilograms}
     * @param kg a value in kilograms
     */
    @NonNull
    public static Mass kg(double kg) {
        return new Mass(kg);
    }

    /**
     * {@return this mass in kilograms}
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
        return new Mass(kg);
    }

    @NonNull
    @Override
    public Mass self() {
        return this;
    }
}
