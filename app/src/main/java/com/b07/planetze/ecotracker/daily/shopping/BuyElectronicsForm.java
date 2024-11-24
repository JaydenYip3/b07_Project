package com.b07.planetze.ecotracker.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.ecotracker.daily.DailyForm;
import com.b07.planetze.ecotracker.exception.DailyFormException;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.IntField;

public final class BuyElectronicsForm
        implements DailyForm<BuyElectronicsDaily> {
    @NonNull public static final BuyElectronicsForm INSTANCE
            = new BuyElectronicsForm();

    @NonNull private final FieldId<Integer> type;
    @NonNull private final FieldId<Integer> number;
    @NonNull private final FormDefinition definition;

    private BuyElectronicsForm() {
        FormBuilder fb = new FormBuilder();
        type = fb.add("Type of device", ChoiceField
                .withChoices("Smartphone", "Laptop", "TV"));
        number = fb.add("Number of devices purchased", IntField.POSITIVE);
        definition = fb.build();
    }

    @NonNull
    @Override
    public FormDefinition definition() {
        return definition;
    }

    @NonNull
    @Override
    public BuyElectronicsDaily createDaily(@NonNull FormSubmission form) {
        BuyElectronicsDaily.DeviceType deviceType = switch(form.get(type)) {
            case 0 -> BuyElectronicsDaily.DeviceType.SMARTPHONE;
            case 1 -> BuyElectronicsDaily.DeviceType.LAPTOP;
            case 2 -> BuyElectronicsDaily.DeviceType.TV;
            default -> throw new DailyFormException();
        };

        return new BuyElectronicsDaily(deviceType, form.get(number));
    }
}
