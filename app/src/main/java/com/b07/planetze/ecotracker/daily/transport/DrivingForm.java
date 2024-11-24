package com.b07.planetze.ecotracker.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.ecotracker.daily.DailyForm;
import com.b07.planetze.ecotracker.exception.DailyFormException;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.DistanceField;

public final class DrivingForm implements DailyForm {
    @NonNull public static final DrivingForm INSTANCE = new DrivingForm();

    @NonNull private final FieldId<ImmutableDistance> distance;
    @NonNull private final FieldId<Integer> vehicle;
    @NonNull private final FormDefinition definition;

    private DrivingForm() {
        FormBuilder fb = new FormBuilder();
        distance = fb.add("Distance travelled", DistanceField.create());
        vehicle = fb.add("Vehicle", ChoiceField
                .withChoices("Gas car", "Electric car", "Motorbike")
                .initially(0));
        definition = fb.build();
    }

    @NonNull
    @Override
    public FormDefinition definition() {
        return definition;
    }

    @NonNull
    @Override
    public DrivingDaily createDaily(@NonNull FormSubmission form) {
        DrivingDaily.Vehicle v = switch(form.get(vehicle)) {
            case 0 -> DrivingDaily.Vehicle.GAS_CAR;
            case 1 -> DrivingDaily.Vehicle.ELECTRIC_CAR;
            case 2 -> DrivingDaily.Vehicle.MOTORBIKE;
            default -> throw new DailyFormException();
        };

        return new DrivingDaily(v, form.get(distance));
    }
}
