package com.b07.planetze.ecotracker.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FormDefinition;

public interface DailyForm {
    @NonNull
    FormDefinition formDefinition();

    @NonNull
    Daily createDaily(@NonNull FormSubmission form);
}
