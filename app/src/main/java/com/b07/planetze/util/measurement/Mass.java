package com.b07.planetze.util.measurement;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJson;

/**
 * A measurement of mass.
 */
public final class Mass extends Measurement<Mass>
        implements ToJson {
    private static final double POUNDS_TO_KG = 	0.45359237;
    private static final double KG_TO_POUNDS = 1 / POUNDS_TO_KG;

    private double kg;

    private Mass(double kg) {
        this.kg = kg;
    }

    /**
     * {@return a zero-mass}
     */
    @NonNull
    public static Mass zero() {
        return new Mass(0);
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
     * {@return a new mass given grams}
     * @param g a value in grams
     */
    @NonNull
    public static Mass g(double g) {
        return new Mass(g / 1000);
    }

    /**
     * {@return a new mass given pounds}
     * @param lb a value in pounds
     */
    @NonNull
    public static Mass lb(double lb) {
        return new Mass(lb * POUNDS_TO_KG);
    }

    /**
     * {@return this mass in kilograms}
     */
    public double kg() {
        return kg;
    }

    /**
     * {@return this mass in grams}
     */
    public double g() {
        return kg * 1000;
    }

    /**
     * {@return this mass in pounds}
     */
    public double lb() {
        return kg * KG_TO_POUNDS;
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
    public Mass copy() {
        return new Mass(kg);
    }

    @NonNull
    @Override
    public ImmutableMass immutableCopy() {
        return new ImmutableMass(this);
    }

    @NonNull
    @Override
    public Mass self() {
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return kg + "kg";
    }

    @NonNull
    public static Mass fromJson(@NonNull Object o) {
        return new Mass((double) o);
    }

    @NonNull
    @Override
    public Object toJson() {
        return kg;
    }
}
