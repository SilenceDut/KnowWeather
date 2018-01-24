
package com.silencedut.baselib.commonhelper.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by SilenceDut on 17/04/19.
 * Json JsonHelper/Deserializer.
 */

public class JsonHelper {

    private static final Gson GSON = new Gson();

    JsonHelper() {}

    /**
     * Serialize an object to Json.
     *
     * @param object to toJson.
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static String toJson(Object object, Type type) {
        return GSON.toJson(object, type);
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param string A json string to fromJson.
     */
    public static <T> T fromJson(String string, Class<T> clazz) {
        return GSON.fromJson(string, clazz);
    }

    public static <T> T fromJson(String string, Type type) {
        return GSON.fromJson(string, type);
    }
}
