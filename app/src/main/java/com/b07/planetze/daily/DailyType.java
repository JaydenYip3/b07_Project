package com.b07.planetze.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.energy.EnergyBillsDaily;
import com.b07.planetze.daily.energy.EnergyBillsForm;
import com.b07.planetze.daily.food.MealDaily;
import com.b07.planetze.daily.food.MealForm;
import com.b07.planetze.daily.shopping.BuyClothesDaily;
import com.b07.planetze.daily.shopping.BuyClothesForm;
import com.b07.planetze.daily.shopping.BuyElectronicsDaily;
import com.b07.planetze.daily.shopping.BuyElectronicsForm;
import com.b07.planetze.daily.shopping.BuyOtherDaily;
import com.b07.planetze.daily.shopping.BuyOtherForm;
import com.b07.planetze.daily.transport.CyclingOrWalkingDaily;
import com.b07.planetze.daily.transport.CyclingOrWalkingForm;
import com.b07.planetze.daily.transport.DrivingDaily;
import com.b07.planetze.daily.transport.DrivingForm;
import com.b07.planetze.daily.transport.FlightDaily;
import com.b07.planetze.daily.transport.FlightForm;
import com.b07.planetze.daily.transport.PublicTransitDaily;
import com.b07.planetze.daily.transport.PublicTransitForm;
import com.b07.planetze.database.ToJson;

import java.util.Map;
import java.util.function.Function;

/**
 * The type of a logged daily activity.
 */
public enum DailyType implements ToJson {
    DRIVING(DrivingForm.INSTANCE,"DRIVING", DrivingDaily::fromJson),
    PUBLIC_TRANSIT(PublicTransitForm.INSTANCE, "PUBLIC_TRANSIT", PublicTransitDaily::fromJson),
    CYCLING_OR_WALKING(CyclingOrWalkingForm.INSTANCE, "CYCLING_OR_WALKING", CyclingOrWalkingDaily::fromJson),
    FLIGHT(FlightForm.INSTANCE, "FLIGHT", FlightDaily::fromJson),
    MEAL(MealForm.INSTANCE, "MEAL", MealDaily::fromJson),
    BUY_CLOTHES(BuyClothesForm.INSTANCE, "BUY_CLOTHES", BuyClothesDaily::fromJson),
    BUY_ELECTRONICS(BuyElectronicsForm.INSTANCE, "BUY_ELECTRONICS", BuyElectronicsDaily::fromJson),
    BUY_OTHER(BuyOtherForm.INSTANCE, "BUY_OTHER", BuyOtherDaily::fromJson),
    ENERGY_BILLS(EnergyBillsForm.INSTANCE, "ENERGY_BILLS", EnergyBillsDaily::fromJson);

    @NonNull private final DailyForm<?> form;
    @NonNull private final String string;
    @NonNull private final Function<Map<String, Object>, Daily> createDailyFromJson;

    DailyType(
            @NonNull DailyForm<?> form,
            @NonNull String string,
            @NonNull Function<Map<String, Object>, Daily> createDailyFromJson
    ) {
        this.form = form;
        this.string = string;
        this.createDailyFromJson = createDailyFromJson;
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

    @NonNull
    public Daily createDailyFromJson(Map<String, Object> map) {
        return createDailyFromJson.apply(map);
    }
}
