package com.silencedut.knowweather.provider;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.api.IFetchWeather;
import com.silencedut.knowweather.repository.WeatherRepository;
import com.silencedut.knowweather.scheduleJob.PollingUtils;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.weatherprovider.IWeatherProvider;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.corebase.StatusDataResource;

import java.util.List;

/**
 * Created by SilenceDut on 2018/1/19 .
 */

@HubInject(api = IWeatherProvider.class)
public class WeatherProviderImpl implements IWeatherProvider {

    @Override
    public LiveData<StatusDataResource<WeatherData>> getWeatherData() {

        return WeatherRepository.getInstance().getWeatherObserver();
    }

    @Override
    public List<WeatherData> fetchFollowedWeather() {
        return WeatherRepository.getInstance().getFollowedWeather();
    }

    @Override
    public void updateWeather(String cityId) {
        CoreManager.getImpl(IFetchWeather.class).queryWeather(cityId);
    }

    @Override
    public void deleteWeather(String cityId) {
        WeatherRepository.getInstance().deleteWeather(cityId);
    }

    @Override
    public void startService(Context context, boolean allowPoll) {
        PollingUtils.startService(context,allowPoll);
    }

}
