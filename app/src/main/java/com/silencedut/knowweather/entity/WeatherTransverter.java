package com.silencedut.knowweather.entity;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.baselib.commonhelper.utils.TimeUtil;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 2018/1/9 .
 */

public class WeatherTransverter {
    private static final String TAG = "WeatherTransverter";

    public static WeatherData convertFromHeWeather(HeWeather heWeather,AqiEntity heWeatherAqi) {
        WeatherData weatherData = new WeatherData();
        try {
            HeWeather.HeWeather6Bean heWeather6Bean = heWeather.getHeWeather6().get(0);
            HeWeather.HeWeather6Bean.BasicBean basicBean = heWeather6Bean.getBasic();
            HeWeather.HeWeather6Bean.NowBean nowBean = heWeather6Bean.getNow();

            List<HeWeather.HeWeather6Bean.HourlyBean> hourlyBeans = heWeather6Bean.getHourly();
            List<HeWeather.HeWeather6Bean.DailyForecastBean> dailyForecastBeans = heWeather6Bean.getDailyForecast();
            List<HeWeather.HeWeather6Bean.LifestyleBean> lifestyleBeans = heWeather6Bean.getLifestyle();

            weatherData.setCityId(basicBean.getCid());

            WeatherData.BasicEntity basicEntity = new WeatherData.BasicEntity();
            weatherData.setBasic(basicEntity);
            basicEntity.setCity(basicBean.getLocation());
            basicEntity.setTemp(nowBean.getTmp());
            basicEntity.setWeather(nowBean.getCond_txt());
            basicEntity.setTime(heWeather6Bean.getUpdate().getLoc());

            List<WeatherData.HoursForecastEntity> hoursForecastEntities  = new ArrayList<>();
            weatherData.setHoursForecast(hoursForecastEntities);
            for(HeWeather.HeWeather6Bean.HourlyBean hourlyBean : hourlyBeans) {

                WeatherData.HoursForecastEntity hoursForecastEntity = new WeatherData.HoursForecastEntity();
                hoursForecastEntity.setTemp(hourlyBean.getTmp());
                hoursForecastEntity.setTime(hourlyBean.getTime());
                hoursForecastEntity.setWeather(hourlyBean.getCond_txt());
                hoursForecastEntities.add(hoursForecastEntity);
            }

            List<WeatherData.DailyForecastEntity> dailyForecastEntities  = new ArrayList<>();
            weatherData.setDailyForecast(dailyForecastEntities);
            for(HeWeather.HeWeather6Bean.DailyForecastBean dailyForecastBean : dailyForecastBeans) {

                WeatherData.DailyForecastEntity dailyForecastEntity = new WeatherData.DailyForecastEntity();
                dailyForecastEntity.setDate(dailyForecastBean.getDate());
                dailyForecastEntity.setTemp_range(dailyForecastBean.getTmp_min()+"~"+dailyForecastBean.getTmp_max()+"°");
                dailyForecastEntity.setWeather(dailyForecastBean.getCond_txt_d());
                dailyForecastEntity.setWeek(TimeUtil.getWeek(dailyForecastBean.getDate()));
                dailyForecastEntities.add(dailyForecastEntity);
            }

            List<WeatherData.LifeIndexEntity> lifeIndexEntities  = new ArrayList<>();
            weatherData.setLifeIndex(lifeIndexEntities);
            for(HeWeather.HeWeather6Bean.LifestyleBean lifestyleBean : lifestyleBeans) {

                WeatherData.LifeIndexEntity lifeIndexEntity = new WeatherData.LifeIndexEntity();

                lifeIndexEntity.setName(lifestyleBean.getType());
                lifeIndexEntity.setLevel(lifestyleBean.getBrf());
                lifeIndexEntity.setContent(lifestyleBean.getTxt());

                lifeIndexEntities.add(lifeIndexEntity);
            }


            if(heWeatherAqi != null && heWeatherAqi.HeWeather6!=null  ) {
                AqiEntity.HeWeather6Bean.AirNowCityBean airNowCityBean = heWeatherAqi.HeWeather6.get(0).air_now_city;
                WeatherData.AqiEntity aqiEntity = new WeatherData.AqiEntity();
                aqiEntity.setAqi(airNowCityBean.aqi);
                aqiEntity.setPm25(airNowCityBean.pm25);
                aqiEntity.setPm10(airNowCityBean.pm10);
                aqiEntity.setQuality(airNowCityBean.qlty);
                weatherData.setAqi(aqiEntity);
            }






        }catch (Exception e) {
            LogHelper.error(TAG,"convertFromHeWeather error %s",e);
        }

        return weatherData;
    }




}
