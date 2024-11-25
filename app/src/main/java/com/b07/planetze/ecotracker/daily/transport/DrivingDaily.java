package com.b07.planetze.ecotracker.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.ecotracker.daily.Daily;
import com.b07.planetze.ecotracker.daily.DailyType;

public record DrivingDaily(
        @NonNull VehicleType vehicleType,
        @NonNull ImmutableDistance distance
) implements Daily {
    private static final double GAS_CAR_G_CO2E_PER_KM = 170;
    private static final double ELECTRIC_CAR_G_CO2E_PER_KM = 47;
    private static final double MOTORBIKE_G_CO2E_PER_KM = 113;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.transport(Mass.g(switch(vehicleType) {
            case GAS_CAR -> GAS_CAR_G_CO2E_PER_KM * distance.km();
            case ELECTRIC_CAR -> ELECTRIC_CAR_G_CO2E_PER_KM * distance.km();
            case MOTORBIKE -> MOTORBIKE_G_CO2E_PER_KM * distance.km();
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.DRIVING;
    }

    public enum VehicleType {
        GAS_CAR,
        ELECTRIC_CAR,
        MOTORBIKE,
    }
}