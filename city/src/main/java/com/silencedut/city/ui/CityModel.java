package com.silencedut.city.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.silencedut.city.ui.adapter.FollowedCityData;
import com.silencedut.city.ui.adapter.FollowedCityHolder;
import com.silencedut.taskscheduler.Task;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;
import com.silencedut.weather_core.api.weatherprovider.IWeatherProvider;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.corebase.StatusDataResource;
import com.silencedut.weather_core.location.ILocationApi;
import com.silencedut.weather_core.location.LocationNotification;
import com.silencedut.weather_core.viewmodel.BaseViewModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by SilenceDut on 2018/1/17 .
 */

public class CityModel extends BaseViewModel implements LocationNotification{
    private MutableLiveData<List<FollowedCityData>> mFollowedWeather = new MutableLiveData<>();


    @Override
    protected void onCreate() {
        mFollowedWeather.setValue(new CopyOnWriteArrayList<FollowedCityData>());
        CoreManager.getImpl(IWeatherProvider.class).getWeatherData().observe(this, new Observer<StatusDataResource<WeatherData>>() {
            @Override
            public void onChanged(@Nullable StatusDataResource<WeatherData> statusDataResource) {
                if(statusDataResource.isSucceed()) {
                    onWeather(statusDataResource.data);
                }
            }

        });
    }

    LiveData<List<FollowedCityData>> getFollowedWeather() {
        return mFollowedWeather;
    }


    private void fetchFollowedWeather() {
        TaskScheduler.execute(new Task<List<WeatherData>>() {
            @Override
            public List<WeatherData> doInBackground() throws InterruptedException {
                return  CoreManager.getImpl(IWeatherProvider.class).fetchFollowedWeather();
            }

            @Override
            public void onSuccess(List<WeatherData> weatherData) {
                parseFollowedWeathers(weatherData);
            }
        });

    }

    public void deleteFollowedWeather(String cityId) {
        CoreManager.getImpl(IWeatherProvider.class).deleteWeather(cityId);

        if(CoreManager.getImpl(ICityProvider.class).getCurrentCityId().equals(cityId)) {
            String locationId = CoreManager.getImpl(ILocationApi.class).getLocatedCityId();
            CoreManager.getImpl(ICityProvider.class).saveCurrentCityId(locationId);
            CoreManager.getImpl(IWeatherProvider.class).updateWeather(locationId);
        }

        List<FollowedCityData> followedCityDatas = mFollowedWeather.getValue();
        for (FollowedCityData followedCityData : followedCityDatas) {
            if (followedCityData.getCityId().equals(cityId)) {
                followedCityDatas.remove(followedCityData);
                break;
            }
        }

        mFollowedWeather.setValue(followedCityDatas);
    }


    @MainThread
    private  void parseFollowedWeathers(List<WeatherData> weatherDatas) {
        List<FollowedCityData> followedCityDatas = mFollowedWeather.getValue();
        followedCityDatas.clear();

        for(int index =0;index<weatherDatas.size();index++) {
            WeatherData weatherData = weatherDatas.get(index);
            if (weatherData != null) {
                if (weatherData.getCityId().equals(CoreManager.getImpl(ILocationApi.class).getLocatedCityId())) {
                    followedCityDatas.add(0, new FollowedCityData(weatherData, FollowedCityHolder.BLUR_IMAGE[index % FollowedCityHolder.BLUR_IMAGE.length]));
                } else {
                    followedCityDatas.add(new FollowedCityData(weatherData, FollowedCityHolder.BLUR_IMAGE[index % FollowedCityHolder.BLUR_IMAGE.length]));
                }
            }
        }
        mFollowedWeather.postValue(followedCityDatas);
    }

    private void onWeather(WeatherData weatherData) {
        boolean exist = false;
        List<FollowedCityData> followedCityDatas = mFollowedWeather.getValue();
        for (FollowedCityData followedCityData : followedCityDatas) {
            if (followedCityData.getCityId().equals(weatherData.getCityId())) {
                followedCityData.update(weatherData);
                exist = true;
                break;
            }
        }

        if(!exist) {
            if (CoreManager.getImpl(ILocationApi.class).getLocatedCityId().equals(weatherData.getCityId())) {
                followedCityDatas.add(0, new FollowedCityData(weatherData, FollowedCityHolder.BLUR_IMAGE[(followedCityDatas.size()+1) % FollowedCityHolder.BLUR_IMAGE.length]));
            } else {
                followedCityDatas.add(new FollowedCityData(weatherData, FollowedCityHolder.BLUR_IMAGE[(followedCityDatas.size()+1) % FollowedCityHolder.BLUR_IMAGE.length]));
            }
        }

        mFollowedWeather.setValue(followedCityDatas);
    }


    @Override
    public void onLocation(boolean success, String cityId) {
        fetchFollowedWeather();
    }
}
