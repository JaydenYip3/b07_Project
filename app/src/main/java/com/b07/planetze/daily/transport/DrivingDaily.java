package com.b07.planetze.daily.transport;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.util.HashMap;
import java.util.Map;

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
    public String summary() {
        return distance.format() + " via " + vehicleType.displayName();
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.DRIVING;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static DrivingDaily fromJson(@NonNull Map<String, Object> map) {
        return new DrivingDaily(
                VehicleType.valueOf((String) map.get("vehicleType")),
                ImmutableDistance.fromJson(map.get("distance"))
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("vehicleType", vehicleType.name());
        map.put("distance", distance.toJson());
        return map;
    }

    public static final Parcelable.Creator<DrivingDaily> CREATOR
            = new Parcelable.Creator<>() {
        public DrivingDaily createFromParcel(Parcel in) {
            return new DrivingDaily(
                    VehicleType.valueOf(in.readString()),
                    ImmutableDistance.CREATOR.createFromParcel(in)
            );
        }

        public DrivingDaily[] newArray(int size) {
            return new DrivingDaily[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(vehicleType.name());
        dest.writeParcelable(distance, 0);
    }

    public enum VehicleType {
        GAS_CAR("gas car"),
        ELECTRIC_CAR("electric car"),
        MOTORBIKE("motorbike");

        @NonNull private final String displayName;

        VehicleType(@NonNull String displayName) {
            this.displayName = displayName;
        }

        @NonNull
        public String displayName() {
            return displayName;
        }
    }
}
