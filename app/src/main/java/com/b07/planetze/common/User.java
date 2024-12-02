package com.b07.planetze.common;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJson;

import java.util.HashMap;
import java.util.Map;

/**
 * User details.
 */
public record User(
        @NonNull String name,
        @NonNull String email,
        @NonNull String country
) implements ToJson {
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @NonNull
    public static User fromJson(@NonNull Object o) {
        Map<String, String> map = (Map<String, String>) o;

        return new User(map.get("name"), map.get("email"), map.get("country"));
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("country", country);
        return map;
    }
}
