package com.b07.planetze.ecotracker.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.measurement.ImmutableDuration;
import com.b07.planetze.common.measurement.Mass;
import com.b07.planetze.ecotracker.daily.Daily;
import com.b07.planetze.ecotracker.daily.DailyType;

public record PublicTransitDaily(
        @NonNull TransitType transitType,
        @NonNull ImmutableDuration duration
) implements Daily {
    // Per passenger:
    private static final double BUS_KG_CO2E_PER_MILE = 0.0714;
    private static final double TRAIN_KG_CO2E_PER_MILE = 0.114;
    private static final double SUBWAY_KG_CO2E_PER_MILE = 0.0996;

    private static final double BUS_MPH = 7.9;
    private static final double TRAIN_MPH = 23;
    private static final double SUBWAY_MPH = 17.4;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.transport(Mass.kg(switch(transitType) {
            case BUS -> BUS_KG_CO2E_PER_MILE * BUS_MPH * duration.h();
            case TRAIN -> TRAIN_KG_CO2E_PER_MILE * TRAIN_MPH * duration.h();
            case SUBWAY -> SUBWAY_KG_CO2E_PER_MILE * SUBWAY_MPH * duration.h();
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.PUBLIC_TRANSIT;
    }

    public enum TransitType {
        BUS,
        TRAIN,
        SUBWAY
    }
}
