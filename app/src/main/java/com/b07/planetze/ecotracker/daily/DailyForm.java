package com.b07.planetze.ecotracker.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FormDefinition;

/**
 * The form associated with a logged daily activity.
 */
public interface DailyForm<T extends Daily> {
    /**
     * {@return the definition of a form used to log activities}
     */
    @NonNull
    FormDefinition definition();


    /**
     * {@return a new form with fields given by a logged daily activity}
     * @param daily the daily
     */
    @NonNull
    Form dailyToForm(@NonNull T daily);

    /**
     * {@return a daily activity instance from a form submission}
     * @param form a form submission with definition given by
     *             <code>this.definition()</code>
     */
    @NonNull
    T formToDaily(@NonNull FormSubmission form);
}
