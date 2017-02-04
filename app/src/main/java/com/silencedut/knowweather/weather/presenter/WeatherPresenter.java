package com.silencedut.knowweather.weather.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.citys.ui.SearchActivity;
import com.silencedut.knowweather.common.BasePresenter;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.model.CityModel;
import com.silencedut.knowweather.model.WeatherModel;
import com.silencedut.knowweather.model.callbacks.ModelCallback;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.knowweather.utils.PreferencesUtil;
import com.silencedut.knowweather.utils.TaskExecutor;
import com.silencedut.knowweather.weather.entity.WeatherEntity;
import com.silencedut.knowweather.weather.ui.adapter.AqiData;
import com.silencedut.knowweather.weather.ui.adapter.DailyWeatherData;
import com.silencedut.knowweather.weather.ui.adapter.HoursForecastData;
import com.silencedut.knowweather.weather.ui.adapter.LifeIndexData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SilenceDut on 16/10/29.
 */
public class WeatherPresenter extends BasePresenter<MainView> implements ModelCallback.LocationResult, ModelCallback.WeatherResult {

    private CityModel mCityModel;
    private WeatherModel mWeatherModel;

    private MainView mMainView;

    public WeatherPresenter(final MainView mainView) {
        super(mainView);
        mMainView = mainView;
        mCityModel = ModelManager.getModel(CityModel.class);
        mWeatherModel = ModelManager.getModel(WeatherModel.class);
        mCityModel.startLocation();
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 23) {

                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};

                    for(String permission:mPermissionList) {
                        if (ActivityCompat.checkSelfPermission(mMainView.getContext(),permission)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions((Activity) mMainView.getContext(), new String[]{permission},1);
                        }
                    }

                }
            }
        });
    }


    @Override
    public void onLocationComplete(String locationId, boolean success) {
        if (!success && mCityModel.noDefaultCity()) {
            Toast.makeText(getContext(), R.string.add_city_hand_mode, Toast.LENGTH_LONG).show();
            SearchActivity.navigationActivity(mMainView.getContext());
            return;
        }

        if (mCityModel.noDefaultCity()||!mCityModel.getDefaultId().equals(locationId)) {
            getWeather(locationId);
        }
    }

    public void getDefaultWeather() {

        WeatherEntity weatherEntity = mWeatherModel.getCachedWeather();

        if (!Check.isNull(weatherEntity)) {
            onWeather(weatherEntity);
        }
        updateDefaultWeather();
    }


    private void getWeather(final String cityId) {
        mMainView.onRefreshing(true);
        mWeatherModel.updateWeather(cityId);
    }

    public void updateDefaultWeather() {

        String defaultCity = mCityModel.getDefaultId();
        getWeather(defaultCity);
    }

    @Override
    public void onWeather(WeatherEntity weatherEntity) {
        if (weatherEntity == null) {
            mMainView.onRefreshing(false);
        } else {

            WeatherEntity.BasicEntity basicEntity = weatherEntity.getBasic();
            List<HoursForecastData> hoursForecastDatas = new ArrayList<>();
            for (WeatherEntity.HoursForecastEntity hoursForecastEntity : weatherEntity.getHoursForecast()) {
                hoursForecastDatas.add(new HoursForecastData(hoursForecastEntity));
            }
            AqiData aqiData = new AqiData(weatherEntity.getAqi());
            List<DailyWeatherData> dailyWeatherDatas = new ArrayList<>();
            List<WeatherEntity.DailyForecastEntity> dailyForecastEntities = weatherEntity.getDailyForecast();
            for (int count = 0; count < dailyForecastEntities.size() - 2; count++) {
                // only take 5 days weather
                dailyWeatherDatas.add(new DailyWeatherData(dailyForecastEntities.get(count)));
            }
            LifeIndexData lifeIndexData = new LifeIndexData(weatherEntity.getLifeIndex());

            boolean isLocationCity = weatherEntity.getCityId().equals(PreferencesUtil.get(Constants.LOCATION, Constants.DEFAULT_STR));

            mMainView.onBasicInfo(basicEntity, hoursForecastDatas, isLocationCity);
            mMainView.onMoreInfo(aqiData, dailyWeatherDatas, lifeIndexData);

        }
    }
}
