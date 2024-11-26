package com.b07.planetze.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.util.measurement.ImmutableDuration;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.util.HashMap;
import java.util.Map;

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

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static PublicTransitDaily fromJson(Map<String, Object> map) {
        return new PublicTransitDaily(
                switch((String) map.get("transitType")) {
                    case "BUS" -> TransitType.BUS;
                    case "TRAIN" -> TransitType.TRAIN;
                    case "SUBWAY" -> TransitType.SUBWAY;
                    default -> throw new DailyException();
                },
                ImmutableDuration.fromJson(map.get("duration"))
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type().toJson());
        map.put("transitType", switch(transitType) {
            case BUS -> "BUS";
            case TRAIN -> "TRAIN";
            case SUBWAY -> "SUBWAY";
        });
        map.put("duration", duration.toJson());
        return map;
    }

    public enum TransitType {
        BUS,
        TRAIN,
        SUBWAY
    }
}
