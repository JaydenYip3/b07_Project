package com.b07.planetze.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.DailyForm;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.form.Form;
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
    public Form dailyToForm(@NonNull BuyElectronicsDaily daily) {
        Form form = definition.createForm();

        int deviceType = switch(daily.deviceType()) {
            case SMARTPHONE -> 0;
            case LAPTOP -> 1;
            case TV -> 2;
        };
        form.set(type, deviceType);

        form.set(number, daily.numberDevices());

        return form;
    }

    @NonNull
    @Override
    public BuyElectronicsDaily formToDaily(@NonNull FormSubmission form) {
        BuyElectronicsDaily.DeviceType deviceType = switch(form.get(type)) {
            case 0 -> BuyElectronicsDaily.DeviceType.SMARTPHONE;
            case 1 -> BuyElectronicsDaily.DeviceType.LAPTOP;
            case 2 -> BuyElectronicsDaily.DeviceType.TV;
            default -> throw new DailyException();
        };

        return new BuyElectronicsDaily(deviceType, form.get(number));
    }
}
