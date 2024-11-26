package com.b07.planetze.database;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.data.DailyId;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * Interfaces with the database backend. <br>
 * All methods operate on the currently signed in user.
 */
public interface Database {
    /**
     * Sets user information.
     * @param callback a function that takes in a {@link Result}. <br>
     *                 This is called upon completion of the operation (of
     *                 setting user information).
     */
    void postUser(
            @NonNull User user,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    /**
     * Fetches user information. <br>
     * Example:
     * <pre>
     *     {@code
     *     fetchUser(result -> {
     *         if (result instanceof Ok<Option<User>, ?> r) { // if this operation was successful
     *             Option<User> userOption = r.get();
     *             if (userOption instanceof Some<User> user) { // if the user has user info set
     *                 Log.d(TAG, "user: " + user);
     *             } else {
     *                 Log.d(TAG, "user nonexistent");
     *             }
     *         } else { // if this operation failed
     *             DatabaseError e = ((Error<?, DatabaseError>) result).get();
     *             Log.d(TAG, "error: " + e);
     *         }
     *     });
     *     }
     * </pre>
     * Alternate syntax:
     * <pre>
     *     {@code
     *     fetchUser(result -> {
     *         result.match(userOption -> { // if this operation was successful:
     *             userOption.match(
     *                 // if the user has user info set:
     *                 user -> Log.d(TAG, "user: " + user),
     *
     *                 // otherwise:
     *                 () -> Log.d(TAG, "user nonexistent")
     *             );
     *         }, dbError -> { // if this operation failed:
     *             Log.d(TAG, "error: " + dbError);
     *         });
     *     });
     *     }
     * </pre>
     */
    void fetchUser(@NonNull Consumer<Result<Option<User>, DatabaseError>> callback);

    void postOnboardingEmissions(
            @NonNull Emissions emissions,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void fetchOnboardingEmissions(@NonNull Consumer<Result<Option<Emissions>, DatabaseError>> callback);

    void postDaily(
            @NonNull LocalDate date,
            @NonNull Daily daily,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    /**
     * Updates (edits) a previously-logged daily activity.
     * @param id the activity id
     * @param update the updated daily activity log
     */
    void updateDaily(
            @NonNull DailyId id,
            @NonNull Daily update,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void deleteDaily(
            @NonNull DailyId id,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    /**
     * Fetches a list of daily activities (and by extension emissions) over an
     * interval. <br>
     * Example:
     * <pre>
     *     {@code
     *     fetchDailies(DateInterval.day(LocalDate.now()), result -> {
     *         if (result instanceof Ok<DailyFetchList, ?> r) {
     *             DailyFetchList list = r.get();
     *             Emissions emissions = list.emissions();
     *             Log.d(TAG, "emissions: " + emissions);
     *
     *             for (DailyFetch fetch : list) {
     *                 Log.d(TAG, "id: " + fetch.id());
     *                 Log.d(TAG, "date: " + fetch.date());
     *                 Log.d(TAG, "daily: " + fetch.daily());
     *             }
     *         }
     *     });
     *     }
     * </pre>
     */
    void fetchDailies(
            @NonNull DateInterval interval,
            @NonNull Consumer<Result<DailyFetchList, DatabaseError>> callback
    );
}
