package com.silencedut.knowweather.api;

import com.silencedut.weather_core.api.BaseCoreApi;

import java.util.List;

/**
 * Created by SilenceDut on 2018/1/21 .
 */

public interface IFetchWeather extends BaseCoreApi {
    void queryWeather(String cityId);
    void queryWeather(List<String> citys);
}
