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
 * A measurement of duration.
 */
public final class Duration extends Measurement<Duration>
        implements ToJson, Parcelable {
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
     *
     * @param s a value in seconds
     */
    @NonNull
    public static Duration s(double s) {
        return new Duration(s);
    }

    /**
     * {@return a new duration given minutes}
     *
     * @param mins a value in minutes
     */
    @NonNull
    public static Duration mins(double mins) {
        return new Duration(60 * mins);
    }

    /**
     * {@return a new duration given hours}
     *
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

    public double mins() {
        return s / 60;
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

    @NonNull
    public String format() {
        long s = (long) this.s;
        if (s < 60) {
            return s + "s";
        } else if (s < 3600) {
            return String.format(Locale.US, "%dm%02ds", s/60, s % 60);
        }
        return String.format(
                Locale.US, "%dh%02dm%02ds", s/3600, (s % 3600)/60, (s % 60));
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

    public static final Parcelable.Creator<Duration> CREATOR
            = new Parcelable.Creator<>() {
        public Duration createFromParcel(Parcel in) {
            return new Duration(in.readDouble());
        }

        public Duration[] newArray(int size) {
            return new Duration[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(s);
    }
}