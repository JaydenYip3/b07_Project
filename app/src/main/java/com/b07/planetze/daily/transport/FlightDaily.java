package com.b07.planetze.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.util.HashMap;
import java.util.Map;

public record FlightDaily(
        int numberFlights,
        @NonNull FlightType flightType
) implements Daily {
    private static final double SHORT_G_CO2E_PER_KM = 251;
    private static final double LONG_G_CO2E_PER_KM = 195;

    private static final double SHORT_DISTANCE_KM = 750;
    private static final double LONG_DISTANCE_KM = 2000;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.transport(Mass.g(switch(flightType) {
            case SHORT_HAUL ->
                    SHORT_G_CO2E_PER_KM * SHORT_DISTANCE_KM * numberFlights;
            case LONG_HAUL ->
                    LONG_G_CO2E_PER_KM * LONG_DISTANCE_KM * numberFlights;
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.FLIGHT;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static FlightDaily fromJson(@NonNull Map<String, Object> map) {
        return new FlightDaily(
                Util.toInt(map.get("numberFlights")),
                FlightType.valueOf((String) map.get("flightType"))
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("numberFlights", numberFlights);
        map.put("flightType", flightType.name());
        return map;
    }

    public enum FlightType {
        /**
         * < 1500km
         */
        SHORT_HAUL,

        /**
         * > 1500km
         */
        LONG_HAUL
    }
}
