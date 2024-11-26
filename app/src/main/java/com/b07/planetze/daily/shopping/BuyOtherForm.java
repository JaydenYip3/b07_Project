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

public final class BuyOtherForm implements DailyForm<BuyOtherDaily> {
    @NonNull public static final BuyOtherForm INSTANCE = new BuyOtherForm();

    @NonNull private final FieldId<Integer> type;
    @NonNull private final FieldId<Integer> number;
    @NonNull private final FormDefinition definition;

    private BuyOtherForm() {
        FormBuilder fb = new FormBuilder();
        type = fb.add("Type of device", ChoiceField
                .withChoices("Furniture", "Appliance"));
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
    public Form dailyToForm(@NonNull BuyOtherDaily daily) {
        Form form = definition.createForm();

        int purchaseType = switch(daily.purchaseType()) {
            case FURNITURE -> 0;
            case APPLIANCE -> 1;
        };
        form.set(type, purchaseType);

        form.set(number, daily.numberItems());

        return form;
    }

    @NonNull
    @Override
    public BuyOtherDaily formToDaily(@NonNull FormSubmission form) {
        BuyOtherDaily.PurchaseType purchaseType = switch(form.get(type)) {
            case 0 -> BuyOtherDaily.PurchaseType.FURNITURE;
            case 1 -> BuyOtherDaily.PurchaseType.APPLIANCE;
            default -> throw new DailyException();
        };

        return new BuyOtherDaily(purchaseType, form.get(number));
    }
}
