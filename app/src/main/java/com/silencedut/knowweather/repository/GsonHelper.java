/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.silencedut.knowweather.repository;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by SilenceDut on 17/04/19.
 * Json GsonHelper/Deserializer.
 */

public class GsonHelper {

  private static final Gson gson = new Gson();

  GsonHelper() {}

  /**
   * Serialize an object to Json.
   *
   * @param object to toJson.
   */
  public static String toJson(Object object) {
    return gson.toJson(object);
  }

  public static String toJson(Object object, Type type) {
    return gson.toJson(object, type);
  }

  /**
   * Deserialize a json representation of an object.
   *
   * @param string A json string to fromJson.
   */
  public static <T> T fromJson(String string, Class<T> clazz) {
    return gson.fromJson(string, clazz);
  }

  public static <T> T fromJson(String string, Type type) {
    return gson.fromJson(string, type);
  }
}
