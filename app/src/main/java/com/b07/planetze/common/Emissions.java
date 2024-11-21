package com.b07.planetze.common;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.MutableWithCopy;

/**
 * Stores CO2e emissions by category.
 */
public class Emissions implements MutableWithCopy<Emissions> {
    private Mass[] categories;

    /**
     * Creates a new {@link Emissions} where all emission categories are zero.
     */
    public Emissions() {
        categories = new Mass[4];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = new Mass();
        }
    }

    @NonNull
    @Override
    public Emissions copy() {
        Emissions emissions = new Emissions();
        for (int i = 0; i < categories.length; i++) {
            emissions.categories[i].set(categories[i]);
        }
        return emissions;
    }

    /**
     * Gets a mutable reference to transportation emissions.
     * @return a mutable reference to transportation emissions
     */
    @NonNull
    public Mass transportation() {
        return categories[0];
    }

    /**
     * Gets a mutable reference to energy use emissions.
     * @return a mutable reference to energy use emissions
     */
    @NonNull
    public Mass energy() {
        return categories[1];
    }

    /**
     * Gets a mutable reference to food consumption emissions.
     * @return a mutable reference to food consumption emissions
     */
    @NonNull
    public Mass food() {
        return categories[2];
    }

    /**
     * Gets a mutable reference to shopping emissions.
     * @return a mutable reference to shopping emissions
     */
    @NonNull
    public Mass shopping() {
        return categories[3];
    }

    /**
     * Computes the sum of emissions across all categories.
     * @return a new {@link Mass} - the sum of emissions across all categories
     */
    @NonNull
    public Mass total() {
        Mass sum = new Mass();
        for (Mass category : categories) {
            sum.add(category);
        }
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
        for (int i = 0; i < categories.length; i++) {
            categories[i].set(other.categories[i]);
        }
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
        for (int i = 0; i < categories.length; i++) {
            categories[i].add(other.categories[i]);
        }
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
        for (int i = 0; i < categories.length; i++) {
            categories[i].subtract(other.categories[i]);
        }
        return this;
    }

    /**
     * Multiplies the emissions of all categories by a scalar.
     * @param scalar the scaling factor
     * @return <code>this</code>
     */
    @NonNull
    public Emissions scale(double scalar) {
        for (Mass category : categories) {
            category.scale(scalar);
        }
        return this;
    }

    /**
     * Flips the sign of the emissions of all categories.
     * @return <code>this</code>
     */
    @NonNull
    public Emissions negate() {
        for (Mass category : categories) {
            category.negate();
        }
        return this;
    }

    /**
     * Sets the emissions of all categories to zero.
     * @return <code>this</code>
     */
    @NonNull
    public Emissions zero() {
        for (Mass category : categories) {
            category.zero();
        }
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
