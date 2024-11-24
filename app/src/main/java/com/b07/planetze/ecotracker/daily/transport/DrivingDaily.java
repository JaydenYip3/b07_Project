package com.b07.planetze.ecotracker.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.measurement.ImmutableDistance;
import com.b07.planetze.common.measurement.Mass;
import com.b07.planetze.ecotracker.daily.Daily;
import com.b07.planetze.ecotracker.daily.DailyType;

public record DrivingDaily(
        @NonNull Vehicle vehicle,
        @NonNull ImmutableDistance distance
) implements Daily {
    private static final double GAS_CAR_G_CO2E_PER_KM = 170;
    private static final double ELECTRIC_CAR_G_CO2E_PER_KM = 47;
    private static final double MOTORBIKE_G_CO2E_PER_KM = 113;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.transport(Mass.g(switch(vehicle) {
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

    public enum Vehicle {
        GAS_CAR,
        ELECTRIC_CAR,
        MOTORBIKE,
    }
}
