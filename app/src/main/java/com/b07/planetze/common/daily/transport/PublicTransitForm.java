package com.b07.planetze.common.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;
import com.b07.planetze.util.measurement.ImmutableDuration;
import com.b07.planetze.common.daily.DailyForm;
import com.b07.planetze.ecotracker.exception.DailyFormException;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.DurationField;

public final class PublicTransitForm implements DailyForm<PublicTransitDaily> {
    @NonNull public static final PublicTransitForm INSTANCE
            = new PublicTransitForm();

    @NonNull private final FieldId<Integer> type;
    @NonNull private final FieldId<ImmutableDuration> duration;
    @NonNull private final FormDefinition definition;

    private PublicTransitForm() {
        FormBuilder fb = new FormBuilder();
        type = fb.add("Type of transportation", ChoiceField
                .withChoices("Bus", "Train", "Subway"));
        duration = fb.add("Time spent", DurationField.create());
        definition = fb.build();
    }

    @NonNull
    @Override
    public FormDefinition definition() {
        return definition;
    }

    @NonNull
    @Override
    public Form dailyToForm(@NonNull PublicTransitDaily daily) {
        Form form = definition.createForm();

        int transitType = switch(daily.transitType()) {
            case BUS -> 0;
            case TRAIN -> 1;
            case SUBWAY -> 2;
        };
        form.set(type, transitType);

        form.set(duration, daily.duration());

        return form;
    }

    @NonNull
    @Override
    public PublicTransitDaily formToDaily(@NonNull FormSubmission form) {
        PublicTransitDaily.TransitType transitType = switch(form.get(type)) {
            case 0 -> PublicTransitDaily.TransitType.BUS;
            case 1 -> PublicTransitDaily.TransitType.TRAIN;
            case 2 -> PublicTransitDaily.TransitType.SUBWAY;
            default -> throw new DailyFormException();
        };

        return new PublicTransitDaily(transitType, form.get(duration));
    }
}
