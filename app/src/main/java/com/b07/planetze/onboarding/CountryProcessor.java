package com.b07.planetze.onboarding;

import android.content.Context;

import org.json.JSONObject;

public class CountryProcessor {
    private JSONObject countries;

    public CountryProcessor(Context context, String fileName) {
        // Load JSON data
        countries = JsonReaderUtil.loadJSONFromAsset(context, fileName);
    }

    public double getResult(String key) {
        try {
            if (countries.has(key)) {
                return countries.getDouble(key);
            } else {
                return 0; // Default value for missing keys
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Handle error
        }
    }
}