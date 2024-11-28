package com.b07.planetze.daily.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.daily.transport.CyclingOrWalkingDaily;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.util.HashMap;
import java.util.Map;

public record BuyOtherDaily(
        @NonNull PurchaseType purchaseType,
        int numberItems
) implements Daily {
    private static final double FURNITURE_KG_CO2E_PER_ITEM = 80;
    private static final double APPLIANCE_KG_CO2E_PER_ITEM = 400;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.shopping(Mass.kg(switch(purchaseType) {
            case FURNITURE -> FURNITURE_KG_CO2E_PER_ITEM * numberItems;
            case APPLIANCE -> APPLIANCE_KG_CO2E_PER_ITEM * numberItems;
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.BUY_OTHER;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static BuyOtherDaily fromJson(@NonNull Map<String, Object> map) {
        return new BuyOtherDaily(
                PurchaseType.valueOf((String) map.get("purchaseType")),
                Util.toInt(map.get("numberItems"))
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("purchaseType", purchaseType.name());
        map.put("numberItems", numberItems);
        return map;
    }

    public static final Parcelable.Creator<BuyOtherDaily> CREATOR
            = new Parcelable.Creator<>() {
        public BuyOtherDaily createFromParcel(Parcel in) {
            return new BuyOtherDaily(
                    PurchaseType.valueOf(in.readString()),
                    in.readInt()
            );
        }

        public BuyOtherDaily[] newArray(int size) {
            return new BuyOtherDaily[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(purchaseType.name());
        dest.writeInt(numberItems);
    }

    public enum PurchaseType {
        FURNITURE,
        APPLIANCE,
    }
}
