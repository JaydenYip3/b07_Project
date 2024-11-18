package com.b07.planetze.common;

import androidx.annotation.NonNull;

import java.time.LocalDate;

/**
 * Stores an amount of CO2e emissions with the date they were emitted.
 * @param date the date of the emissions
 * @param emissions the mass of CO2e emissions
 */
public record DatedEmissions(@NonNull LocalDate date, @NonNull Emissions emissions) {}
