package com.b07.planetze.ecotracker.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.ecotracker.daily.energy.EnergyBillsForm;
import com.b07.planetze.ecotracker.daily.food.MealForm;
import com.b07.planetze.ecotracker.daily.shopping.BuyClothesForm;
import com.b07.planetze.ecotracker.daily.shopping.BuyElectronicsForm;
import com.b07.planetze.ecotracker.daily.shopping.BuyOtherForm;
import com.b07.planetze.ecotracker.daily.transport.CyclingOrWalkingForm;
import com.b07.planetze.ecotracker.daily.transport.DrivingForm;
import com.b07.planetze.ecotracker.daily.transport.FlightForm;
import com.b07.planetze.ecotracker.daily.transport.PublicTransitForm;

/**
 * The type of a logged daily activity.
 */
public enum DailyType {
    DRIVING(DrivingForm.INSTANCE),
    PUBLIC_TRANSIT(PublicTransitForm.INSTANCE),
    CYCLING_OR_WALKING(CyclingOrWalkingForm.INSTANCE),
    FLIGHT(FlightForm.INSTANCE),
    MEAL(MealForm.INSTANCE),
    BUY_CLOTHES(BuyClothesForm.INSTANCE),
    BUY_ELECTRONICS(BuyElectronicsForm.INSTANCE),
    BUY_OTHER(BuyOtherForm.INSTANCE),
    ENERGY_BILLS(EnergyBillsForm.INSTANCE);

    @NonNull private final DailyForm form;

    DailyType(@NonNull DailyForm form) {
        this.form = form;
    }

    @NonNull
    public DailyForm form() {
        return form;
    }
}
