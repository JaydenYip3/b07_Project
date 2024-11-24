package com.b07.planetze.ecotracker.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.ecotracker.daily.Daily;
import com.b07.planetze.ecotracker.daily.DailyType;

public record BuyElectronicsDaily(
        @NonNull DeviceType deviceType,
        int numberDevices
) implements Daily {
    private static final double KG_CO2E_PER_SMARTPHONE = 70;
    private static final double KG_CO2E_PER_LAPTOP = 331;
    private static final double KG_CO2E_PER_TV = 350;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.shopping(Mass.kg(switch(deviceType) {
            case SMARTPHONE -> KG_CO2E_PER_SMARTPHONE * numberDevices;
            case LAPTOP -> KG_CO2E_PER_LAPTOP * numberDevices;
            case TV -> KG_CO2E_PER_TV * numberDevices;
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.BUY_ELECTRONICS;
    }

    public enum DeviceType {
        SMARTPHONE,
        LAPTOP,
        TV
    }
}
