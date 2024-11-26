package com.b07.planetze.database;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.common.daily.Daily;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.data.DailyId;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;

import java.time.LocalDate;
import java.util.function.Consumer;

public interface Database {
    void postUser(
            @NonNull User user,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void fetchUser(
            @NonNull Consumer<Result<Option<User>, DatabaseError>> callback
    );

    void postOnboardingEmissions(
            @NonNull Emissions emissions,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void fetchOnboardingEmissions(
            @NonNull Consumer<Result<Option<Emissions>, DatabaseError>> callback
    );

    void postDaily(
            @NonNull LocalDate date,
            @NonNull Daily daily,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void updateDaily(
            @NonNull DailyId id,
            @NonNull Daily update,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void deleteDaily(
            @NonNull DailyId id,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    );

    void fetchDailies(
            @NonNull DateInterval interval,
            @NonNull Consumer<Result<DailyFetchList, DatabaseError>> callback
    );
}
