package com.b07.planetze.database.data;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.daily.Daily;
import com.b07.planetze.common.daily.DailyType;

import java.time.LocalDate;

/**
 * A {@link Daily} with an added id and date.
 */
public record DailyFetch(
        @NonNull DailyId id,
        @NonNull LocalDate date,
        @NonNull Daily daily
) {
}
