package com.b07.planetze.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.energy.EnergyBillsDaily;
import com.b07.planetze.daily.food.MealDaily;
import com.b07.planetze.daily.shopping.BuyClothesDaily;
import com.b07.planetze.daily.shopping.BuyElectronicsDaily;
import com.b07.planetze.daily.shopping.BuyOtherDaily;
import com.b07.planetze.daily.transport.CyclingOrWalkingDaily;
import com.b07.planetze.daily.transport.DrivingDaily;
import com.b07.planetze.daily.transport.FlightDaily;
import com.b07.planetze.daily.transport.PublicTransitDaily;
import com.b07.planetze.database.ToJsonSerializable;

import java.util.HashMap;

/**
 * The details of a daily activity.
 */
public interface Daily extends ToJsonSerializable {
    /**
     * {@return the CO2e emitted from this activity}
     */
    @NonNull
    Emissions emissions();

    /**
     * {@return the type of this activity}
     */
    @NonNull
    DailyType type();

    @NonNull
    static Daily fromJson(Object o) {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = (HashMap<String, Object>) o;

        return switch (DailyType.fromJson(map.get("type"))) {
            case DRIVING -> DrivingDaily.fromJson(map);
            case PUBLIC_TRANSIT -> PublicTransitDaily.fromJson(map);
            case CYCLING_OR_WALKING -> CyclingOrWalkingDaily.fromJson(map);
            case FLIGHT -> FlightDaily.fromJson(map);
            case MEAL -> MealDaily.fromJson(map);
            case BUY_CLOTHES -> BuyClothesDaily.fromJson(map);
            case BUY_ELECTRONICS -> BuyElectronicsDaily.fromJson(map);
            case BUY_OTHER -> BuyOtherDaily.fromJson(map);
            case ENERGY_BILLS -> EnergyBillsDaily.fromJson(map);
        };
    }
}
