package com.b07.planetze.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.DailyForm;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.field.IntField;

public final class BuyClothesForm implements DailyForm<BuyClothesDaily> {
    @NonNull public static final BuyClothesForm INSTANCE = new BuyClothesForm();

    @NonNull private final FieldId<Integer> numberItems;
    @NonNull private final FormDefinition definition;

    private BuyClothesForm() {
        FormBuilder fb = new FormBuilder();
        numberItems = fb.add("Number of clothing items purchased",
                IntField.create());
        definition = fb.build();
    }

    @NonNull
    @Override
    public FormDefinition definition() {
        return definition;
    }

    @NonNull
    @Override
    public Form dailyToForm(@NonNull BuyClothesDaily daily) {
        Form form = definition.createForm();

        form.set(numberItems, daily.numberItems());

        return form;
    }

    @NonNull
    @Override
    public BuyClothesDaily formToDaily(@NonNull FormSubmission form) {
        return new BuyClothesDaily(form.get(numberItems));
    }
}
