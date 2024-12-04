package com.b07.planetze.database.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.database.ToJson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * The representation of logged daily activities in the database.
 */
public record DailyData(LocalDate date, Daily daily)
        implements ToJson, Parcelable {
    @NonNull
    public DailyFetch withId(@NonNull DailyId id) {
        return new DailyFetch(id, this);
    }

    @NonNull
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static DailyData fromJson(Object o) {
        Map<String, Object> map = (Map<String, Object>) o;
        Map<String, Object> fields = (Map<String, Object>) map.get("fields");
        DailyType type = DailyType.fromJson(map.get("type"));

        LocalDate date = LocalDate.parse((String) map.get("date"),
                DateTimeFormatter.ISO_LOCAL_DATE);

        return new DailyData(date, type.createDailyFromJson(fields));
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        map.put("type", daily.type().toJson());
        map.put("fields", daily.toJson());
        return map;
    }

    public static final Parcelable.Creator<DailyData> CREATOR
            = new Parcelable.Creator<>() {
        public DailyData createFromParcel(Parcel in) {
            LocalDate date = LocalDate.parse(in.readString(),
                    DateTimeFormatter.ISO_LOCAL_DATE);
            DailyType type = DailyType.CREATOR.createFromParcel(in);
            Daily daily = type.createDailyFromParcel(in);
            return new DailyData(date, daily);
        }

        public DailyData[] newArray(int size) {
            return new DailyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        dest.writeParcelable(daily.type(), 0);
        dest.writeParcelable(daily, 0);
    }
}
