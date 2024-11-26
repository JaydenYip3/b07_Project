package com.b07.planetze.util.measurement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.database.ToJson;
import com.b07.planetze.util.Util;

/**
 * A measurement of distance.
 */
public final class Distance extends Measurement<Distance>
        implements ToJson {
    private static final double MILES_TO_METRES = 1609.344;
    private static final double METRES_TO_MILES = 1 / MILES_TO_METRES;

    private double m;

    private Distance(double m) {
        this.m = m;
    }

    /**
     * {@return a zero-distance}
     */
    @NonNull
    public static Distance zero() {
        return new Distance(0);
    }

    /**
     * {@return a new distance given metres}
     * @param m a value in metres
     */
    @NonNull
    public static Distance m(double m) {
        return new Distance(m);
    }

    /**
     * {@return a new distance given kilometres}
     * @param km a value in kilometres
     */
    @NonNull
    public static Distance km(double km) {
        return new Distance(km / 1000);
    }

    /**
     * {@return a new distance given miles}
     * @param mi a value in miles
     */
    @NonNull
    public static Distance mi(double mi) {
        return new Distance(mi * MILES_TO_METRES);
    }

    /**
     * {@return this distance in metres}
     */
    public double m() {
        return m;
    }

    /**
     * {@return this distance in kilometres}
     */
    public double km() {
        return 1000 * m;
    }

    public double mi() {
        return METRES_TO_MILES * m;
    }

    @Override
    protected double getValue() {
        return m;
    }

    @Override
    protected void setValue(double value) {
        m = value;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof Distance d) && (m == d.m);
    }

    @NonNull
    @Override
    public String toString() {
        return m + "m";
    }

    @NonNull
    @Override
    public Distance copy() {
        return new Distance(m);
    }

    @NonNull
    @Override
    public ImmutableDistance immutableCopy() {
        return new ImmutableDistance(this);
    }

    @NonNull
    @Override
    public Distance self() {
        return this;
    }

    @NonNull
    public static Distance fromJson(@NonNull Object o) {
        return new Distance(Util.toDouble(o));
    }

    @NonNull
    @Override
    public Object toJson() {
        return m;
    }
}
