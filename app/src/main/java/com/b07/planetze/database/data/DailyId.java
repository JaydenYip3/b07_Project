package com.b07.planetze.database.data;

import androidx.annotation.NonNull;

/**
 * Uniquely identifies a logged daily activity stored in a database.
 */
public record DailyId(@NonNull String id) implements Comparable<DailyId> {
    @Override
    public int compareTo(@NonNull DailyId o) {
        return id.compareTo(o.id);
    }
}
