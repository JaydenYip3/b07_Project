package com.b07.planetze.ecotracker;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.DailyType;

/**
 * A sum type representing EcoTracker screens.
 */
public sealed interface EcoTrackerState {
    record ViewLogs() implements EcoTrackerState {}
    record Form(@NonNull DailyType dailyType) implements EcoTrackerState {}
}
