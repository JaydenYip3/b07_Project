package com.b07.planetze.daily.transport;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.measurement.ImmutableDuration;
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

    @NonNull
    @Override
    public String summary() {
        return numberFlights + " " + flightType.displayName(numberFlights != 1);
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

    public static final Parcelable.Creator<FlightDaily> CREATOR
            = new Parcelable.Creator<>() {
        public FlightDaily createFromParcel(Parcel in) {
            return new FlightDaily(
                    in.readInt(),
                    FlightType.valueOf(in.readString())
            );
        }

        public FlightDaily[] newArray(int size) {
            return new FlightDaily[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(numberFlights);
        dest.writeString(flightType.name());
    }

    public enum FlightType {
        /**
         * < 1500km
         */
        SHORT_HAUL("short-haul flight"),

        /**
         * > 1500km
         */
        LONG_HAUL("long-haul flight");

        @NonNull private final String displayName;

        FlightType(@NonNull String displayName) {
            this.displayName = displayName;
        }

        @NonNull
        public String displayName(boolean isPlural) {
            return displayName + (isPlural ? "s" : "");
        }
    }
}
