package com.b07.planetze.database;

import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.Emissions;
import com.b07.planetze.util.Mass;
import com.b07.planetze.util.Result;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

/**
 * A database. <br>
 * Example usage:
 * <pre>
 *     {@code
 *     db.fetchDailyEmissions(userId, date, result -> {
 *         result.match(emissions -> {
 *             Log.d(TAG, emissions.toString());
 *         }, error -> {
 *             Log.e(TAG, error.message());
 *         });
 *     });
 * </pre>
 */
public interface Database {
    /**
     * Fetches daily CO2e emissions for a specific user and date. <br>
     * Days with no recorded emissions are considered to have an emission mass of 0kg.
     * @param userId the user's id
     * @param date the date
     * @param callback a callback that takes in the mass of CO2e emissions on the
     *                 specified date if the fetch was successful, or an error otherwise
     */
    void fetchDailyEmissions(
            UserId userId,
            LocalDate date,
            Consumer<Result<Emissions, DatabaseError>> callback
    );

    /**
     * Fetches daily CO2e emissions over an interval of dates. <br>
     * Days with no recorded emissions are skipped.
     * @param userId the user's id
     * @param interval the interval of dates
     * @param callback a callback that takes in a list of the mass and date of CO2e emissions
     *                 over the specified interval if the fetch was successful, or an error
     *                 otherwise
     */
    void fetchEmissionsOverInterval(
            UserId userId,
            DateInterval interval,
            Consumer<Result<List<DatedEmissions>, DatabaseError>> callback
    );

    /**
     * Updates daily CO2e emissions for a specific user and date.
     * @param userId the user's id
     * @param date the date of the emissions
     * @param emissions the mass of CO2e emitted
     */
    void updateDailyEmissions(
            UserId userId,
            LocalDate date,
            Emissions emissions
    );
}
