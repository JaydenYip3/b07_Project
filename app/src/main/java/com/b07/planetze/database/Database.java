package com.b07.planetze.database;

import androidx.annotation.NonNull;

import com.b07.planetze.common.daily.Daily;
import com.b07.planetze.database.data.DailyReference;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

import java.util.function.Consumer;

/**
 * Interacts with the database backend.
 */
public interface Database {
    void postDaily(
            @NonNull Daily daily,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void updateDaily(
            @NonNull DailyReference ref,
            @NonNull Daily update,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void deleteDaily(
            @NonNull DailyReference ref,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void fetchDailies(
            @NonNull DateInterval interval
    );
}
