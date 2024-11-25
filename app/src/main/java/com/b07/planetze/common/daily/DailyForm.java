package com.b07.planetze.common.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FormDefinition;

/**
 * The form associated with a logged daily activity.
 */
public interface DailyForm<T extends Daily> {
    /**
     * {@return the definition of this form} <br>
     * This is used for creating the form used to log the activity associated
     * with this <code>DailyForm</code>.
     * @see FormDefinition
     */
    @NonNull
    FormDefinition definition();

    /**
     * {@return a new form where the fields are filled with values given by a
     * logged daily activity} <br>
     * This form is used for editing previously used activities.
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
