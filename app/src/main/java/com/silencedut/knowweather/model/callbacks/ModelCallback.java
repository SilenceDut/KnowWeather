package com.silencedut.knowweather.model.callbacks;

import com.silencedut.knowweather.weather.entity.WeatherEntity;

/**
 * Created by SilenceDut on 2016/11/15 .
 */

public interface ModelCallback {
    interface LocationResult {
        void onLocationComplete(String cityId, boolean success);
    }

    interface WeatherResult {
        void onWeather(WeatherEntity weatherEntity);
    }

}
