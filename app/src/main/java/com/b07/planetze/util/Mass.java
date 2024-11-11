package com.b07.planetze.util;

/**
 * A measurement of mass.
 */
public class Mass {
    private double kg;

    private Mass() {}

    /**
     * Creates a new <code>Mass</code> given kilograms.
     * @param kg a value in kilograms
     * @return a new <code>Mass</code>
     */
    public static Mass fromKg(double kg) {
        Mass mass = new Mass();
        mass.kg = kg;
        return mass;
    }

    /**
     * Sets the value of <code>this</code> given a value in kilograms.
     * @param kg a value in kilograms
     */
    public void setKg(double kg) {
        this.kg = kg;
    }

    /**
     * Gets the value of <code>this</code> in kilograms.
     * @return the value of <code>this</code> in kilograms
     */
    public double getKg() {
        return kg;
    }
}
