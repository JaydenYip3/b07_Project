package com.b07.planetze.daily.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.util.HashMap;
import java.util.Map;

public record BuyClothesDaily(int numberItems)
implements Daily {
    private static final double KG_CO2E_PER_ITEM = 15;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.shopping(Mass.kg(KG_CO2E_PER_ITEM * numberItems));
    }

    @NonNull
    @Override
    public String summary() {
        return numberItems + " item" + (numberItems != 1 ? "s" : "");
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.BUY_CLOTHES;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static BuyClothesDaily fromJson(@NonNull Map<String, Object> map) {
        return new BuyClothesDaily(Util.toInt(map.get("numberItems")));
    }

    public static final Parcelable.Creator<BuyClothesDaily> CREATOR
            = new Parcelable.Creator<>() {
        public BuyClothesDaily createFromParcel(Parcel in) {
            return new BuyClothesDaily(
                    in.readInt()
            );
        }

        public BuyClothesDaily[] newArray(int size) {
            return new BuyClothesDaily[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(numberItems);
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("numberItems", numberItems);
        return map;
    }
}
