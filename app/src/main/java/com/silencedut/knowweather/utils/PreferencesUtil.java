package com.silencedut.knowweather.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;


import com.silencedut.knowweather.WeatherApplication;

import java.util.Set;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class PreferencesUtil {

    private static String defaultName = PreferencesUtil.class.getSimpleName();

    private static SharedPreferences getPreferences(String name) {
        return WeatherApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    public static String getDefaultName() {
        return defaultName;
    }

    public static void setDefaultName(String name) {
        defaultName = name;
    }


    public static boolean get(String key, boolean defValue) {
        return get(defaultName, key, defValue);
    }

    public static int get(String key, int defValue) {
        return get(defaultName, key, defValue);
    }

    public static float get(String key, float defValue) {
        return get(defaultName, key, defValue);
    }

    public static long get(String key, long defValue) {
        return get(defaultName, key, defValue);
    }

    public static String get(String key, String defValue) {
        return get(defaultName, key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> get(String key, Set<String> defValue) {
        return get(defaultName, key, defValue);
    }

    public static boolean get(String name, String key, boolean defValue) {
        return getPreferences(name).getBoolean(key, defValue);
    }

    public static int get(String name, String key, int defValue) {
        return getPreferences(name).getInt(key, defValue);
    }

    public static float get(String name, String key, float defValue) {
        return getPreferences(name).getFloat(key, defValue);
    }

    public static long get(String name, String key, long defValue) {
        return getPreferences(name).getLong(key, defValue);
    }

    public static String get(String name, String key, String defValue) {
        return getPreferences(name).getString(key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> get(String name, String key, Set<String> defValue) {
        return getPreferences(name).getStringSet(key, defValue);
    }

    public static void put(String key, boolean value) {
        put(defaultName, key, value);
    }

    public static void put(String key, int value) {
        put(defaultName, key, value);
    }

    public static void put(String key, float value) {
        put(defaultName, key, value);
    }

    public static void put(String key, long value) {
        put(defaultName, key, value);
    }

    public static void put(String key, String value) {
        put(defaultName, key, value);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void put(String key, Set<String> value) {
        put(defaultName, key, value);
    }

    public static void put(String name, String key, boolean value) {
        getPreferences(name).edit().putBoolean(key, value).apply();
    }

    public static void put(String name, String key, int value) {
        getPreferences(name).edit().putInt(key, value).apply();

    }

    public static void put(String name, String key, float value) {
        getPreferences(name).edit().putFloat(key, value).apply();
    }

    public static void put(String name, String key, long value) {
        getPreferences(name).edit().putLong(key, value).apply();
    }

    private static void put(final String name, final String key, final String value) {
        getPreferences(name).edit().putString(key, value).apply();


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void put(final String name, final String key, final Set<String> value) {
        getPreferences(name).edit().putStringSet(key, value).apply();

    }


    public static void remove(String key) {
        remove(defaultName, key);
    }

    public static void remove(String name, String key) {
        getPreferences(name).edit().remove(key).apply();
    }


    public static void clear() {
        clear(defaultName);
    }

    public static void clear(String name) {
        getPreferences(name).edit().clear().apply();
    }
}