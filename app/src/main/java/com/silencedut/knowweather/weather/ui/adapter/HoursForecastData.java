package com.silencedut.knowweather.weather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class HoursForecastData implements BaseAdapterData {

    public WeatherEntity.HoursForecastEntity hoursForecastData;

    public HoursForecastData(WeatherEntity.HoursForecastEntity hoursForecastData) {
        this.hoursForecastData = hoursForecastData;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_hour_forecast;
    }
}
