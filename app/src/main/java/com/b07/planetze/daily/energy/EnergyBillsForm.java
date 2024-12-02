package com.b07.planetze.daily.energy;

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
import com.b07.planetze.form.field.MoneyField;

public final class EnergyBillsForm implements DailyForm<EnergyBillsDaily> {
    @NonNull public static final EnergyBillsForm INSTANCE
            = new EnergyBillsForm();

    @NonNull private final FieldId<Integer> type;
    @NonNull private final FieldId<Double> bills;
    @NonNull private final FormDefinition definition;

    private EnergyBillsForm() {
        FormBuilder fb = new FormBuilder();
        type = fb.add("Type of bill", ChoiceField
                .withChoices("Electricity", "Gas", "Water"));
        bills = fb.add("Bill amount", MoneyField.create());
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
        var daily = (EnergyBillsDaily) d;
        Form form = definition.createForm();

        int billType = switch(daily.billType()) {
            case ELECTRICITY -> 0;
            case GAS -> 1;
            case WATER -> 2;
        };
        form.set(type, billType);

        form.set(bills, daily.billAmount());

        return form;
    }

    @NonNull
    @Override
    public EnergyBillsDaily formToDaily(@NonNull FormSubmission form) {
        EnergyBillsDaily.BillType billType = switch(form.get(type)) {
            case 0 -> EnergyBillsDaily.BillType.ELECTRICITY;
            case 1 -> EnergyBillsDaily.BillType.GAS;
            case 2 -> EnergyBillsDaily.BillType.WATER;
            default -> throw new DailyException();
        };

        return new EnergyBillsDaily(billType, form.get(bills));
    }
}
