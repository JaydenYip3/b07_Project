package com.b07.planetze.database;

import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.Emissions;
import com.b07.planetze.util.Mass;
import com.b07.planetze.util.Result;

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
            UserId userId,
            LocalDate date,
            Consumer<Result<Emissions, DatabaseError>> callback
    ) {
        UserIdWithDate key = new UserIdWithDate(userId, date);
        callback.accept(new Result.Ok<>(Objects.requireNonNull(map.getOrDefault(key, new Emissions())).copy()));
    }

    @Override
    public void fetchEmissionsOverInterval(
            UserId userId,
            DateInterval interval,
            Consumer<Result<List<DatedEmissions>, DatabaseError>> callback
    ) {
        ArrayList<DatedEmissions> emissions = new ArrayList<>();

        for (Map.Entry<UserIdWithDate, Emissions> entry : map.entrySet()) {
            if (userId.equals(entry.getKey().userId()) && interval.contains(entry.getKey().date())) {
                emissions.add(new DatedEmissions(entry.getKey().date(), entry.getValue()));
            }
        }

        callback.accept(new Result.Ok<>(emissions));
    }

    @Override
    public void updateDailyEmissions(
            UserId userId,
            LocalDate date,
            Emissions emissions
    ) {
        map.put(new UserIdWithDate(userId, date), emissions.copy());
    }
}
