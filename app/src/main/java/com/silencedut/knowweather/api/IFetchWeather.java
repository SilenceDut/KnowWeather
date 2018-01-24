package com.silencedut.knowweather.api;

import com.silencedut.weather_core.api.ICoreApi;

import java.util.List;

/**
 * Created by SilenceDut on 2018/1/21 .
 */

public interface IFetchWeather extends ICoreApi {
    void queryWeather(String cityId);
    void queryWeather(List<String> citys);
}
