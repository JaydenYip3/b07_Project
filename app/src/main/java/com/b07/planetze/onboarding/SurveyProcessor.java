package com.b07.planetze.onboarding;

import android.content.Context;
import org.json.JSONObject;

public class SurveyProcessor {
    private JSONObject combinations;

    public SurveyProcessor(Context context, String fileName) {
        // Load JSON data
        combinations = JsonReaderUtil.loadJSONFromAsset(context, fileName);
    }

    public int getResult(String key) {
        try {
            if (combinations.has(key)) {
                return combinations.getInt(key);
            } else {
                return 0; // Default value for missing keys
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Handle error
        }
    }
}