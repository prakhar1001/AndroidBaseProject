package com.lendingkart.instant.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    protected static final String TAG = PreferenceUtils.class.getName();

    private static final String PREFERENCE_NAME = "lendingkart";

    public static final String PREFERENCE_KEY_ACCESS_TOKEN = "accessToken";

    public static final String PREFERENCE_KEY_USER_NAME = "userName";

    public static final String ELIGIBILITY_FRAGMENT_KEY = "EligibilityFragments";

    public static String getString(Context context, String key) {
        String value = null;
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(key)) {
            value = sharedpreferences.getString(key, null);
        }
        return value;
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        prefEditor.putString(key, value);
        prefEditor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        boolean value = false;
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(key)) {
            value = sharedpreferences.getBoolean(key, false);
        }
        return value;
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        prefEditor.putBoolean(key, value);
        prefEditor.apply();
    }

    public static int getInt(Context context, String key) {
        int value = 0;
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(key)) {
            value = sharedpreferences.getInt(key, -1);
        }
        return value;
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        prefEditor.putInt(key, value);
        prefEditor.apply();
    }

    public static float getFloat(Context context, String key) {
        float value = 0.0f;
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(key)) {
            value = sharedpreferences.getFloat(key, 0.0f);
        }
        return value;
    }

    public static void saveFloat(Context context, String key, float value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        prefEditor.putFloat(key, value);
        prefEditor.apply();
    }

    public static long getLong(Context context, String key) {
        long value = 0L;
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(key)) {
            value = sharedpreferences.getLong(key, 0);
        }
        return value;
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        prefEditor.putLong(key, value);
        prefEditor.apply();
    }

    public static void saveCurrentlyLoadedFragment(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(key, value);
        prefEditor.apply();
    }

    public static int getLoadedEligibilityFragmentNumber(Context context, String key) {
        int value = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getInt(key, -1);
        }
        return value;
    }
}
