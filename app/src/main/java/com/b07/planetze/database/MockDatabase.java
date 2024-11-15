package com.b07.planetze.database;

import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.Mass;
import com.b07.planetze.util.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Local "database" for the purpose of testing.
 */
public class MockDatabase implements Database {
    private HashMap<UserIdWithDate, Mass> map;

    private record UserIdWithDate(UserId userId, LocalDate date) {}

    @Override
    public void fetchDailyEmissions(UserId userId, LocalDate date, DailyEmissionsCallback callback) {
        UserIdWithDate key = new UserIdWithDate(userId, date);
        callback.dailyEmissionsCallback(new Result.Ok<>(Objects.requireNonNull(map.getOrDefault(key, new Mass())).copy()));
    }

    @Override
    public void fetchEmissionsOverInterval(UserId userId, DateInterval interval, IntervalEmissionsCallback callback) {
        ArrayList<DatedEmissions> emissions = new ArrayList<>();

        for (Map.Entry<UserIdWithDate, Mass> entry : map.entrySet()) {
            if (userId.equals(entry.getKey().userId()) && interval.contains(entry.getKey().date())) {
                emissions.add(new DatedEmissions(entry.getKey().date(), entry.getValue()));
            }
        }

        callback.intervalEmissionsCallback(new Result.Ok<>(emissions));
    }

    @Override
    public void updateDailyEmissions(UserId userId, LocalDate date, Mass emissions) {
        map.put(new UserIdWithDate(userId, date), emissions.copy());
    }
}
