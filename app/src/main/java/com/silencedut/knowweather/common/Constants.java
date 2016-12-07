package com.silencedut.knowweather.common;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.utils.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SilenceDut on 16/10/25.
 */


public class Constants {

    private Constants() {
    }

    //SharedPreferences KEY
    public static final String ALARM_ALLOW = "ALARM_ALLOW";
    public static final String NOTIFICATION_ALLOW = "NOTIFICATION_ALLOW";
    public static final String NOTIFICATION_THEME = "NOTIFICATION_THEME";
    public static final String POLLING_TIME = "POLLING_TIME";

    public static final String CITYS_TIPS_SHOW = "CITYS_TIPS_SHOW";
    public static final String LOCATION = "LOCATION";
    public static final String MAIN_PAGE_WEATHER = "MAIN_PAGE_WEATHER";
    public static final String DEFAULT_CITY = "DEFAULT_CITY";
    public static final String FOLLOWED_CITIES = "FOLLOWED_CITIES";
    public static final String DEFAULT_CITY_ID = "101220901";//亳州CityId

    public static final String DEFAULT_STR = "$";

    private static Map<String, Integer> sWeatherIcons = new HashMap<>();

    private static final int[] NOTIFICATION_THEMES = {R.layout.notification_system, R.layout.notification_white};
    private static final int[] NOTIFICATION_THEMES_NAMES = {R.string.follow_system, R.string.pure_white};
    private static final long[] SCHEDULES = {30 * 60, 60 * 60, 3 * 60 * 60, 0};
    private static final String[] SUNNY = {"晴", "多云"};
    private static final String[] WEATHERS = {"阴", "晴", "多云", "大雨", "雨", "雪", "风", "雾霾", "雨夹雪"};
    private static final int[] ICONS_ID = {R.mipmap.weather_clouds, R.mipmap.weather_sunny, R.mipmap.weather_few_clouds, R.mipmap.weather_big_rain, R.mipmap.weather_rain, R.mipmap.weather_snow, R.mipmap.weather_wind, R.mipmap.weather_haze, R.mipmap.weather_rain_snow};

    static {
        for (int index = 0; index < WEATHERS.length; index++) {
            sWeatherIcons.put(WEATHERS[index], ICONS_ID[index]);
        }
    }

    public static long getSchedule(int which) {
        return SCHEDULES[which];
    }

    public static int getNotificationThemeId(int which) {
        return NOTIFICATION_THEMES[which];
    }

    public static int getNotificationName(int which) {
        return NOTIFICATION_THEMES_NAMES[which];
    }

    public static boolean sunny(String weather) {

        for (String weatherKey : SUNNY) {
            if (weatherKey.contains(weather) || weather.contains(weatherKey)) {
                return true;
            }
        }

        return false;
    }

    public static int getIconId(String weather) {

        if (Check.isEmpty(weather)) {
            return R.mipmap.weather_none_available;
        }

        if (sWeatherIcons.get(weather) != null) {
            return sWeatherIcons.get(weather);
        }

        for (String weatherKey : sWeatherIcons.keySet()) {
            if (weatherKey.contains(weather) || weather.contains(weatherKey)) {
                return sWeatherIcons.get(weatherKey);
            }
        }

        return R.mipmap.weather_none_available;
    }


}
