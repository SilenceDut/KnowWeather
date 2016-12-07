package com.silencedut.knowweather.weather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

/**
 * Created by SilenceDut on 16/10/20.
 */

public class AqiData implements BaseAdapterData {

    public WeatherEntity.AqiEntity aqiData;

    public AqiData(WeatherEntity.AqiEntity aqiData) {
        this.aqiData = aqiData;
    }


    @Override
    public int getItemViewType() {
        return R.layout.item_aqi;
    }
}
