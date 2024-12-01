package com.b07.planetze.common;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User details.
 */
public record Habit(
        @NonNull ArrayList<String> keys
) implements ToJson {
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @NonNull
    public static Habit fromJson(@NonNull Object o) {
        Map<String, ArrayList<String>> map = (Map<String, ArrayList<String>>) o;
        ArrayList<String> keys = ((Map<String, ArrayList<String>>)o).get("keys");
        return new Habit(keys);
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.put("keys", keys);
        return map;
    }

    @Override
    @NonNull
    public ArrayList<String> keys() {
        return keys;
    }
}
