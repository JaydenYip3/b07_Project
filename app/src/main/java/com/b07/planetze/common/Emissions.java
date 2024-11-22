package com.b07.planetze.common;

import androidx.annotation.NonNull;

import com.b07.planetze.common.measurement.Mass;
import com.b07.planetze.util.Measurement;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.immutability.MutableWithCopy;

import java.util.List;

/**
 * Stores CO2e emissions by category.
 */
public class Emissions implements MutableWithCopy<Emissions> {
    @NonNull private final ImmutableList<Mass> categories;

    /**
     * Creates a new {@link Emissions} where all emission categories are zero.
     */
    public Emissions() {
        List<Mass> list = Util.filledArrayList(4, Mass::zero);
        categories = new ImmutableList<>(list);
    }

    @NonNull
    @Override
    public Emissions copy() {
        Emissions emissions = new Emissions();
        Util.zip(emissions.categories, categories).forEach(Mass::set);
        return emissions;
    }

    /**
     * Gets a mutable reference to transportation emissions.
     * @return a mutable reference to transportation emissions
     */
    @NonNull
    public Mass transportation() {
        return categories.get(0);
    }

    /**
     * Gets a mutable reference to energy use emissions.
     * @return a mutable reference to energy use emissions
     */
    @NonNull
    public Mass energy() {
        return categories.get(1);
    }

    /**
     * Gets a mutable reference to food consumption emissions.
     * @return a mutable reference to food consumption emissions
     */
    @NonNull
    public Mass food() {
        return categories.get(2);
    }

    /**
     * Gets a mutable reference to shopping emissions.
     * @return a mutable reference to shopping emissions
     */
    @NonNull
    public Mass shopping() {
        return categories.get(3);
    }

    /**
     * Computes the sum of emissions across all categories.
     * @return a new {@link Mass} - the sum of emissions across all categories
     */
    @NonNull
    public Mass total() {
        Mass sum = Mass.zero();
        categories.forEach(sum::add);
        return sum;
    }

    /**
     * Sets the values of <code>this</code> to the values of
     * another {@link Emissions}.
     * @param other the {@link Emissions} to set <code>this</code> to
     * @return <code>this</code>
     */
    @NonNull
    public Emissions set(@NonNull Emissions other) {
        Util.zip(categories, other.categories).forEach(Mass::set);
        return this;
    }

    /**
     * Adds (category-wise) another {@link Emissions}
     * to <code>this</code>.
     * @param other the {@link Emissions} to add
     * @return <code>this</code>
     */
    @NonNull
    public Emissions add(@NonNull Emissions other) {
        Util.zip(categories, other.categories).forEach(Mass::add);
        return this;
    }

    /**
     * Subtracts (category-wise) another {@link Emissions}
     * from <code>this</code>.
     * @param other the {@link Emissions} to subtract
     * @return <code>this</code>
     */
    @NonNull
    public Emissions subtract(@NonNull Emissions other) {
        Util.zip(categories, other.categories).forEach(Mass::subtract);
        return this;
    }

    /**
     * Multiplies the emissions of all categories by a scalar.
     * @param scalar the scaling factor
     * @return <code>this</code>
     */
    @NonNull
    public Emissions scale(double scalar) {
        categories.forEach(c -> c.scale(scalar));
        return this;
    }

    /**
     * Flips the sign of the emissions of all categories.
     * @return <code>this</code>
     */
    @NonNull
    public Emissions negate() {
        categories.forEach(Mass::negate);
        return this;
    }

    /**
     * Sets the emissions of all categories to zero.
     * @return <code>this</code>
     */
    @NonNull
    public Emissions zero() {
        categories.forEach(c -> c.set(Mass.zero()));
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Emissions[transportation=%s, energy=%s, food=%s, shopping=%s]",
                transportation(),
                energy(),
                food(),
                shopping());
    }

    @NonNull
    @Override
    public Emissions self() {
        return this;
    }
}
