package com.silencedut.knowweather.citys.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

/**
 * Created by SilenceDut on 16/10/21.
 */

public class FollowedCityData implements BaseAdapterData {

    String cityId;
    String cityName;
    String temp;
    String weatherStatus;
    int backgroundId;

    public FollowedCityData(WeatherEntity weatherEntity, int backgroundId) {
        if (weatherEntity != null) {
            this.cityId = weatherEntity.getCityId();
            this.cityName = weatherEntity.getBasic().getCity();
            this.temp = weatherEntity.getBasic().getTemp();
            this.weatherStatus = weatherEntity.getBasic().getWeather();
        }
        this.backgroundId = backgroundId;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_followed_city;
    }
}
