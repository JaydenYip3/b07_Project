package com.b07.planetze.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.Daily;
import com.b07.planetze.form.Form;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.daily.DailyForm;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.DistanceField;

public final class DrivingForm implements DailyForm<DrivingDaily> {
    @NonNull public static final DrivingForm INSTANCE = new DrivingForm();

    @NonNull private final FieldId<ImmutableDistance> distance;
    @NonNull private final FieldId<Integer> vehicle;
    @NonNull private final FormDefinition definition;

    private DrivingForm() {
        FormBuilder fb = new FormBuilder();
        distance = fb.add("Distance travelled", DistanceField.create());
        vehicle = fb.add("Vehicle type", ChoiceField
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
    public Form dailyToForm(@NonNull Daily d) {
        var daily = (DrivingDaily) d;
        Form form = definition.createForm();

        form.set(distance, daily.distance());

        int vehicleType = switch(daily.vehicleType()) {
            case GAS_CAR -> 0;
            case ELECTRIC_CAR -> 1;
            case MOTORBIKE -> 2;
        };
        form.set(vehicle, vehicleType);

        return form;
    }

    @NonNull
    @Override
    public DrivingDaily formToDaily(@NonNull FormSubmission form) {
        DrivingDaily.VehicleType vehicleType = switch(form.get(vehicle)) {
            case 0 -> DrivingDaily.VehicleType.GAS_CAR;
            case 1 -> DrivingDaily.VehicleType.ELECTRIC_CAR;
            case 2 -> DrivingDaily.VehicleType.MOTORBIKE;
            default -> throw new DailyException();
        };

        return new DrivingDaily(vehicleType, form.get(distance));
    }
}
