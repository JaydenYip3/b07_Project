package com.b07.planetze.util;

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
}
