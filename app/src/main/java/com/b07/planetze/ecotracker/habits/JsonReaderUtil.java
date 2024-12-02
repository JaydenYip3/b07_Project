package com.b07.planetze.ecotracker.habits;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

import java.io.InputStream;

public class JsonReaderUtil extends Activity {
    public static JSONObject loadJSONFromAsset(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
