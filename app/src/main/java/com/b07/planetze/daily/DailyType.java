package com.b07.planetze.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.energy.EnergyBillsForm;
import com.b07.planetze.daily.food.MealForm;
import com.b07.planetze.daily.shopping.BuyClothesForm;
import com.b07.planetze.daily.shopping.BuyElectronicsForm;
import com.b07.planetze.daily.shopping.BuyOtherForm;
import com.b07.planetze.daily.transport.CyclingOrWalkingForm;
import com.b07.planetze.daily.transport.DrivingForm;
import com.b07.planetze.daily.transport.FlightForm;
import com.b07.planetze.daily.transport.PublicTransitForm;
import com.b07.planetze.database.ToJsonSerializable;

/**
 * The type of a logged daily activity.
 */
public enum DailyType implements ToJsonSerializable {
    DRIVING(DrivingForm.INSTANCE, "DRIVING"),
    PUBLIC_TRANSIT(PublicTransitForm.INSTANCE, "PUBLIC_TRANSIT"),
    CYCLING_OR_WALKING(CyclingOrWalkingForm.INSTANCE, "CYCLING_OR_WALKING"),
    FLIGHT(FlightForm.INSTANCE, "FLIGHT"),
    MEAL(MealForm.INSTANCE, "MEAL"),
    BUY_CLOTHES(BuyClothesForm.INSTANCE, "BUY_CLOTHES"),
    BUY_ELECTRONICS(BuyElectronicsForm.INSTANCE, "BUY_ELECTRONICS"),
    BUY_OTHER(BuyOtherForm.INSTANCE, "BUY_OTHER"),
    ENERGY_BILLS(EnergyBillsForm.INSTANCE, "ENERGY_BILLS");

    @NonNull private final DailyForm<?> form;
    @NonNull private final String string;

    DailyType(@NonNull DailyForm<?> form, @NonNull String string) {
        this.form = form;
        this.string = string;
    }

    @NonNull
    public DailyForm<?> form() {
        return form;
    }

    @NonNull
    public static DailyType fromJson(Object o) {
        return switch((String) o) {
            case "DRIVING" -> DRIVING;
            case "PUBLIC_TRANSIT" -> PUBLIC_TRANSIT;
            case "CYCLING_OR_WALKING" -> CYCLING_OR_WALKING;
            case "FLIGHT" -> FLIGHT;
            case "MEAL" -> MEAL;
            case "BUY_CLOTHES" -> BUY_CLOTHES;
            case "BUY_ELECTRONICS" -> BUY_ELECTRONICS;
            case "BUY_OTHER" -> BUY_OTHER;
            case "ENERGY_BILLS" -> ENERGY_BILLS;
            default -> throw new DailyException();
        };
    }

    @NonNull
    @Override
    public Object toJson() {
        return string;
    }
}
