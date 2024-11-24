package com.b07.planetze.ecotracker.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;

/**
 * A logged daily activity.
 */
public interface Daily {
    /**
     * {@return the CO2e emitted from this activity}
     */
    @NonNull
    Emissions emissions();

    /**
     * {@return the type of this activity}
     */
    @NonNull
    String type();
}
