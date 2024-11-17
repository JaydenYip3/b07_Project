package com.b07.planetze.database;

import com.b07.planetze.util.Emissions;

import java.time.LocalDate;

/**
 * Stores an amount of CO2e emissions with the date they were emitted
 * @param date the date of the emissions
 * @param emissions the mass of CO2e emissions
 */
public record DatedEmissions(LocalDate date, Emissions emissions) {}
