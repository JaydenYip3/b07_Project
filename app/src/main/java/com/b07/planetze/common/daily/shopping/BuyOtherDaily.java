package com.b07.planetze.common.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.common.daily.Daily;
import com.b07.planetze.common.daily.DailyType;

public record BuyOtherDaily(
        @NonNull PurchaseType purchaseType,
        int numberItems
) implements Daily {
    private static final double FURNITURE_KG_CO2E_PER_ITEM = 80;
    private static final double APPLIANCE_KG_CO2E_PER_ITEM = 400;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.shopping(Mass.kg(switch(purchaseType) {
            case FURNITURE -> FURNITURE_KG_CO2E_PER_ITEM * numberItems;
            case APPLIANCE -> APPLIANCE_KG_CO2E_PER_ITEM * numberItems;
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.BUY_OTHER;
    }

    public enum PurchaseType {
        FURNITURE,
        APPLIANCE,
    }
}
