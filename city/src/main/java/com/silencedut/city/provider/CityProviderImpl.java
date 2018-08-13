package com.silencedut.city.provider;

import android.os.Handler;
import android.support.annotation.WorkerThread;
import android.support.v4.util.Pair;

import com.silencedut.city.R;
import com.silencedut.city.repository.ICityRepositoryApi;
import com.silencedut.city.ui.CityFragment;
import com.silencedut.hub_annotation.HubInject;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.City;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;
import com.silencedut.weather_core.corebase.BaseFragment;

/**
 * Created by SilenceDut on 2018/1/17 .
 */

@HubInject(api = ICityProvider.class)
public class CityProviderImpl implements ICityProvider {

    @Override
    public void onCreate() {

    }


    @Override
    public City searchCity(String cityId) {
        return CoreManager.getImpl(ICityRepositoryApi.class).searchCity(cityId);
    }

    @WorkerThread
    @Override
    public City searchCity(String cityName, String county) {
        return CoreManager.getImpl(ICityRepositoryApi.class).searchCity(cityName,county);
    }

    @WorkerThread
    @Override
    public Handler getCityWorkHandler() {
        return CoreManager.getImpl(ICityRepositoryApi.class).getCityWorkHandler();
    }


    @Override
    public void saveCurrentCityId(String cityId) {
        CoreManager.getImpl(ICityRepositoryApi.class).saveCurrentCityId(cityId);
    }

    @Override
    public String getCurrentCityId() {
        return CoreManager.getImpl(ICityRepositoryApi.class).getCurrentCityId();
    }

    @Override
    public boolean hadCurrentCityId() {
        return CoreManager.getImpl(ICityRepositoryApi.class).hadCurrentCityId();
    }


    @Override
    public void loadCitys() {
        CoreManager.getImpl(ICityRepositoryApi.class).loadCitys();
    }

    @Override
    public Pair<BaseFragment,Integer> provideCityFragment() {
        return new Pair<>(CityFragment.newInstance(), R.drawable.city_tab_drawable);
    }


}
