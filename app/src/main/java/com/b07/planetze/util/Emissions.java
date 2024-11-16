package com.b07.planetze.util;

import androidx.annotation.NonNull;

/**
 * Stores CO2e emissions by category.
 */
public class Emissions {
    private Mass[] categories;

    /**
     * Creates a new <code>Emissions</code> where all emission categories are zero.
     */
    public Emissions() {
        categories = new Mass[4];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = new Mass();
        }
    }

    /**
     * Gets a mutable reference to transportation emissions.
     * @return a mutable reference to transportation emissions
     */
    public Mass transportation() {
        return categories[0];
    }

    /**
     * Gets a mutable reference to energy use emissions.
     * @return a mutable reference to energy use emissions
     */
    public Mass energy() {
        return categories[1];
    }

    /**
     * Gets a mutable reference to food consumption emissions.
     * @return a mutable reference to food consumption emissions
     */
    public Mass food() {
        return categories[2];
    }

    /**
     * Gets a mutable reference to shopping emissions.
     * @return a mutable reference to shopping emissions
     */
    public Mass shopping() {
        return categories[3];
    }

    /**
     * Computes the sum of emissions across all categories.
     * @return a new <code>Mass</code> - the sum of emissions across all categories
     */
    public Mass total() {
        Mass sum = new Mass();
        for (Mass category : categories) {
            sum.add(category);
        }
        return sum;
    }

    /**
     * Creates a deep copy of <code>this</code>.
     * @return a new <code>Emissions</code> of the same values
     */
    public Emissions copy() {
        Emissions emissions = new Emissions();
        for (int i = 0; i < categories.length; i++) {
            emissions.categories[i].set(categories[i]);
        }
        return emissions;
    }

    /**
     * Sets the values of <code>this</code> to the values of
     * another <code>Emissions</code>.
     * @param other the <code>Emissions</code> to set <code>this</code> to
     * @return <code>this</code>
     */
    public Emissions set(Emissions other) {
        for (int i = 0; i < categories.length; i++) {
            categories[i].set(other.categories[i]);
        }
        return this;
    }

    /**
     * Adds (category-wise) another <code>Emissions</code>
     * to <code>this</code>.
     * @param other the <code>Emissions</code> to add
     * @return <code>this</code>
     */
    public Emissions add(Emissions other) {
        for (int i = 0; i < categories.length; i++) {
            categories[i].add(other.categories[i]);
        }
        return this;
    }

    /**
     * Subtracts (category-wise) another <code>Emissions</code>
     * from <code>this</code>.
     * @param other the <code>Emissions</code> to subtract
     * @return <code>this</code>
     */
    public Emissions subtract(Emissions other) {
        for (int i = 0; i < categories.length; i++) {
            categories[i].subtract(other.categories[i]);
        }
        return this;
    }

    /**
     * Multiplies the emissions of all categories by a scalar.
     * @param scalar the multiplication factor
     * @return <code>this</code>
     */
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
}
