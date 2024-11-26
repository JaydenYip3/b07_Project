package com.b07.planetze.common.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;

import java.time.LocalDate;

/**
 * The details of a daily activity.
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
    DailyType type();
}
