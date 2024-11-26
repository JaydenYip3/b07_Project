package com.b07.planetze.database.data;

import androidx.annotation.NonNull;

/**
 * Uniquely identifies a logged daily activity stored in a database.
 */
public class DailyId implements Comparable<DailyId> {
    @NonNull private final String id;
    public DailyId(@NonNull String id) {
        this.id = id;
    }

    public String get() {
        return id;
    }

    @Override
    public int compareTo(@NonNull DailyId o) {
        return id.compareTo(o.id);
    }
}
