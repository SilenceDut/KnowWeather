package com.silencedut.knowweather.citys.ui.presenter;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.citys.adapter.FollowedCityData;
import com.silencedut.knowweather.citys.adapter.FollowedCityHolder;
import com.silencedut.knowweather.common.BasePresenter;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.model.CityModel;
import com.silencedut.knowweather.model.callbacks.ModelCallback;
import com.silencedut.knowweather.network.AppHttpClient;
import com.silencedut.knowweather.network.api.WeatherApi;
import com.silencedut.knowweather.utils.LogHelper;
import com.silencedut.knowweather.utils.PreferencesUtil;
import com.silencedut.knowweather.utils.TaskExecutor;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by SilenceDut on 16/10/22 .
 */

public class FollowedCityPresenter extends BasePresenter<FollowedCityView> implements ModelCallback.WeatherResult {
    private WeatherApi mWeatherApiService;
    private CityModel mCityModel;

    public FollowedCityPresenter(FollowedCityView followedCityView) {
        super(followedCityView);
        mWeatherApiService = AppHttpClient.getInstance().getService(WeatherApi.class);
        mCityModel = ModelManager.getModel(CityModel.class);
    }

    public int followedCitiesNumber() {
        Set<String> defaultFollowed = new HashSet<>();
        defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, defaultFollowed);
        return defaultFollowed.size();
    }

    public void getFollowedWeather() {
        final List<FollowedCityData> followedCityDatas = new ArrayList<>();


        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                int length = 0;
                final Set<String> defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, new HashSet<String>());
                for (String cityId : defaultFollowed) {
                    Call<WeatherEntity> weatherEntityCall = mWeatherApiService.getWeather(cityId);
                    try {
                        Response<WeatherEntity> response = weatherEntityCall.execute();
                        WeatherEntity weatherEntity = response.body();
                        if (response.isSuccessful() && weatherEntity != null) {
                            parseWeatherEntity(weatherEntity, cityId, length, followedCityDatas);
                        }
                    } catch (IOException e) {
                        LogHelper.e(e, e.getMessage());
                    }
                    length++;
                }

                if (followedCityDatas.size() == 0) {
                    return;
                }
                TaskExecutor.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mPresentView.onAllFollowedCities(followedCityDatas);
                    }
                });
            }
        });
    }

    private List<FollowedCityData> parseWeatherEntity(WeatherEntity weatherEntity, String cityId, int length, List<FollowedCityData> followedCityDatas) {

        if (weatherEntity != null) {
            if (mCityModel.getLocationCityId().equals(cityId)) {
                followedCityDatas.add(0, new FollowedCityData(weatherEntity, FollowedCityHolder.BLUR_IMAGE[length % FollowedCityHolder.BLUR_IMAGE.length]));
            } else {
                followedCityDatas.add(new FollowedCityData(weatherEntity, FollowedCityHolder.BLUR_IMAGE[length % FollowedCityHolder.BLUR_IMAGE.length]));
            }
        }
        return followedCityDatas;
    }


    @Override
    public void onWeather(WeatherEntity weatherEntity) {
        if (weatherEntity == null) {
            return;
        }

        String cityId = weatherEntity.getCityId();

        Set<String> defaultFollowed = new HashSet<>();

        defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, defaultFollowed);
        boolean cityExisted = defaultFollowed.contains(cityId);
        if (!cityExisted) {
            defaultFollowed.add(cityId);
            PreferencesUtil.put(Constants.FOLLOWED_CITIES, defaultFollowed);
            FollowedCityData followedCityData = new FollowedCityData(weatherEntity, FollowedCityHolder.BLUR_IMAGE[followedCitiesNumber() % FollowedCityHolder.BLUR_IMAGE.length]);
            mPresentView.onFollowedCity(followedCityData);
        } else {
            mPresentView.onFollowedCity(null);
        }

    }
}
