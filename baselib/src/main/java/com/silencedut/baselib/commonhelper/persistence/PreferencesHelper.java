package com.silencedut.baselib.commonhelper.persistence;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Set;

/**
 * Created by SilenceDut on 16/10/28.
 */

public  class PreferencesHelper {
    private static Context mContext;
    private static String mPreferencesName;

    public static void init(Context context , String appName) {
        mContext = context;
        mPreferencesName = appName;
    }

    /**
     * 设置对用户的preference
     * @param uid 用户uid
     */
    public static void setUid(long uid) {
        mPreferencesName += uid;
    }

    /**
     * 根据应用设置缓存地址
     * @return 缓存文件夹名称
     */
    private static String getPreferencesName(){
        return mPreferencesName;
    }


    private static SharedPreferences getPreferences(String name) {
        return mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    public static boolean get(String key, boolean defValue) {
        return get(getPreferencesName(), key, defValue);
    }

    public static int get(String key, int defValue) {
        return get(getPreferencesName(), key, defValue);
    }

    public static float get(String key, float defValue) {
        return get(getPreferencesName(), key, defValue);
    }

    public static long get(String key, long defValue) {
        return get(getPreferencesName(), key, defValue);
    }

    public static String get(String key, String defValue) {
        return get(getPreferencesName(), key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> get(String key, Set<String> defValue) {
        return get(getPreferencesName(), key, defValue);
    }

    public static boolean get(String name, String key, boolean defValue) {
        return getPreferences(name).getBoolean(key, defValue);
    }

    public  static int get(String name, String key, int defValue) {
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
        put(getPreferencesName(), key, value);
    }

    public static void put(String key, int value) {
        put(getPreferencesName(), key, value);
    }

    public static void put(String key, float value) {
        put(getPreferencesName(), key, value);
    }

    public static void put(String key, long value) {
        put(getPreferencesName(), key, value);
    }

    public static void put(String key, String value) {
        put(getPreferencesName(), key, value);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void put(String key, Set<String> value) {
        put(getPreferencesName(), key, value);
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
        remove(getPreferencesName(), key);
    }

    public static void remove(String name, String key) {
        getPreferences(name).edit().remove(key).apply();
    }


    public static void clear() {
        clear(getPreferencesName());
    }

    public static void clear(String name) {
        getPreferences(name).edit().clear().apply();
    }
}