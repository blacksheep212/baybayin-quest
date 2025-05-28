package com.example.baybayinquest;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String PREF_NAME = "BaybayinPrefs";
    private static final String KEY_UNLOCKED_LEVEL = "UnlockedLevel";

    public static void setUnlockedLevel(Context context, int level) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_UNLOCKED_LEVEL, level);
        editor.apply();
    }

    public static int getUnlockedLevel(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_UNLOCKED_LEVEL, 1); // Default to level 1
    }
}