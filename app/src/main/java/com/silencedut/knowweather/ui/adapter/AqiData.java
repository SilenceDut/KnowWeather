package com.silencedut.knowweather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;

/**
 * Created by SilenceDut on 16/10/20.
 */

public class AqiData implements BaseAdapterData {

    public WeatherData.AqiEntity aqiData;

    public AqiData(WeatherData.AqiEntity aqiData) {
        this.aqiData = aqiData;
    }


    @Override
    public int getContentViewId() {
        return R.layout.weather_item_aqi;
    }
}
