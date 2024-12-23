package com.b07.planetze.daily.energy;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyException;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.daily.food.MealDaily;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.measurement.Mass;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public record EnergyBillsDaily(
        @NonNull BillType billType,
        double billAmount
) implements Daily {
    private static final double KWH_PER_DOLLAR = 8.2;
    private static final double G_CO2E_PER_KWH = 35;

    private static final double GAS_M3_PER_DOLLAR = 8.1;
    private static final double G_CO2E_PER_GAS_M3 = 12;

    private static final double WATER_M3_PER_DOLLAR = 0.2;
    private static final double G_CO2E_PER_WATER_M3 = 61;


    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.energy(Mass.g(switch(billType) {
            case ELECTRICITY ->
                    G_CO2E_PER_KWH * KWH_PER_DOLLAR * billAmount;
            case GAS ->
                    G_CO2E_PER_GAS_M3 * GAS_M3_PER_DOLLAR * billAmount;
            case WATER ->
                    G_CO2E_PER_WATER_M3 * WATER_M3_PER_DOLLAR * billAmount;
        }));
    }

    @NonNull
    @Override
    public String summary() {
        return String.format(
                Locale.US, "$%.2f in %s", billAmount, billType.displayName());
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.ENERGY_BILLS;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static EnergyBillsDaily fromJson(@NonNull Map<String, Object> map) {
        return new EnergyBillsDaily(
                BillType.valueOf((String) map.get("billType")),
                Util.toDouble(map.get("billAmount"))
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("billType", billType.name());
        map.put("billAmount", billAmount);
        return map;
    }

    public static final Parcelable.Creator<EnergyBillsDaily> CREATOR
            = new Parcelable.Creator<>() {
        public EnergyBillsDaily createFromParcel(Parcel in) {
            return new EnergyBillsDaily(
                    BillType.valueOf(in.readString()),
                    in.readDouble()
            );
        }

        public EnergyBillsDaily[] newArray(int size) {
            return new EnergyBillsDaily[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(billType.name());
        dest.writeDouble(billAmount);
    }

    public enum BillType {
        ELECTRICITY("electricity bills"),
        GAS("gas bills"),
        WATER("water bills");

        @NonNull private final String displayName;

        BillType(@NonNull String displayName) {
            this.displayName = displayName;
        }

        @NonNull
        public String displayName() {
            return displayName;
        }
    }
}
