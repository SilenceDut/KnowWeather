package com.silencedut.weather_core.location;

/**
 * Created by SilenceDut on 2018/1/10 .
 */

public interface LocationNotification {
    void onLocation(boolean success,String cityId);
}
