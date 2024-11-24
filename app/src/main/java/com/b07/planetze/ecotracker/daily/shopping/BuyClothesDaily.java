package com.b07.planetze.ecotracker.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.ecotracker.daily.Daily;
import com.b07.planetze.ecotracker.daily.DailyType;

public record BuyClothesDaily(int numberItems)
implements Daily {
    private static final double KG_CO2E_PER_ITEM = 15;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.shopping(Mass.kg(KG_CO2E_PER_ITEM * numberItems));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.BUY_CLOTHES;
    }
}
