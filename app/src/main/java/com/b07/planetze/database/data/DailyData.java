package com.b07.planetze.database.data;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.database.ToJson;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Describes the representation of logged daily activities in the database.
 */
public record DailyData(LocalDate date, Daily daily)
        implements ToJson {
    @NonNull
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static DailyData fromJson(Object o) {
        Map<String, Object> map = (Map<String, Object>) o;
        Map<String, Object> fields = (Map<String, Object>) map.get("fields");
        DailyType type = DailyType.fromJson(map.get("type"));

        return new DailyData(
                LocalDate.parse((String) map.get("date")),
                type.createDailyFromJson(fields)
        );
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date.toString());
        map.put("type", daily.type().toJson());
        map.put("fields", daily.toJson());
        return map;
    }
}