package com.b07.planetze.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.Measurement;

/**
 * A measurement of distance.
 */
public class Distance extends Measurement<Distance> {
    private static final double milesToMetres;
    private static final double metresToMiles;

    static {
        milesToMetres = 1609.344;
        metresToMiles = 1 / milesToMetres;
    }

    private double m;

    /**
     * Instantiates a zero-distance.
     */
    public Distance() {
        this(0);
    }

    private Distance(double m) {
        this.m = m;
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
        return new Distance(mi * milesToMetres);
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
        return metresToMiles * m;
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
    public Distance self() {
        return this;
    }
}
