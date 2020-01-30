package com.example.maktabproj.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class BadgePrefs {

    public static final String PREFS_BADGE_COUNT = "badge count";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE);
    }

    public static int getBadgeCount(Context context){
        SharedPreferences preferences = getSharedPreferences(context);

        return preferences.getInt(PREFS_BADGE_COUNT, 0);
    }

    public static void setBadgeCount(Context context, int value){
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putInt(PREFS_BADGE_COUNT, value).apply();
    }
}
