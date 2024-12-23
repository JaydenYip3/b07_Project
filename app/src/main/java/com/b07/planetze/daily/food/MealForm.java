package com.b07.planetze.daily.food;


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

public final class MealForm implements DailyForm<MealDaily> {
    @NonNull public static final MealForm INSTANCE = new MealForm();

    @NonNull private final FieldId<Integer> type;
    @NonNull private final FieldId<Integer> number;
    @NonNull private final FormDefinition definition;

    private MealForm() {
        FormBuilder fb = new FormBuilder();
        type = fb.add("Type of meal", ChoiceField
                .withChoices("Beef", "Pork", "Chicken", "Fish", "Plant-based"));
        number = fb.add("Number of servings consumed", IntField.create());
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
        var daily = (MealDaily) d;
        Form form = definition.createForm();

        int mealType = switch(daily.mealType()) {
            case BEEF -> 0;
            case PORK -> 1;
            case CHICKEN -> 2;
            case FISH -> 3;
            case PLANT_BASED -> 4;
        };
        form.set(type, mealType);

        form.set(number, daily.numberServings());

        return form;
    }

    @NonNull
    @Override
    public MealDaily formToDaily(@NonNull FormSubmission form) {
        MealDaily.MealType mealType = switch(form.get(type)) {
            case 0 -> MealDaily.MealType.BEEF;
            case 1 -> MealDaily.MealType.PORK;
            case 2 -> MealDaily.MealType.CHICKEN;
            case 3 -> MealDaily.MealType.FISH;
            case 4 -> MealDaily.MealType.PLANT_BASED;
            default -> throw new DailyException();
        };

        return new MealDaily(mealType, form.get(number));
    }
}
