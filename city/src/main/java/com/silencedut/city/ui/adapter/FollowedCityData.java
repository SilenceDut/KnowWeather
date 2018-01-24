package com.silencedut.city.ui.adapter;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.city.R;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;


/**
 * Created by SilenceDut on 16/10/21.
 */

public class FollowedCityData implements BaseAdapterData {


    String cityId;
    String cityName;
    String temp;
    String weatherStatus;
    int backgroundId;

    public FollowedCityData(WeatherData weatherData, int backgroundId) {
        update(weatherData);
        this.backgroundId = backgroundId;
    }

    public void update(WeatherData weatherData) {
        if (weatherData != null) {
            this.cityId = weatherData.getCityId();
            this.cityName = weatherData.getBasic().getCity();
            this.temp = weatherData.getBasic().getTemp();
            this.weatherStatus = weatherData.getBasic().getWeather();
        }
    }


    public String getCityId() {
        return cityId;
    }

    @Override
    public int getContentViewId() {
        return R.layout.city_item_followed_city;
    }
}
