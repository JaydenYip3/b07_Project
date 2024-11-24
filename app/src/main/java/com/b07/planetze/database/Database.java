package com.b07.planetze.database;

import androidx.annotation.NonNull;

import com.b07.planetze.util.DateInterval;
import com.b07.planetze.common.DatedEmissions;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.UserId;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

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
     * @param callback A function to be called after the data is fetched.
     *                 If fetching is successful, the result contains the
     *                 emissions on the specified date.
     */
    void fetchDailyEmissions(
            @NonNull UserId userId,
            @NonNull LocalDate date,
            @NonNull Consumer<Result<Emissions, DatabaseError>> callback
    );

    /**
     * Fetches daily CO2e emissions over an interval of dates. <br>
     * Days with no recorded emissions are skipped.
     * @param userId the user's id
     * @param interval the interval of dates
     * @param callback A function to be called after the data is fetched.
     *                 If fetching is successful, the result contains
     *                 dated emissions over the specified interval.
     */
    void fetchEmissionsOverInterval(
            @NonNull UserId userId,
            @NonNull DateInterval interval,
            @NonNull Consumer<Result<List<DatedEmissions>, DatabaseError>> callback
    );

    /**
     * Updates daily CO2e emissions for a specific user and date.
     * @param userId the user's id
     * @param date the date of the emissions
     * @param emissions the emitted CO2e
     * @param callback a function to be called after the update finishes
     */
    void updateDailyEmissions(
            @NonNull UserId userId,
            @NonNull LocalDate date,
            @NonNull Emissions emissions,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );
}
