package com.b07.planetze.database;

import androidx.annotation.NonNull;

import com.b07.planetze.common.DateInterval;
import com.b07.planetze.common.DatedEmissions;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.UserId;
import com.b07.planetze.util.Ok;
import com.b07.planetze.util.Result;
import com.b07.planetze.util.Unit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A local "database" for the purpose of testing.
 */
public class FakeDatabase implements Database {
    private final HashMap<UserIdWithDate, Emissions> map;

    private record UserIdWithDate(UserId userId, LocalDate date) {}

    public FakeDatabase() {
        map = new HashMap<>();
    }

    @Override
    public void fetchDailyEmissions(
            @NonNull UserId userId,
            @NonNull LocalDate date,
            @NonNull Consumer<Result<Emissions, DatabaseError>> callback
    ) {
        UserIdWithDate key = new UserIdWithDate(userId, date);
        callback.accept(new Ok<>(Objects.requireNonNull(map.getOrDefault(key, new Emissions())).copy()));
    }

    @Override
    public void fetchEmissionsOverInterval(
            @NonNull UserId userId,
            @NonNull DateInterval interval,
            @NonNull Consumer<Result<List<DatedEmissions>, DatabaseError>> callback
    ) {
        ArrayList<DatedEmissions> emissions = new ArrayList<>();

        for (Map.Entry<UserIdWithDate, Emissions> entry : map.entrySet()) {
            if (userId.equals(entry.getKey().userId()) && interval.contains(entry.getKey().date())) {
                emissions.add(new DatedEmissions(entry.getKey().date(), entry.getValue()));
            }
        }

        callback.accept(new Ok<>(emissions));
    }

    @Override
    public void updateDailyEmissions(
            @NonNull UserId userId,
            @NonNull LocalDate date,
            @NonNull Emissions emissions,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        map.put(new UserIdWithDate(userId, date), emissions.copy());
        callback.accept(new Ok<>(Unit.INSTANCE));
    }
}
