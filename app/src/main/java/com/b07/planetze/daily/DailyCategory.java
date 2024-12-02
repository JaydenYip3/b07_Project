package com.b07.planetze.daily;

import androidx.annotation.NonNull;

public enum DailyCategory {
    TRANSPORT("Transportation"),
    ENERGY("Energy"),
    FOOD("Food consumption"),
    SHOPPING("Shopping");

    @NonNull private final String displayName;

    DailyCategory(@NonNull String displayName) {
        this.displayName = displayName;
    }

    @NonNull
    public String displayName() {
        return displayName;
    }
}
