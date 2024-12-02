package com.b07.planetze.ecotracker;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.DailyType;
import com.b07.planetze.database.data.DailyFetch;

public sealed interface FormAction {
    @NonNull
    String formTitle();

    record New(@NonNull DailyType type) implements FormAction {
        @NonNull
        public String formTitle() {
            return "New activity";
        }
    }

    record Edit(@NonNull DailyFetch fetch) implements FormAction {
        @NonNull
        public String formTitle() {
            return "Edit activity";
        }
    }
}
