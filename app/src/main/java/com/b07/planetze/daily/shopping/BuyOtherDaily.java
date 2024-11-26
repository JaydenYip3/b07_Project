package com.b07.planetze.daily.shopping;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyException;
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
    public static BuyOtherDaily fromJson(Map<String, Object> map) {
        return new BuyOtherDaily(
                switch((String) map.get("purchaseType")) {
                    case "FURNITURE" -> PurchaseType.FURNITURE;
                    case "APPLIANCE" -> PurchaseType.APPLIANCE;
                    default -> throw new DailyException();
                },
                (int) map.get("numberItems")
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type().toJson());
        map.put("purchaseType", switch(purchaseType) {
            case FURNITURE -> "FURNITURE";
            case APPLIANCE -> "APPLIANCE";
        });
        map.put("numberItems", numberItems);
        return map;
    }

    public enum PurchaseType {
        FURNITURE,
        APPLIANCE,
    }
}
