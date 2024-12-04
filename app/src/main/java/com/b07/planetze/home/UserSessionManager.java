package com.b07.planetze.home;

import static com.b07.planetze.auth.AuthScreen.LOGIN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.planetze.MainActivity;
import com.b07.planetze.auth.AuthActivity;
import com.b07.planetze.auth.LoginFragment;

public class UserSessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    // Sign out the user
    public static void signOut(Context context) {
        // Clear user session data
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Show a toast message
        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to the login screen
        MainActivity.start(context);

        // Optionally finish the current activity (if called within an activity)
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).finishAffinity();
        }
    }

    // Check if user is logged in
    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Save the user's login state
    public static void setLoggedIn(Context context, boolean isLoggedIn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }
}