package com.silencedut.knowweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.city.ui.search.SearchActivity;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.WeatherApplication;
import com.silencedut.knowweather.api.IFetchWeather;
import com.silencedut.knowweather.repository.WeatherRepository;
import com.silencedut.knowweather.ui.adapter.AqiData;
import com.silencedut.knowweather.ui.adapter.DailyWeatherData;
import com.silencedut.knowweather.ui.adapter.HoursForecastData;
import com.silencedut.knowweather.ui.adapter.LifeIndexData;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.corebase.StatusDataResource;
import com.silencedut.weather_core.location.ILocationApi;
import com.silencedut.weather_core.location.LocationNotification;
import com.silencedut.weather_core.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 2018/1/5 .
 */

public class WeatherModel extends BaseViewModel implements LocationNotification  {

    private static final String TAG = "WeatherModel";

    private MutableLiveData<StatusDataResource.Status> mGetWeatherStatus;

    private WeatherData.BasicEntity mWeatherBaseData;
    private List<HoursForecastData> mHoursDatas;
    private AqiData mAqiData;
    private List<DailyWeatherData> mDailyData;
    private LifeIndexData mLifeIndexData;

    @Override
    protected void onCreate() {

        mGetWeatherStatus = new MutableLiveData<>();

        WeatherRepository.getInstance().getWeatherObserver().observe(this, new Observer<StatusDataResource<WeatherData>>() {
            @Override
            public void onChanged(@Nullable StatusDataResource<WeatherData> statusDataResource) {

                try {
                    parseWeather(statusDataResource.data);
                }catch (Exception e) {
                    LogHelper.error(TAG,"parse weather date error %s",e);
                }

                mGetWeatherStatus.setValue(statusDataResource.status);
            }

        });
    }

    LiveData<StatusDataResource.Status> getGetWeatherStatus() {
        return mGetWeatherStatus;
    }


    void updateWeather() {
        if(CoreManager.getImpl(ICityProvider.class).hadCurrentCityId()) {
            CoreManager.getImpl(IFetchWeather.class).queryWeather(CoreManager.getImpl(ICityProvider.class).getCurrentCityId());
        }
    }

    boolean locationIsCurrent() {
        return CoreManager.getImpl(ICityProvider.class).getCurrentCityId()
                .equals(CoreManager.getImpl(ILocationApi.class).getLocatedCityId());
    }



    private void parseWeather(WeatherData weatherData) {

        mWeatherBaseData= weatherData.getBasic();

        List<HoursForecastData> hoursForecastDatas = new ArrayList<>();
        for (WeatherData.HoursForecastEntity hoursForecastEntity : weatherData.getHoursForecast()) {
            hoursForecastDatas.add(new HoursForecastData(hoursForecastEntity));
        }
        mHoursDatas = hoursForecastDatas ;

        mAqiData = new AqiData(weatherData.getAqi());

        List<DailyWeatherData> dailyWeatherDatas = new ArrayList<>();
        List<WeatherData.DailyForecastEntity> dailyForecastEntities = weatherData.getDailyForecast();
        for (int count = 0; count < dailyForecastEntities.size() - 2; count++) {
            // only take 5 days weather
            dailyWeatherDatas.add(new DailyWeatherData(dailyForecastEntities.get(count)));
        }
        mDailyData = dailyWeatherDatas;

        mLifeIndexData = new LifeIndexData(weatherData.getLifeIndex());
    }

    WeatherData.BasicEntity getWeatherBaseData() {
        return mWeatherBaseData;
    }

    List<HoursForecastData> getHoursDatas() {
        return mHoursDatas;
    }

    AqiData getAqiData() {
        return mAqiData;
    }

    List<DailyWeatherData> getDailyData() {
        return mDailyData;
    }

    LifeIndexData getLifeIndexData() {
        return mLifeIndexData;
    }

    @Override
    public void onLocation(boolean success, String cityId) {
        if (!success) {
            Toast.makeText(WeatherApplication.getContext(), R.string.weather_add_city_hand_mode, Toast.LENGTH_LONG).show();
            SearchActivity.navigationFromApplication(WeatherApplication.getContext());
        } else {
            if(!CoreManager.getImpl(ICityProvider.class).hadCurrentCityId()) {
                CoreManager.getImpl(ICityProvider.class).saveCurrentCityId(cityId);
                updateWeather();
            }
        }

    }
}
