package com.b07.planetze.daily.transport;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
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
    public static FlightDaily fromJson(Map<String, Object> map) {
        return new FlightDaily(
                (int) map.get("numberFlights"),
                switch((String) map.get("flightType")) {
                    case "SHORT_HAUL" -> FlightType.SHORT_HAUL;
                    case "LONG_HAUL" -> FlightType.LONG_HAUL;
                    default -> throw new DailyException();
                }
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type().toJson());
        map.put("numberFlights", numberFlights);
        map.put("flightType", switch(flightType) {
            case SHORT_HAUL -> "SHORT_HAUL";
            case LONG_HAUL -> "LONG_HAUL";
        });
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
