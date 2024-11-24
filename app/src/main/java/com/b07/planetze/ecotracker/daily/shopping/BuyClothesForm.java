package com.b07.planetze.ecotracker.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.ecotracker.daily.DailyForm;
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
                IntField.POSITIVE);
        definition = fb.build();
    }

    @NonNull
    @Override
    public FormDefinition definition() {
        return definition;
    }

    @NonNull
    @Override
    public BuyClothesDaily createDaily(@NonNull FormSubmission form) {
        return new BuyClothesDaily(form.get(numberItems));
    }
}
