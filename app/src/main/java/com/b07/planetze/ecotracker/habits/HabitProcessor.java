package com.b07.planetze.ecotracker.habits;

import android.content.Context;

import com.b07.planetze.onboarding.JsonReaderUtil;

import org.json.JSONObject;

public class HabitProcessor {
    private JSONObject habits;
    Object[] habitInfo= new Object[7];

    public HabitProcessor(Context context, String fileName) {
        // Load JSON data
        habits = JsonReaderUtil.loadJSONFromAsset(context, fileName);
    }

    public Object[] getResult(String key) {
        try {
            if (habits.has(key)) {
                JSONObject habit = (JSONObject) habits.get(key);
                habitInfo[0] = habit.get("habitName");
                habitInfo[1] = habit.get("dailyType");
                habitInfo[2] = habit.get("category");
                habitInfo[3] = habit.get("description");
                habitInfo[4] = habit.get("potential");
                habitInfo[5] = habit.get("tracking");
                habitInfo[6] = habit.get("keywords");
                return habitInfo;
            } else {
                return habitInfo; // Default value for missing keys
            }
        } catch (Exception e) {
            e.printStackTrace();
            return habitInfo; // Handle error
        }
    }
}