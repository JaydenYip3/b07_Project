package com.b07.planetze.database.data;

import androidx.annotation.NonNull;

import com.b07.planetze.common.daily.Daily;

/**
 * A logged daily activity fetched from a database.
 */
public record FetchedDaily(
        @NonNull DailyReference reference,
        @NonNull Daily daily
) {}
