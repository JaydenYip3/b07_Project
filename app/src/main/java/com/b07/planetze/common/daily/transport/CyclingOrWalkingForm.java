package com.b07.planetze.common.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.common.daily.DailyForm;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.DistanceField;

public final class CyclingOrWalkingForm
        implements DailyForm<CyclingOrWalkingDaily> {
    @NonNull public static final CyclingOrWalkingForm INSTANCE
            = new CyclingOrWalkingForm();

    @NonNull private final FieldId<ImmutableDistance> distance;
    @NonNull private final FormDefinition definition;

    private CyclingOrWalkingForm() {
        FormBuilder fb = new FormBuilder();
        distance = fb.add("Distance cycled or walked", DistanceField
                .create());
        definition = fb.build();
    }

    @NonNull
    @Override
    public FormDefinition definition() {
        return definition;
    }

    @NonNull
    @Override
    public Form dailyToForm(@NonNull CyclingOrWalkingDaily daily) {
        Form form = definition.createForm();

        form.set(distance, daily.distance());

        return form;
    }

    @NonNull
    @Override
    public CyclingOrWalkingDaily formToDaily(@NonNull FormSubmission form) {
        return new CyclingOrWalkingDaily(form.get(distance));
    }
}
