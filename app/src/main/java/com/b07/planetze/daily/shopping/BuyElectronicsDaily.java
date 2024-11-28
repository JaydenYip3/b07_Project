package com.b07.planetze.daily.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.util.HashMap;
import java.util.Map;

public record BuyElectronicsDaily(
        @NonNull DeviceType deviceType,
        int numberDevices
) implements Daily {
    private static final double KG_CO2E_PER_SMARTPHONE = 70;
    private static final double KG_CO2E_PER_LAPTOP = 331;
    private static final double KG_CO2E_PER_TV = 350;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.shopping(Mass.kg(switch(deviceType) {
            case SMARTPHONE -> KG_CO2E_PER_SMARTPHONE * numberDevices;
            case LAPTOP -> KG_CO2E_PER_LAPTOP * numberDevices;
            case TV -> KG_CO2E_PER_TV * numberDevices;
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.BUY_ELECTRONICS;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static BuyElectronicsDaily fromJson(@NonNull Map<String, Object> map) {
        return new BuyElectronicsDaily(
                DeviceType.valueOf((String) map.get("deviceType")),
                Util.toInt(map.get("numberDevices"))
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("deviceType", deviceType.name());
        map.put("numberDevices", numberDevices);
        return map;
    }

    public static final Parcelable.Creator<BuyElectronicsDaily> CREATOR
            = new Parcelable.Creator<>() {
        public BuyElectronicsDaily createFromParcel(Parcel in) {
            return new BuyElectronicsDaily(
                    DeviceType.valueOf(in.readString()),
                    in.readInt()
            );
        }

        public BuyElectronicsDaily[] newArray(int size) {
            return new BuyElectronicsDaily[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(deviceType.name());
        dest.writeInt(numberDevices);
    }

    public enum DeviceType {
        SMARTPHONE,
        LAPTOP,
        TV
    }
}
