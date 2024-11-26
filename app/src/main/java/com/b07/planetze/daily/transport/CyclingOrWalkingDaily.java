package com.b07.planetze.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.util.HashMap;
import java.util.Map;

public record CyclingOrWalkingDaily(@NonNull ImmutableDistance distance)
implements Daily {
    private static final double WALKING_G_CO2E_PER_KM = 35;
    private static final double CYCLING_G_CO2E_PER_KM = 21;
    private static final double G_CO2E_PER_KM
            = (WALKING_G_CO2E_PER_KM + CYCLING_G_CO2E_PER_KM) / 2;
    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.transport(Mass.g(G_CO2E_PER_KM * distance.km()));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.CYCLING_OR_WALKING;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static CyclingOrWalkingDaily fromJson(Map<String, Object> map) {
        return new CyclingOrWalkingDaily(
                ImmutableDistance.fromJson(map.get("distance")));
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type().toJson());
        map.put("distance", distance.toJson());
        return map;
    }
}
