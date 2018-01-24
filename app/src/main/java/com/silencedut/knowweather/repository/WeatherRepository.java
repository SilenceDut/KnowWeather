package com.silencedut.knowweather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.baselib.commonhelper.utils.JsonHelper;
import com.silencedut.knowweather.repository.db.Weather;
import com.silencedut.knowweather.repository.db.WeatherDatabase;
import com.silencedut.router.Router;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.callback.EventCenter;
import com.silencedut.weather_core.corebase.StatusDataResource;
import com.silencedut.weather_core.repository.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by SilenceDut on 2018/1/15 .
 */
public class WeatherRepository  {
    private static final String TAG = "WeatherRepository";

    private final static  String WEATHER_DB_NAME = "weather";
    private WeatherDatabase mWeatherDatabase;
    private static final AtomicReference<WeatherRepository> INSTANCE_REFERENCE = new AtomicReference<>();

    private MutableLiveData<StatusDataResource<WeatherData>> mWeatherDataLiveData;
    private Handler mWeatherWorkHandler;

    private WeatherRepository() {
        mWeatherDatabase = DBHelper.provider(WeatherDatabase.class,WEATHER_DB_NAME);
        mWeatherWorkHandler = TaskScheduler.provideHandler(TAG);
        mWeatherDataLiveData = new MutableLiveData<>();

    }

    public static WeatherRepository getInstance() {

        for (;;) {
            WeatherRepository current = INSTANCE_REFERENCE.get();
            if (current != null) {
                return current;
            }
            current = new WeatherRepository();
            if (INSTANCE_REFERENCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    public Handler getWeatherWorkHandler() {
        return mWeatherWorkHandler;
    }

    @MainThread
    public MutableLiveData<StatusDataResource<WeatherData>> getWeatherObserver() {
        return mWeatherDataLiveData;
    }

    @Nullable
    public WeatherData getCachedWeatherData() {
        return mWeatherDataLiveData.getValue() == null?null:mWeatherDataLiveData.getValue().data;
    }


    @MainThread
    public void saveWeatherAsync(final String cityId, final StatusDataResource<WeatherData> statusDataResource) {
        mWeatherWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                updateWeather(cityId,statusDataResource);
            }
        });
    }

    @WorkerThread
    public void updateWeather(String cityId,final StatusDataResource<WeatherData> statusDataResource) {


        if(StatusDataResource.Status.SUCCESS.equals(statusDataResource.status)) {
            try {
                WeatherData weatherData = statusDataResource.data;
                Weather weather = new Weather();
                weather.cityId = weatherData.getCityId();
                weather.weatherJson = JsonHelper.toJson(weatherData);

                mWeatherDatabase.weatherDao().saveWeather(weather);
            }catch (Exception e) {
                LogHelper.error(TAG,"updateWeather error %s",e);
            }
        } else if(StatusDataResource.Status.LOADING.equals(statusDataResource.status)) {
            try {
                WeatherData weatherData = JsonHelper.fromJson(mWeatherDatabase.weatherDao().fetchWeather(cityId).weatherJson, WeatherData.class);
                if (weatherData != null) {
                    statusDataResource.data = weatherData;
                }
            }catch (Exception e) {
                LogHelper.error(TAG,"no cache hit");
            }

        }

        mWeatherDataLiveData.postValue(statusDataResource);
        Router.instance().getReceiver(EventCenter.NotificationStatus.class).onUpdateNotification();

    }



    @MainThread
    public void deleteWeather(final String cityId) {
        if(cityId == null) {
            return;
        }
        mWeatherWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                mWeatherDatabase.weatherDao().deleteWeather(cityId);
            }
        });

    }

    @WorkerThread
    public List<WeatherData> getFollowedWeather() {
        List<WeatherData> followedWeather = new ArrayList<>();
        try {
            for(Weather weather:mWeatherDatabase.weatherDao().fetchFollowedWeather()) {
                WeatherData weatherData = JsonHelper.fromJson(weather.weatherJson,WeatherData.class);
                followedWeather.add(weatherData);
            }

        }catch (Exception e) {
            LogHelper.info(TAG,"getFollowedWeather error %s",e);
        }

        return followedWeather;
    }
}
