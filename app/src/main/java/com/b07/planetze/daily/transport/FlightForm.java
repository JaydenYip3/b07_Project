package com.b07.planetze.daily.transport;


import androidx.annotation.NonNull;

import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyForm;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.IntField;

public final class FlightForm implements DailyForm<FlightDaily> {
    @NonNull public static final FlightForm INSTANCE = new FlightForm();

    @NonNull private final FieldId<Integer> type;
    @NonNull private final FieldId<Integer> number;
    @NonNull private final FormDefinition definition;

    private FlightForm() {
        FormBuilder fb = new FormBuilder();
        number = fb.add("Number of flights taken", IntField.create());
        type = fb.add("Type of flight", ChoiceField
                .withChoices("Short-haul", "Long-haul"));
        definition = fb.build();
    }

    @NonNull
    @Override
    public FormDefinition definition() {
        return definition;
    }

    @NonNull
    @Override
    public Form dailyToForm(@NonNull Daily d) {
        var daily = (FlightDaily) d;
        Form form = definition.createForm();

        int flightType = switch(daily.flightType()) {
            case SHORT_HAUL -> 0;
            case LONG_HAUL -> 1;
        };
        form.set(type, flightType);

        form.set(number, daily.numberFlights());

        return form;
    }

    @NonNull
    @Override
    public FlightDaily formToDaily(@NonNull FormSubmission form) {
        FlightDaily.FlightType flightType = switch(form.get(type)) {
            case 0 -> FlightDaily.FlightType.SHORT_HAUL;
            case 1 -> FlightDaily.FlightType.LONG_HAUL;
            default -> throw new DailyException();
        };

        return new FlightDaily(form.get(number), flightType);
    }
}