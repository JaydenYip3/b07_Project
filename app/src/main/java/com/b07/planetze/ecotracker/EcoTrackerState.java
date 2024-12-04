package com.b07.planetze.ecotracker;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.DailyType;

/**
 * A sum type representing EcoTracker screens.
 */
public sealed interface EcoTrackerState {
    record Home() implements EcoTrackerState {}
    record ViewLogs(boolean isBackFromForm) implements EcoTrackerState {}
    record Form(@NonNull FormAction action) implements EcoTrackerState {}
}
