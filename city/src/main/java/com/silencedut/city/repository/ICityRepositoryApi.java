package com.silencedut.city.repository;

import android.os.Handler;
import android.support.annotation.WorkerThread;

import com.silencedut.weather_core.api.ICoreApi;
import com.silencedut.weather_core.api.cityprovider.City;

import java.util.List;

/**
 * Created by SilenceDut on 2018/1/5 .
 */

public interface ICityRepositoryApi extends ICoreApi {
    /**
     * 通过名字或者拼音搜索
     *
     * @param cityName 市,county 县
     * @return 结果
     */
    @WorkerThread
    City searchCity(final String cityName, final String county);

    @WorkerThread
    City searchCity(String cityId);

    @WorkerThread
    List<City> matchingCity(final String keyword);

    @WorkerThread
    List<City> queryAllCities();

    Handler getCityWorkHandler();
    void saveCurrentCityId(String cityId);
    String getCurrentCityId();
    boolean hadCurrentCityId();
    void loadCitys();
}
