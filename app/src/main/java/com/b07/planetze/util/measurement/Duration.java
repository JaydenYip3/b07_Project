package com.b07.planetze.util.measurement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.database.ToJson;
import com.b07.planetze.util.Util;

/**
 * A measurement of duration.
 */
public final class Duration extends Measurement<Duration>
        implements ToJson {
    private double s;

    private Duration(double s) {
        this.s = s;
    }

    /**
     * {@return a zero-duration}
     */
    @NonNull
    public static Duration zero() {
        return new Duration(0);
    }

    /**
     * {@return a new duration given seconds}
     * @param s a value in seconds
     */
    @NonNull
    public static Duration s(double s) {
        return new Duration(s);
    }

    /**
     * {@return a new duration given hours}
     * @param h a value in hours
     */
    @NonNull
    public static Duration h(double h) {
        return new Duration(3600 * h);
    }

    /**
     * {@return this duration in seconds}
     */
    public double s() {
        return s;
    }

    /**
     * {@return this duration in hours}
     */
    public double h() {
        return s / 3600;
    }

    @Override
    protected double getValue() {
        return s;
    }

    @Override
    protected void setValue(double value) {
        s = value;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof Duration d) && (s == d.s);
    }

    @NonNull
    @Override
    public Duration copy() {
        return new Duration(s);
    }

    @NonNull
    @Override
    public ImmutableDuration immutableCopy() {
        return new ImmutableDuration(this);
    }

    @NonNull
    @Override
    public Duration self() {
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return s + "s";
    }

    @NonNull
    public static Duration fromJson(@NonNull Object o) {
        return new Duration(Util.toDouble(o));
    }

    @NonNull
    @Override
    public Object toJson() {
        return s;
    }
}
