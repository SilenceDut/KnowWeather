package com.silencedut.knowweather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;

/**
 * Created by SilenceDut on 16/10/20.
 */

public class DailyWeatherData implements BaseAdapterData {

    public WeatherData.DailyForecastEntity dailyForecastData;

    public DailyWeatherData(WeatherData.DailyForecastEntity dailyForecastData) {
        this.dailyForecastData = dailyForecastData;
    }


    @Override
    public int getContentViewId() {
        return R.layout.weather_item_daily_forecast;
    }
}
