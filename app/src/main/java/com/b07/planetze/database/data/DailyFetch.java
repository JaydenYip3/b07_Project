package com.b07.planetze.database.data;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.Daily;

import java.time.LocalDate;

/**
 * A {@link Daily} with an added id and date.
 */
public record DailyFetch(
        @NonNull DailyId id,
        @NonNull LocalDate date,
        @NonNull Daily daily
) {
    @NonNull DailySummary summary() {
        return new DailySummary(id, date, daily.type(), daily.emissions());
    }
}
