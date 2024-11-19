package com.b07.planetze.ecotracker;

import com.b07.planetze.common.Emissions;

/**
 * A logged daily activity.
 */
public abstract class Daily {
    /**
     * Gets the CO2e emitted from this activity.
     * @return the mass of CO2e emissions
     */
    abstract Emissions emissions();

    /**
     * Gets the name of this activity.
     * @return the name of this activity
     */
    abstract String name();
}
