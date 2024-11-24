package com.b07.planetze.ecotracker.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FormDefinition;

/**
 * The form associated with a logged daily activity.
 */
public interface DailyForm<T extends Daily> {
    @NonNull
    FormDefinition definition();

    @NonNull
    T createDaily(@NonNull FormSubmission form);
}
