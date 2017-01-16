package com.silencedut.knowweather.model;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.WeatherApplication;
import com.silencedut.knowweather.common.BaseModel;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.model.callbacks.ModelCallback;
import com.silencedut.knowweather.network.AppHttpClient;
import com.silencedut.knowweather.network.api.WeatherApi;
import com.silencedut.knowweather.utils.LogHelper;
import com.silencedut.knowweather.utils.PreferencesUtil;
import com.silencedut.knowweather.weather.callbacks.WeatherCallBack;
import com.silencedut.knowweather.weather.entity.WeatherEntity;
import com.silencedut.knowweather.weather.presenter.MainView;
import com.silencedut.router.Router;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SilenceDut on 2016/11/15 .
 */

public class WeatherModel extends BaseModel {
    private WeatherApi mWeatherApiService;
    private WeatherEntity mCachedWeather = null;

    public void onCreate() {
        mWeatherApiService = AppHttpClient.getInstance().getService(WeatherApi.class);
        mCachedWeather = initWeather();
    }

    public WeatherEntity getCachedWeather() {
        return mCachedWeather;
    }


    private WeatherEntity initWeather() {
        WeatherEntity weatherEntity = null;
        String mainPageCache = PreferencesUtil.get(Constants.MAIN_PAGE_WEATHER, Constants.DEFAULT_STR);
        if (!mainPageCache.equals(Constants.DEFAULT_STR)) {
            weatherEntity = WeatherApplication.getGson().fromJson(mainPageCache, WeatherEntity.class);
        }
        return weatherEntity;
    }


    public void updateDefaultWeather() {

        String defaultCity = ModelManager.getModel(CityModel.class).getDefaultId();
        updateWeather(defaultCity);
    }


    public void updateWeather(final String cityId) {

        Router.instance().getReceiver(MainView.class).onRefreshing(true);

        Call<WeatherEntity> weatherEntityCall = mWeatherApiService.getWeather(cityId);
        weatherEntityCall.enqueue(new Callback<WeatherEntity>() {
            @Override
            public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                WeatherEntity weatherEntity = response.body();
                if (response.isSuccessful() && weatherEntity != null) {
                    String cache = WeatherApplication.getGson().toJson(weatherEntity);
                    PreferencesUtil.put(Constants.MAIN_PAGE_WEATHER, cache);
                    mCachedWeather = weatherEntity;
                    onWeatherEntity(weatherEntity);
                    ModelManager.getModel(CityModel.class).setDefaultId(cityId);
                }
            }

            @Override
            public void onFailure(Call<WeatherEntity> call, Throwable t) {
                LogHelper.e(t, call.toString() + t.getMessage());
                onWeatherEntity(null);
            }
        });

    }

    private void onWeatherEntity(WeatherEntity weatherEntity) {

        Router.instance().getReceiver(ModelCallback.WeatherResult.class).onWeather(weatherEntity);
        Router.instance().getReceiver(WeatherCallBack.NotificationStatus.class).onUpdateNotification();
    }
}
