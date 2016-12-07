package com.silencedut.knowweather.weather.presenter;

import com.silencedut.knowweather.common.BaseView;
import com.silencedut.knowweather.weather.entity.WeatherEntity;
import com.silencedut.knowweather.weather.ui.adapter.AqiData;
import com.silencedut.knowweather.weather.ui.adapter.DailyWeatherData;
import com.silencedut.knowweather.weather.ui.adapter.HoursForecastData;
import com.silencedut.knowweather.weather.ui.adapter.LifeIndexData;

import java.util.List;

/**
 * Created by SilenceDut on 2016/11/14 .
 */

public interface MainView extends BaseView {
    void onBasicInfo(WeatherEntity.BasicEntity basicData, List<HoursForecastData> hoursForecastDatas, boolean isLocationCity);

    void onMoreInfo(AqiData aqiData, List<DailyWeatherData> dailyForecastDatas, LifeIndexData lifeIndexData);

    void onRefreshing(boolean refreshing);

}
