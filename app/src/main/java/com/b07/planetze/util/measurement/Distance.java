package com.b07.planetze.util.measurement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.database.ToJson;
import com.b07.planetze.util.Util;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * A measurement of distance.
 */
public final class Distance extends Measurement<Distance>
        implements ToJson, Parcelable {
    private static final double MILES_TO_METRES = 1609.344;
    private static final double METRES_TO_MILES = 1 / MILES_TO_METRES;

    public enum Unit {
        KM,
        M,
        MI
    }

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
        return new Distance(km * 1000);
    }

    /**
     * {@return a new distance given miles}
     * @param mi a value in miles
     */
    @NonNull
    public static Distance mi(double mi) {
        return new Distance(mi * MILES_TO_METRES);
    }

    @NonNull
    public static Distance withUnit(Unit unit, double value) {
        return switch (unit) {
            case KM -> km(value);
            case M -> m(value);
            case MI -> mi(value);
        };
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
        return m / 1000;
    }

    public double mi() {
        return METRES_TO_MILES * m;
    }

    public double get(Unit unit) {
        return switch (unit) {
            case KM -> km();
            case M -> m();
            case MI -> mi();
        };
    }

    @Override
    protected double getValue() {
        return m;
    }

    @Override
    protected void setValue(double value) {
        m = value;
    }

    @NonNull
    public String format() {
        if (m() < 10) {
            return String.format(Locale.US, "%.1fm", m());
        } else if (m() < 1000) {
            return String.format(Locale.US, "%.0fm", m());
        } else if (km() < 10) {
            return String.format(Locale.US, "%.1fkm", km());
        }
        return String.format(Locale.US, "%.0fkm", km());
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

    public static final Parcelable.Creator<Distance> CREATOR
            = new Parcelable.Creator<>() {
        public Distance createFromParcel(Parcel in) {
            return new Distance(in.readDouble());
        }

        public Distance[] newArray(int size) {
            return new Distance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(m);
    }
}
