package com.b07.planetze.daily;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.energy.EnergyBillsDaily;
import com.b07.planetze.daily.energy.EnergyBillsForm;
import com.b07.planetze.daily.food.MealDaily;
import com.b07.planetze.daily.food.MealForm;
import com.b07.planetze.daily.shopping.BuyClothesDaily;
import com.b07.planetze.daily.shopping.BuyClothesForm;
import com.b07.planetze.daily.shopping.BuyElectronicsDaily;
import com.b07.planetze.daily.shopping.BuyElectronicsForm;
import com.b07.planetze.daily.shopping.BuyOtherDaily;
import com.b07.planetze.daily.shopping.BuyOtherForm;
import com.b07.planetze.daily.transport.CyclingOrWalkingDaily;
import com.b07.planetze.daily.transport.CyclingOrWalkingForm;
import com.b07.planetze.daily.transport.DrivingDaily;
import com.b07.planetze.daily.transport.DrivingForm;
import com.b07.planetze.daily.transport.FlightDaily;
import com.b07.planetze.daily.transport.FlightForm;
import com.b07.planetze.daily.transport.PublicTransitDaily;
import com.b07.planetze.daily.transport.PublicTransitForm;
import com.b07.planetze.database.ToJson;

import java.util.Map;
import java.util.function.Function;

/**
 * The type of a logged daily activity.
 */
public enum DailyType implements ToJson, Parcelable {
    DRIVING("Driving",
            DailyCategory.TRANSPORT,
            DrivingForm.INSTANCE,
            DrivingDaily::fromJson),
    PUBLIC_TRANSIT("Public transit",
            DailyCategory.TRANSPORT,
            PublicTransitForm.INSTANCE,
            PublicTransitDaily::fromJson),
    CYCLING_OR_WALKING("Cycling/walking",
            DailyCategory.TRANSPORT,
            CyclingOrWalkingForm.INSTANCE,
            CyclingOrWalkingDaily::fromJson),
    FLIGHT("Flight",
            DailyCategory.TRANSPORT,
            FlightForm.INSTANCE,
            FlightDaily::fromJson),
    MEAL("Food consumption",
            DailyCategory.FOOD,
            MealForm.INSTANCE,
            MealDaily::fromJson),
    BUY_CLOTHES("Clothes shopping",
            DailyCategory.SHOPPING,
            BuyClothesForm.INSTANCE,
            BuyClothesDaily::fromJson),
    BUY_ELECTRONICS("Electronics shopping",
            DailyCategory.SHOPPING,
            BuyElectronicsForm.INSTANCE,
            BuyElectronicsDaily::fromJson),
    BUY_OTHER("Other shopping",
            DailyCategory.SHOPPING,
            BuyOtherForm.INSTANCE,
            BuyOtherDaily::fromJson),
    ENERGY_BILLS("Energy bills",
            DailyCategory.ENERGY,
            EnergyBillsForm.INSTANCE,
            EnergyBillsDaily::fromJson);

    @NonNull private final String displayName;
    @NonNull private final DailyCategory category;
    @NonNull private final DailyForm<?> form;
    @NonNull private final Function<Map<String, Object>, Daily> createDailyFromJson;

    DailyType(
            @NonNull String displayName,
            @NonNull DailyCategory category,
            @NonNull DailyForm<?> form,
            @NonNull Function<Map<String, Object>, Daily> createDailyFromJson
    ) {
        this.displayName = displayName;
        this.category = category;
        this.form = form;
        this.createDailyFromJson = createDailyFromJson;
    }
    @NonNull
    public String displayName() {
        return displayName;
    }

    @NonNull
    public DailyCategory category() {
        return category;
    }

    @NonNull
    public DailyForm<?> form() {
        return form;
    }

    @NonNull
    public static DailyType fromJson(Object o) {
        return DailyType.valueOf((String) o);
    }

    @NonNull
    @Override
    public Object toJson() {
        return this.name();
    }

    @NonNull
    public Daily createDailyFromJson(Map<String, Object> map) {
        return createDailyFromJson.apply(map);
    }

    public static final Parcelable.Creator<DailyType> CREATOR
            = new Parcelable.Creator<>() {
        public DailyType createFromParcel(Parcel in) {
            return DailyType.valueOf(in.readString());
        }

        public DailyType[] newArray(int size) {
            return new DailyType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.name());
    }
}
