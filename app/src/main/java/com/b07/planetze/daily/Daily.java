package com.b07.planetze.daily;

import android.os.Parcelable;

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
import com.b07.planetze.database.ToJson;

import java.util.HashMap;

/**
 * The details of a daily activity.
 */
public interface Daily extends ToJson, Parcelable {
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
}
