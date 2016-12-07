package com.silencedut.knowweather.weather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

/**
 * Created by SilenceDut on 16/10/20.
 */

public class DailyWeatherData implements BaseAdapterData {

    public WeatherEntity.DailyForecastEntity dailyForecastData;

    public DailyWeatherData(WeatherEntity.DailyForecastEntity dailyForecastData) {
        this.dailyForecastData = dailyForecastData;
    }


    @Override
    public int getItemViewType() {
        return R.layout.item_daily_forecast;
    }
}
