package com.b07.planetze.ecotracker.daily;

import androidx.annotation.NonNull;

public enum DailyType {
    DRIVING(DrivingForm.INSTANCE);

    @NonNull private final DailyForm form;

    DailyType(@NonNull DailyForm form) {
        this.form = form;
    }

    @NonNull
    public DailyForm form() {
        return form;
    }
}
