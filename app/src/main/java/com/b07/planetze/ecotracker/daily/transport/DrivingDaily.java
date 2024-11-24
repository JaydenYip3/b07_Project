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
    private static final double CAR_G_CO2E_PER_KM = 192;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.transport(switch(vehicle) {
            case CAR -> Mass.g(CAR_G_CO2E_PER_KM * distance.km());
        });
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.DRIVING;
    }

    public enum Vehicle {
        CAR
    }
}
