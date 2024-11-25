package com.b07.planetze.ecotracker.daily.energy;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.ecotracker.daily.Daily;
import com.b07.planetze.ecotracker.daily.DailyType;
import com.b07.planetze.util.measurement.Mass;

public record EnergyBillsDaily(
        @NonNull BillType billType,
        double billAmount
) implements Daily {
    private static final double KWH_PER_DOLLAR = 8.2;
    private static final double G_CO2E_PER_KWH = 35;

    private static final double GAS_M3_PER_DOLLAR = 8.1;
    private static final double G_CO2E_PER_GAS_M3 = 12;

    private static final double WATER_M3_PER_DOLLAR = 0.2;
    private static final double G_CO2E_PER_WATER_M3 = 61;


    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.energy(Mass.g(switch(billType) {
            case ELECTRICITY ->
                    G_CO2E_PER_KWH * KWH_PER_DOLLAR * billAmount;
            case GAS ->
                    G_CO2E_PER_GAS_M3 * GAS_M3_PER_DOLLAR * billAmount;
            case WATER ->
                    G_CO2E_PER_WATER_M3 * WATER_M3_PER_DOLLAR * billAmount;
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.ENERGY_BILLS;
    }

    public enum BillType {
        ELECTRICITY,
        GAS,
        WATER
    }
}
