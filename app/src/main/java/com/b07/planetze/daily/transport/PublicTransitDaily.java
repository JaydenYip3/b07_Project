package com.b07.planetze.daily.transport;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.util.measurement.Distance;
import com.b07.planetze.util.measurement.ImmutableDistance;
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
    public String summary() {
        return duration.format() + " on " + transitType.displayName();
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.PUBLIC_TRANSIT;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static PublicTransitDaily fromJson(@NonNull Map<String, Object> map) {
        return new PublicTransitDaily(
                TransitType.valueOf((String) map.get("transitType")),
                ImmutableDuration.fromJson(map.get("duration"))
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("transitType", transitType.name());
        map.put("duration", duration.toJson());
        return map;
    }

    public static final Parcelable.Creator<PublicTransitDaily> CREATOR
            = new Parcelable.Creator<>() {
        public PublicTransitDaily createFromParcel(Parcel in) {
            return new PublicTransitDaily(
                    TransitType.valueOf(in.readString()),
                    ImmutableDuration.CREATOR.createFromParcel(in)
            );
        }

        public PublicTransitDaily[] newArray(int size) {
            return new PublicTransitDaily[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(transitType.name());
        dest.writeParcelable(duration, 0);
    }

    public enum TransitType {
        BUS("bus"),
        TRAIN("train"),
        SUBWAY("subway");

        @NonNull private final String displayName;

        TransitType(@NonNull String displayName) {
            this.displayName = displayName;
        }

        @NonNull
        public String displayName() {
            return displayName;
        }
    }
}
