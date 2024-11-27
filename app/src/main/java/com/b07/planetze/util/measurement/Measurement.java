package com.b07.planetze.util.measurement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.Self;
import com.b07.planetze.util.immutability.MutableWithCopy;

/**
 * A newtype for units of measurement.
 */
public abstract class Measurement<T extends Measurement<T>>
        implements Comparable<T>, MutableWithCopy<T>, Self<T> {
    /**
     * {@return the value of this measurement}
     */
    protected abstract double getValue();

    /**
     * Sets the value of this measurement.
     * @param value the value to set
     */
    protected abstract void setValue(double value);

    /**
     * Sets this measurement to another measurement.
     * @param other the other measurement
     * @return <code>this</code>
     */
    @NonNull
    public T set(@NonNull T other) {
        setValue(other.getValue());
        return self();
    }

    /**
     * Adds another measurement to this measurement.
     * @param other the other measurement
     * @return <code>this</code>
     */
    @NonNull
    public T add(@NonNull T other) {
        setValue(getValue() + other.getValue());
        return self();
    }

    /**
     * Subtracts another measurement to this measurement.
     * @param other the other measurement
     * @return <code>this</code>
     */
    @NonNull
    public T subtract(@NonNull T other) {
        setValue(getValue() - other.getValue());
        return self();
    }

    /**
     * Scales this measurement by a scalar.
     * @param scalar the scalar
     * @return <code>this</code>
     */
    @NonNull
    public T scale(double scalar) {
        setValue(getValue() * scalar);
        return self();
    }

    /**
     * Flips the sign of this measurement.
     * @return <code>this</code>
     */
    @NonNull
    public T negate() {
        setValue(-getValue());
        return self();
    }

    @Override
    public abstract boolean equals(@Nullable Object o);

    @Override
    public int compareTo(@NonNull T o) {
        return Double.compare(getValue(), o.getValue());
    }

    @Override
    public int hashCode() {
        return Double.hashCode(getValue());
    }
}
